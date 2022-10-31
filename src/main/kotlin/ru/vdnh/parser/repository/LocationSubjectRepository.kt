package ru.vdnh.parser.repository

import ru.vdnh.parser.model.entity.LocationSubjectEntity

interface LocationSubjectRepository {

    fun clearLocationSubjects()

    fun saveLocationSubjects(locationSubjects: List<LocationSubjectEntity>)
}
