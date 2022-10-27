package ru.vdnh.parser.repository

import ru.vdnh.parser.model.entity.LocationTypeEntity

interface LocationTypeRepository {

    fun clearLocationTypes()

    fun saveLocationTypes(locationTypes: List<LocationTypeEntity>)
}
