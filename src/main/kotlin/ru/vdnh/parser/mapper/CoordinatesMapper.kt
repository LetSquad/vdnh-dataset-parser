package ru.vdnh.parser.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.vdnh.parser.model.domain.Coordinates
import ru.vdnh.parser.model.entity.CoordinatesEntity

@Component
class CoordinatesMapper(private val mapper: ObjectMapper) {

    fun domainToEntity(coordinates: Coordinates) = CoordinatesEntity(
        id = coordinates.id,
        latitude = coordinates.latitude,
        longitude = coordinates.longitude,
        connections = mapper.writeValueAsString(coordinates.connections),
        loadFactor = null
    )
}
