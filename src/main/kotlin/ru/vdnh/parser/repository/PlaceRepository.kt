package ru.vdnh.parser.repository

import ru.vdnh.parser.model.domain.Place
import ru.vdnh.parser.model.entity.PlaceEntity

interface PlaceRepository {

    fun clearPlaces()

    fun savePlaces(places: List<PlaceEntity>)
}
