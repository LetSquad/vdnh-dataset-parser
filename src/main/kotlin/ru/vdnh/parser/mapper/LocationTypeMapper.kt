package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.dto.event.EventPlaceDTO
import ru.vdnh.parser.model.dto.place.PlaceDTO
import ru.vdnh.parser.model.entity.LocationTypeEntity

@Component
class LocationTypeMapper {

    fun placeDtoToDomain(place: PlaceDTO): LocationType {
        val typeEn: String = place.properties.typeEn
            ?: TYPES_TRANSLATION.getValue(place.properties.type)

        return LocationType(
            code = typeEn.normalizeType(),
            name = place.properties.type.trim(),
            nameEn = typeEn.trim(),
            nameCn = place.properties.typeCn?.trim()
        )
    }

    fun eventDtoToDomain(event: EventPlaceDTO): LocationType {
        val typeEn: String = event.properties.typeEn
            ?: TYPES_TRANSLATION.getValue(event.properties.type!!)

        return LocationType(
            code = typeEn.normalizeType(),
            name = event.properties.type!!.trim(),
            nameEn = typeEn.trim(),
            nameCn = event.properties.typeCn?.trim()
        )
    }

    fun domainToEntity(locationType: LocationType) = LocationTypeEntity(
        code = locationType.code,
        name = locationType.name,
        nameEn = locationType.nameEn,
        nameCn = locationType.nameCn
    )

    private fun String.normalizeType() = uppercase().trim()
        .replace(' ', '_')
        .replace("'", "")

    companion object {
        private val TYPES_TRANSLATION = mapOf(
            "Билеты" to "Tickets",
            "Здоровье" to "Health",
            "Квест" to "Quest"
        )
    }
}
