package ru.vdnh.parser.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vdnh.parser.getLogger
import ru.vdnh.parser.mapper.CoordinatesMapper
import ru.vdnh.parser.mapper.EventMapper
import ru.vdnh.parser.mapper.LocationTypeMapper
import ru.vdnh.parser.mapper.PlaceMapper
import ru.vdnh.parser.mapper.ScheduleMapper
import ru.vdnh.parser.model.VdnhDatasetParserConstants.CATEGORY_EVENT
import ru.vdnh.parser.model.domain.Coordinates
import ru.vdnh.parser.model.domain.Event
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.domain.Place
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO
import ru.vdnh.parser.model.dto.dataset.VdnhDatasetDTO
import ru.vdnh.parser.repository.CoordinatesRepository
import ru.vdnh.parser.repository.DatasetSourceRepository
import ru.vdnh.parser.repository.EventRepository
import ru.vdnh.parser.repository.LocationTypeRepository
import ru.vdnh.parser.repository.PlaceRepository
import ru.vdnh.parser.repository.ScheduleRepository
import java.math.BigDecimal

@Service
@Profile("database")
class DatabaseTargetService(
    private val locationTypeMapper: LocationTypeMapper,
    private val coordinatesMapper: CoordinatesMapper,
    private val scheduleMapper: ScheduleMapper,
    private val placeMapper: PlaceMapper,
    private val eventMapper: EventMapper,
    private val datasetRepository: DatasetSourceRepository,
    private val locationTypeRepository: LocationTypeRepository,
    private val coordinatesRepository: CoordinatesRepository,
    private val scheduleRepository: ScheduleRepository,
    private val placeRepository: PlaceRepository,
    private val eventRepository: EventRepository
) {

    @Transactional
    fun parseDatasetsToDatabase() {
        log.info("1 – Parsing datasets")
        val vdnhDataset: VdnhDatasetDTO = datasetRepository.getDataset()
        val vdnhPlaces: VdnhPlacesDTO = datasetRepository.getPlaces()
        val vdnhEvents: VdnhEventPlacesDTO = datasetRepository.getEventPlaces()

        log.info("2 – Mapping data")
        val places: List<Place> = vdnhPlaces.values
            .map { placeMapper.dtoToDomain(it, vdnhDataset.places[it.id.toString()]) }
        val events: List<Event> = vdnhEvents.values
            .filter { it.properties.cat == CATEGORY_EVENT }
            .map { eventMapper.dtoToDomain(it) }

        val locationTypes = HashSet<LocationType>()
        val coordinates = HashMap<Pair<BigDecimal, BigDecimal>, Coordinates>()

        for (place in places) {
            locationTypes.add(place.type)
            if (!coordinates.containsKey(place.latitude to place.longitude)) {
                val coordinatesId: Long = (coordinates.size + 1).toLong()
                coordinates[place.latitude to place.longitude] = Coordinates(coordinatesId, place.latitude, place.longitude)
            }
        }
        for (event in events) {
            locationTypes.add(event.type)
            if (event.longitude != null && event.latitude != null && !coordinates.containsKey(event.latitude to event.longitude)) {
                val coordinatesId: Long = (coordinates.size + 1).toLong()
                coordinates[event.latitude to event.longitude] = Coordinates(coordinatesId, event.latitude, event.longitude)
            }
        }

        log.info("3 – Clearing database")
        locationTypeRepository.clearLocationTypes()
        coordinatesRepository.clearCoordinates()
        scheduleRepository.clearSchedules()
        placeRepository.clearPlaces()
        eventRepository.clearEvents()

        log.info("4 – Filling database")
        locationTypes.map { locationTypeMapper.domainToEntity(it) }
            .also { locationTypeRepository.saveLocationTypes(it) }
        coordinates.values
            .map { coordinatesMapper.domainToEntity(it) }
            .also { coordinatesRepository.saveCoordinates(it) }
        places.mapNotNull { place -> place.schedule?.let { scheduleMapper.domainToEntity(it) } }
            .also { scheduleRepository.saveSchedules(it) }
        places.map { placeMapper.domainToEntity(it, coordinates.getValue(it.latitude to it.longitude).id) }
            .also { placeRepository.savePlaces(it) }
        events.map { eventMapper.domainToEntity(it, coordinates[it.latitude to it.longitude]?.id) }
            .also { eventRepository.saveEvents(it) }
    }

    companion object {
        private val log = getLogger<DatabaseTargetService>()
    }
}
