package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.dto.event.EventPlaceDTO
import ru.vdnh.parser.model.dto.place.PlaceDTO

@Component
class TypeMapper {

    fun placeDtoToDomain(place: PlaceDTO): LocationType {
        val typeEn: String = place.properties.typeEn
            ?: TYPES_TRANSLATION.getValue(place.properties.type)

        return LocationType(
            code = typeEn.normalizeType(),
            name = place.properties.type,
            nameEn = typeEn,
            nameCn = place.properties.typeCn
        )
    }

    fun eventDtoToDomain(event: EventPlaceDTO): LocationType {
        val typeEn: String = event.properties.typeEn
            ?: TYPES_TRANSLATION.getValue(event.properties.type!!)

        return LocationType(
            code = typeEn.normalizeType(),
            name = event.properties.type!!,
            nameEn = typeEn,
            nameCn = event.properties.typeCn
        )
    }

    fun String.normalizeType() = uppercase().trim()
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
