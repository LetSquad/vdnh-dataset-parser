package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.VdnhDatasetParserConstants.BASE_URL
import ru.vdnh.parser.model.csv.EventCsv
import ru.vdnh.parser.model.domain.Event
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.dto.event.EventPlaceDTO
import ru.vdnh.parser.model.entity.EventEntity
import ru.vdnh.parser.model.entity.EventPlaceEntity
import ru.vdnh.parser.model.enums.LocationSubject
import ru.vdnh.parser.model.enums.PaymentConditions
import java.sql.Timestamp
import java.time.Instant

@Component
class EventMapper(private val locationTypeMapper: LocationTypeMapper) {

    fun dtoToDomain(event: EventPlaceDTO): Event {
        val locationType: LocationType = locationTypeMapper.eventDtoToDomain(event)
        return Event(
            id = event.id,
            title = event.properties.title,
            titleEn = event.properties.titleEn,
            titleCn = event.properties.titleCn,
            type = locationType,
            subject = LocationSubject.forEvent(event.id),
            priority = event.properties.order.toInt(),
            visitTime = locationType.retrieveVisitTime(),
            placement = locationType.placement,
            paymentConditions = PaymentConditions.TICKET,
            url = event.properties.url,
            imageUrl = event.properties.pic,
            latitude = event.properties.coordinates?.last(),
            longitude = event.properties.coordinates?.first(),
            placeIds = event.properties.places?.map { it.toLong() } ?: emptyList()
        )
    }

    fun domainToCsvDto(event: Event) = EventCsv(
        id = event.id,
        title = event.title,
        type = event.type.name,
        subject = event.subject?.nameRu,
        priority = event.priority,
        placement = event.placement,
        paymentConditions = event.paymentConditions,
        url = BASE_URL + event.url
    )

    fun domainToEntity(event: Event, coordinatesId: Long?) = EventEntity(
        id = event.id,
        title = event.title,
        titleEn = event.titleEn,
        titleCn = event.titleCn,
        priority = event.priority,
        visitTimeMinutes = event.visitTime.toMinutes().toInt(),
        placement = event.placement,
        paymentConditions = event.paymentConditions,
        url = event.url,
        imageUrl = event.imageUrl,
        isActive = true,
        startDate = null,
        finishDate = null,
        schedule = null,
        coordinatesId = coordinatesId,
        typeCode = event.type.code,
        subjectCode = event.subject?.name,
        createdAt = Timestamp.from(Instant.now())
    )

    fun domainToEventPlaceEntities(event: Event): List<EventPlaceEntity> = event.placeIds.map { placeId ->
        EventPlaceEntity(
            eventId = event.id,
            placeId = placeId
        )
    }
}
