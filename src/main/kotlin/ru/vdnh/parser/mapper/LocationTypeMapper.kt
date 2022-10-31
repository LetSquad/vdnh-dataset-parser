package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.domain.LocationType
import ru.vdnh.parser.model.dto.event.EventPlaceDTO
import ru.vdnh.parser.model.dto.place.PlaceDTO
import ru.vdnh.parser.model.entity.LocationTypeEntity
import ru.vdnh.parser.model.enums.LocationPlacement

@Component
class LocationTypeMapper {

    fun placeDtoToDomain(place: PlaceDTO): LocationType {
        val typeEn: String = place.properties.typeEn
            ?: TYPES_TRANSLATION.getValue(place.properties.type)
        val code: String = typeEn.normalizeType()

        return LocationType(
            code = code,
            name = place.properties.type.trim(),
            nameEn = typeEn.trim(),
            nameCn = place.properties.typeCn?.trim(),
            iconCode = place.properties.icon,
            iconColor = place.properties.color,
            placement = if (INDOORS_LOCATIONS.contains(code)) {
                LocationPlacement.INDOORS
            } else {
                LocationPlacement.OUTSIDE
            }
        )
    }

    fun eventDtoToDomain(event: EventPlaceDTO): LocationType {
        val typeEn: String = event.properties.typeEn
            ?: TYPES_TRANSLATION.getValue(event.properties.type!!)
        val code: String = typeEn.normalizeType()

        return LocationType(
            code = code,
            name = event.properties.type!!.trim(),
            nameEn = typeEn.trim(),
            nameCn = event.properties.typeCn?.trim(),
            iconCode = event.properties.icon!!,
            iconColor = event.properties.color!!,
            placement = if (INDOORS_LOCATIONS.contains(code)) {
                LocationPlacement.INDOORS
            } else {
                LocationPlacement.OUTSIDE
            }
        )
    }

    fun domainToEntity(locationType: LocationType) = LocationTypeEntity(
        code = locationType.code,
        name = locationType.name,
        nameEn = locationType.nameEn,
        nameCn = locationType.nameCn,
        iconCode = locationType.iconCode,
        iconColor = locationType.iconColor
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

        private val INDOORS_LOCATIONS = listOf(
            "WC",
            "EXHIBITION",
            "LECTURES_AND_MASTER_CLASSES",
            "INFOCENTER",
            "TEMPLE",
            "MUSEUM",
            "SOUVENIRS",
            "WORKSHOP",
            "FIRST_AID",
            "READING_ROOM",
            "HEALTH",
            "QUEST",
            "PAVILION",
            "FILM_SCREENING",
            "EDUCATION",
            "FOOD",
            "CONCERTS_AND_SHOWS",
            "ONLINE",
            "ENTERTAINMENT",
            "COMBO",
            "EXCURSION",
            "FOR_CHILDREN"
        )
    }
}
