package ru.vdnh.parser.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vdnh.parser.getLogger
import ru.vdnh.parser.mapper.CoordinatesMapper
import ru.vdnh.parser.mapper.EventMapper
import ru.vdnh.parser.mapper.LocationSubjectMapper
import ru.vdnh.parser.mapper.LocationTypeMapper
import ru.vdnh.parser.mapper.PlaceMapper
import ru.vdnh.parser.model.VdnhDatasetParserConstants.EVENT_CATEGORY
import ru.vdnh.parser.model.domain.Coordinates
import ru.vdnh.parser.model.domain.Event
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.domain.Place
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO
import ru.vdnh.parser.model.dto.VdnhWorkloadDTO
import ru.vdnh.parser.model.dto.dataset.VdnhDatasetDTO
import ru.vdnh.parser.model.enums.LocationSubject
import ru.vdnh.parser.repository.CoordinatesRepository
import ru.vdnh.parser.repository.DatasetSourceRepository
import ru.vdnh.parser.repository.EventPlaceRepository
import ru.vdnh.parser.repository.EventRepository
import ru.vdnh.parser.repository.LocationSubjectRepository
import ru.vdnh.parser.repository.LocationTypeRepository
import ru.vdnh.parser.repository.PlaceRepository
import java.math.BigDecimal

@Service
@Profile("database")
class DatabaseTargetService(
    private val locationTypeMapper: LocationTypeMapper,
    private val locationSubjectMapper: LocationSubjectMapper,
    private val coordinatesMapper: CoordinatesMapper,
    private val placeMapper: PlaceMapper,
    private val eventMapper: EventMapper,
    private val vdnhService: VdnhService,
    private val routeDataService: RouteDataService,
    private val datasetRepository: DatasetSourceRepository,
    private val locationTypeRepository: LocationTypeRepository,
    private val locationSubjectRepository: LocationSubjectRepository,
    private val coordinatesRepository: CoordinatesRepository,
    private val placeRepository: PlaceRepository,
    private val eventRepository: EventRepository,
    private val eventPlaceRepository: EventPlaceRepository
) {

    @Transactional
    fun parseDatasetsToDatabase() {
        log.info("Parsing datasets")
        val vdnhDataset: VdnhDatasetDTO = datasetRepository.getDataset()
        val vdnhPlaces: VdnhPlacesDTO = vdnhService.getVdnhPlaces()
        val vdnhEventPlaces: VdnhEventPlacesDTO = vdnhService.getVdnhEventPlaces()
        val vdnhWorkload: VdnhWorkloadDTO = datasetRepository.getWorkload()

        log.info("Mapping data")
        val places: Map<Long, Place> = vdnhPlaces.values
            .associate { it.id to placeMapper.dtoToDomain(it, vdnhEventPlaces[it.id.toString()], vdnhDataset.places[it.id.toString()]) }
        val events: Map<Long, Event> = vdnhEventPlaces.values
            .filter { it.properties.cat == EVENT_CATEGORY }
            .associate { it.id to eventMapper.dtoToDomain(it) }

        val locationTypes = HashSet<LocationType>()
        val coordinates = HashMap<Pair<BigDecimal, BigDecimal>, Coordinates>()

        for (place in places.values) {
            locationTypes.add(place.type)
            if (!coordinates.containsKey(place.latitude to place.longitude)) {
                val coordinatesId: Long = (coordinates.size + 1).toLong()
                coordinates[place.latitude to place.longitude] = Coordinates(
                    id = coordinatesId,
                    latitude = place.latitude,
                    longitude = place.longitude,
                    connections = vdnhEventPlaces[place.id.toString()]?.properties?.attractions ?: emptyList(),
                    loadFactor = vdnhWorkload[place.id.toString()]?.workload
                )
            }
        }
        for (event in events.values) {
            locationTypes.add(event.type)
            if (event.longitude != null && event.latitude != null && !coordinates.containsKey(event.latitude to event.longitude)) {
                val coordinatesId: Long = (coordinates.size + 1).toLong()
                coordinates[event.latitude to event.longitude] = Coordinates(
                    id = coordinatesId,
                    latitude = event.latitude,
                    longitude = event.longitude,
                    connections = vdnhEventPlaces.getValue(event.id.toString()).properties.attractions
                )
            }
        }

        log.info("Clearing database")
        clearDatabase()

        log.info("Filling database")

        locationTypes.map { locationTypeMapper.domainToEntity(it) }
            .also { locationTypeRepository.saveLocationTypes(it) }

        LocationSubject.values()
            .map { locationSubjectMapper.enumToEntity(it) }
            .also { locationSubjectRepository.saveLocationSubjects(it) }

        val unknownNeighbors = HashSet<Long>()
        coordinates.values
            .map { coordinate ->
                coordinate.copy(
                    connections = coordinate.connections.mapNotNull {
                        val neighborPlace: Place? = places[it]
                        if (neighborPlace != null) {
                            return@mapNotNull coordinates.getValue(neighborPlace.latitude to neighborPlace.longitude).id
                        }

                        val neighborEvent: Event? = events[it]
                        if (neighborEvent?.latitude != null && neighborEvent.longitude != null) {
                            return@mapNotNull coordinates.getValue(neighborEvent.latitude to neighborEvent.longitude).id
                        }

                        unknownNeighbors.add(it)
                        return@mapNotNull null
                    }
                )
            }
            .map { coordinatesMapper.domainToEntity(it) }
            .also { coordinatesRepository.saveCoordinates(it) }
        if (unknownNeighbors.isNotEmpty()) {
            log.warn("Unknown neighbor ids = $unknownNeighbors")
        }

        places.values
            .map { placeMapper.domainToEntity(it, coordinates.getValue(it.latitude to it.longitude).id) }
            .also { placeRepository.savePlaces(it) }
        events.values
            .map { eventMapper.domainToEntity(it, coordinates[it.latitude to it.longitude]?.id) }
            .also { eventRepository.saveEvents(it) }
        events.values
            .flatMap { eventMapper.domainToEventPlaceEntities(it) }
            .also { eventPlaceRepository.saveEventPlaces(it) }

        routeDataService.fillRouteData()
    }

    private fun clearDatabase() {
        routeDataService.clearRouteData()
        locationTypeRepository.clearLocationTypes()
        locationSubjectRepository.clearLocationSubjects()
        coordinatesRepository.clearCoordinates()
        placeRepository.clearPlaces()
        eventRepository.clearEvents()
        eventPlaceRepository.clearEventPlaces()
    }

    companion object {
        private val log = getLogger<DatabaseTargetService>()
    }
}
