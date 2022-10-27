package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.domain.Coordinates
import ru.vdnh.parser.model.entity.CoordinatesEntity

@Component
class CoordinatesMapper {

    fun domainToEntity(coordinates: Coordinates) = CoordinatesEntity(
        id = coordinates.id,
        latitude = coordinates.latitude,
        longitude = coordinates.longitude,
        connections = null,
        loadFactor = null
    )
}
