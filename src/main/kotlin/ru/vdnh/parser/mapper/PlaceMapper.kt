package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.csv.PlaceCsv
import ru.vdnh.parser.model.domain.Place
import ru.vdnh.parser.model.dto.dataset.DatasetPlaceDTO
import ru.vdnh.parser.model.dto.place.PlaceDTO

@Component
class PlaceMapper(
    private val scheduleMapper: ScheduleMapper,
    private val typeMapper: TypeMapper
) {

    fun dtoToCsv(place: PlaceDTO) = PlaceCsv(
        id = place.id,
        title = place.properties.title,
        type = place.properties.type
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
        latitude = place.properties.coordinates.first(),
        longitude = place.properties.coordinates.last(),
        schedule = datasetPlace?.schedule?.let { scheduleMapper.dtoToDomain(it) },
        type = typeMapper.placeDtoToDomain(place)
    )
}
