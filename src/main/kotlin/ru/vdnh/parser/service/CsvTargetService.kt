package ru.vdnh.parser.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.vdnh.parser.mapper.EventMapper
import ru.vdnh.parser.mapper.PlaceMapper
import ru.vdnh.parser.model.VdnhDatasetParserConstants.EVENT_CATEGORY
import ru.vdnh.parser.model.csv.EventCsv
import ru.vdnh.parser.model.csv.PlaceCsv
import ru.vdnh.parser.repository.CsvTargetRepository

@Service
@Profile("csv")
class CsvTargetService(
    private val placeMapper: PlaceMapper,
    private val eventMapper: EventMapper,
    private val vdnhService: VdnhService,
    private val csvTargetRepository: CsvTargetRepository
) {

    fun parsePlacesToCsv() {
        val places: List<PlaceCsv> = vdnhService.getVdnhPlaces()
            .values
            .map { placeMapper.dtoToDomain(it) }
            .map { placeMapper.domainToCsvDto(it)  }
        csvTargetRepository.savePlaces(places)
    }

    fun parseEventsToCsv() {
        val events: List<EventCsv> = vdnhService.getVdnhEventPlaces()
            .values
            .filter { it.properties.cat == EVENT_CATEGORY }
            .map { eventMapper.dtoToDomain(it) }
            .map { eventMapper.domainToCsvDto(it)  }
        csvTargetRepository.saveEvents(events)
    }
}
