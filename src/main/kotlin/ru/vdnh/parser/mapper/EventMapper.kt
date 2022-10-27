package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.csv.EventCsv
import ru.vdnh.parser.model.domain.Event
import ru.vdnh.parser.model.dto.event.EventPlaceDTO

@Component
class EventMapper(private val locationTypeMapper: LocationTypeMapper) {

    fun dtoToCsv(event: EventPlaceDTO) = EventCsv(
        id = event.id,
        title = event.properties.title,
        type = event.properties.type
    )

    fun dtoToDomain(event: EventPlaceDTO) = Event(
        id = event.id,
        title = event.properties.title,
        titleEn = event.properties.titleEn,
        titleCn = event.properties.titleCn,
        priority = event.properties.order.toInt(),
        url = event.properties.url,
        imageUrl = event.properties.pic,
        latitude = event.properties.coordinates?.first(),
        longitude = event.properties.coordinates?.last(),
        type = locationTypeMapper.eventDtoToDomain(event)
    )
}
