package ru.vdnh.parser.repository

import ru.vdnh.parser.model.entity.EventPlaceEntity

interface EventPlaceRepository {

    fun clearEventPlaces()

    fun saveEventPlaces(eventPlaces: List<EventPlaceEntity>)
}
