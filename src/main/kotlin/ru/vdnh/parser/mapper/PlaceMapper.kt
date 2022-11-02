package ru.vdnh.parser.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.vdnh.parser.model.VdnhDatasetParserConstants.BASE_URL
import ru.vdnh.parser.model.csv.PlaceCsv
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.domain.Place
import ru.vdnh.parser.model.dto.dataset.DatasetPlaceDTO
import ru.vdnh.parser.model.dto.dataset.ScheduleDTO
import ru.vdnh.parser.model.dto.place.PlaceDTO
import ru.vdnh.parser.model.entity.PlaceEntity
import ru.vdnh.parser.model.enums.LocationPlacement
import ru.vdnh.parser.model.enums.LocationSubject
import ru.vdnh.parser.model.enums.PaymentConditions
import java.sql.Timestamp
import java.time.Duration
import java.time.Instant

@Component
class PlaceMapper(
    private val mapper: ObjectMapper,
    private val scheduleMapper: ScheduleMapper,
    private val locationTypeMapper: LocationTypeMapper
) {

    fun dtoToDomain(place: PlaceDTO, datasetPlace: DatasetPlaceDTO? = null): Place {
        val locationType: LocationType = locationTypeMapper.placeDtoToDomain(place)
        val scheduleList: List<ScheduleDTO>? = datasetPlace?.schedule

        return Place(
            id = place.id,
            title = place.properties.title,
            titleEn = place.properties.titleEn,
            titleCn = place.properties.titleCn,
            type = locationType,
            subject = LocationSubject.forPlace(place.id),
            priority = place.properties.order.toInt(),
            visitTime = Duration.ofMinutes(15),
            placement = retrievePlacement(place.properties.title.lowercase(), locationType),
            paymentConditions = if (place.properties.ticketsLink.isBlank()) PaymentConditions.FREE else PaymentConditions.TICKET,
            url = place.properties.url,
            imageUrl = place.properties.pic,
            ticketsUrl = place.properties.ticketsLink.ifBlank { null },
            isActive = !CLOSED_PLACE_IDS.contains(place.id),
            schedule = if (scheduleList.isNullOrEmpty()) null else scheduleMapper.dtoToDomain(place.id, scheduleList),
            latitude = place.properties.coordinates.last(),
            longitude = place.properties.coordinates.first()
        )
    }

    fun domainToCsvDto(place: Place) = PlaceCsv(
        id = place.id,
        title = place.title,
        type = place.type.name,
        subject = place.subject?.nameRu,
        priority = place.priority,
        placement = place.placement,
        paymentConditions = place.paymentConditions,
        url = BASE_URL + place.url
    )

    fun domainToEntity(place: Place, coordinatesId: Long) = PlaceEntity(
        id = place.id,
        title = place.title,
        titleEn = place.titleEn,
        titleCn = place.titleCn,
        priority = place.priority,
        visitTimeMinutes = place.visitTime.toMinutes().toInt(),
        placement = place.placement,
        paymentConditions = place.paymentConditions,
        url = place.url,
        imageUrl = place.imageUrl,
        ticketsUrl = place.ticketsUrl,
        isActive = place.isActive,
        schedule = place.schedule?.let { mapper.writeValueAsString(it) },
        coordinatesId = coordinatesId,
        typeCode = place.type.code,
        subjectCode = place.subject?.name,
        createdAt = Timestamp.from(Instant.now())
    )

    private fun retrievePlacement(title: String, locationType: LocationType): LocationPlacement = when {
        title.contains("дом") || title.contains("павильон") || title.contains("студия") -> {
            LocationPlacement.INDOORS
        }
        title.contains("киоск") || title.contains("doner") -> LocationPlacement.OUTSIDE
        else -> locationType.placement
    }

    companion object {

        private val CLOSED_PLACE_IDS = setOf<Long>(
            242,
            259,
            270,
            271,
            281,
            287,
            289,
            290,
            294,
            311,
            327,
            342,
            347,
            348,
            353,
            357,
            360,
            366,
            431,
            432,
            2980,
            3581,
            4432,
            6040,
            6255
        )
    }
}
