package ru.vdnh.parser.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.vdnh.parser.mapper.EventMapper
import ru.vdnh.parser.mapper.PlaceMapper
import ru.vdnh.parser.model.VdnhDatasetParserConstants.CATEGORY_EVENT
import ru.vdnh.parser.model.domain.Coordinates
import ru.vdnh.parser.model.domain.Event
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.domain.Place
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO
import ru.vdnh.parser.model.dto.dataset.VdnhDatasetDTO
import ru.vdnh.parser.repository.DatasetSourceRepository

@Service
@Profile("database")
class DatabaseTargetService(
    private val placeMapper: PlaceMapper,
    private val eventMapper: EventMapper,
    private val datasetRepository: DatasetSourceRepository
) {

    fun parseDatasetsToDatabase() {
        val vdnhDataset: VdnhDatasetDTO = datasetRepository.getDataset()
        val vdnhPlaces: VdnhPlacesDTO = datasetRepository.getPlaces()
        val vdnhEvents: VdnhEventPlacesDTO = datasetRepository.getEventPlaces()

        val places: List<Place> = vdnhPlaces.values
            .map { placeMapper.dtoToDomain(it, vdnhDataset.places[it.id.toString()]) }
        val events: List<Event> = vdnhEvents.values
            .filter { it.properties.cat == CATEGORY_EVENT }
            .map { eventMapper.dtoToDomain(it) }

        val locationTypes = HashSet<LocationType>()
        val coordinates = HashSet<Coordinates>()

        for (place in places) {
            locationTypes.add(place.type)
            coordinates.add(Coordinates(place.latitude, place.longitude))
        }
        for (event in events) {
            locationTypes.add(event.type)
            if (event.latitude != null && event.longitude != null) {
                coordinates.add(Coordinates(event.latitude, event.longitude))
            }
        }
    }
}
