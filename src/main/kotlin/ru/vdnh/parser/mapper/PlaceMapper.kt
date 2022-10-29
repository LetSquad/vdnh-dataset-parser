package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.csv.PlaceCsv
import ru.vdnh.parser.model.domain.Place
import ru.vdnh.parser.model.dto.dataset.DatasetPlaceDTO
import ru.vdnh.parser.model.dto.place.PlaceDTO
import ru.vdnh.parser.model.entity.PlaceEntity
import java.sql.Timestamp
import java.time.Instant

@Component
class PlaceMapper(
    private val scheduleMapper: ScheduleMapper,
    private val locationTypeMapper: LocationTypeMapper
) {

    fun dtoToCsv(place: PlaceDTO) = PlaceCsv(
        id = place.id,
        title = place.properties.title,
        type = place.properties.type,
        priority = place.properties.order.toInt()
    )

    fun dtoToDomain(place: PlaceDTO, datasetPlace: DatasetPlaceDTO?) = Place(
        id = place.id,
        title = place.properties.title,
        titleEn = place.properties.titleEn,
        titleCn = place.properties.titleCn,
        priority = place.properties.order.toInt(),
        url = place.properties.url,
        imageUrl = place.properties.pic,
        ticketsUrl = place.properties.ticketsLink.ifBlank { null },
        isActive = !CLOSED_PLACES.contains(place.id),
        latitude = place.properties.coordinates.last(),
        longitude = place.properties.coordinates.first(),
        schedule = datasetPlace?.schedule?.let { scheduleMapper.dtoToDomain(place.id, it) },
        type = locationTypeMapper.placeDtoToDomain(place)
    )

    fun domainToEntity(place: Place, coordinatesId: Long) = PlaceEntity(
        id = place.id,
        title = place.title,
        titleEn = place.titleEn,
        titleCn = place.titleCn,
        priority = place.priority,
        url = place.url,
        imageUrl = place.imageUrl,
        ticketsUrl = place.ticketsUrl,
        isActive = place.isActive,
        coordinatesId = coordinatesId,
        scheduleId = place.schedule?.id,
        typeCode = place.type.code,
        subjectCode = null,
        createdAt = Timestamp.from(Instant.now())
    )

    companion object {
        private val CLOSED_PLACES = listOf(
            3581L
        )
    }
}
