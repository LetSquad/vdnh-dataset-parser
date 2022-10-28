package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.csv.EventCsv
import ru.vdnh.parser.model.domain.Event
import ru.vdnh.parser.model.dto.event.EventPlaceDTO
import ru.vdnh.parser.model.entity.EventEntity
import ru.vdnh.parser.model.entity.EventPlaceEntity
import java.sql.Timestamp
import java.time.Instant

@Component
class EventMapper(private val locationTypeMapper: LocationTypeMapper) {

    fun dtoToCsv(event: EventPlaceDTO) = EventCsv(
        id = event.id,
        title = event.properties.title,
        type = event.properties.type!!,
        priority = event.properties.order.toInt()
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
        placeIds = event.properties.places?.map { it.toLong() } ?: emptyList(),
        type = locationTypeMapper.eventDtoToDomain(event)
    )

    fun domainToEntity(event: Event, coordinatesId: Long?) = EventEntity(
        id = event.id,
        title = event.title,
        titleEn = event.titleEn,
        titleCn = event.titleCn,
        priority = event.priority,
        url = event.url,
        imageUrl = event.imageUrl,
        isActive = true,
        startDate = null,
        finishDate = null,
        coordinatesId = coordinatesId,
        scheduleId = null,
        typeCode = event.type.code,
        subjectCode = null,
        createdAt = Timestamp.from(Instant.now())
    )

    fun domainToEventPlaceEntities(event: Event): List<EventPlaceEntity> = event.placeIds.map { placeId ->
        EventPlaceEntity(
            eventId = event.id,
            placeId = placeId
        )
    }
}
