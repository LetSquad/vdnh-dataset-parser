package ru.vdnh.parser.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.vdnh.parser.mapper.EventMapper
import ru.vdnh.parser.mapper.PlaceMapper
import ru.vdnh.parser.model.VdnhDatasetParserConstants.CATEGORY_EVENT
import ru.vdnh.parser.model.csv.EventCsv
import ru.vdnh.parser.model.csv.PlaceCsv
import ru.vdnh.parser.repository.CsvTargetRepository
import ru.vdnh.parser.repository.DatasetSourceRepository

@Service
@Profile("csv")
class CsvTargetService(
    private val placeMapper: PlaceMapper,
    private val eventMapper: EventMapper,
    private val datasetRepository: DatasetSourceRepository,
    private val csvTargetRepository: CsvTargetRepository
) {

    fun parsePlacesToCsv() {
        val places: List<PlaceCsv> = datasetRepository.getPlaces()
            .values
            .map { placeMapper.dtoToCsv(it) }
        csvTargetRepository.savePlaces(places)
    }

    fun parseEventsToCsv() {
        val events: List<EventCsv> = datasetRepository.getEventPlaces()
            .values
            .filter { it.properties.cat == CATEGORY_EVENT }
            .map { eventMapper.dtoToCsv(it) }
        csvTargetRepository.saveEvents(events)
    }
}
