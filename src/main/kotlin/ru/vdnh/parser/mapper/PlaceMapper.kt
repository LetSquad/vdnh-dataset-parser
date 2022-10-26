package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.csv.PlaceCsv
import ru.vdnh.parser.model.dto.place.PlaceDTO

@Component
class PlaceMapper {

    fun dtoToCsv(place: PlaceDTO) = PlaceCsv(
        id = place.id,
        title = place.properties.title,
        type = place.properties.type
    )
}
