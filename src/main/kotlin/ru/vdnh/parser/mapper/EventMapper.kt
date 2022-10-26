package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.csv.EventCsv
import ru.vdnh.parser.model.dto.event.EventPlaceDTO

@Component
class EventMapper {

    fun dtoToCsv(event: EventPlaceDTO) = EventCsv(
        id = event.id,
        title = event.properties.title,
        type = event.properties.type
    )
}
