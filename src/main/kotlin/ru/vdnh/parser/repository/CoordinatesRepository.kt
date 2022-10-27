package ru.vdnh.parser.repository

import ru.vdnh.parser.model.entity.CoordinatesEntity

interface CoordinatesRepository {

    fun clearCoordinates()

    fun saveCoordinates(coordinatesList: List<CoordinatesEntity>)
}
