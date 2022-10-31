package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.entity.LocationSubjectEntity
import ru.vdnh.parser.model.enums.LocationSubject

@Component
class LocationSubjectMapper {

    fun enumToEntity(locationSubject: LocationSubject) = LocationSubjectEntity(
        code = locationSubject.name,
        name = locationSubject.nameRu,
        nameEn = locationSubject.nameEn,
        nameCn = null
    )
}
