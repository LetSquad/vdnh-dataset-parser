package ru.vdnh.parser.repository

import ru.vdnh.parser.model.entity.EventEntity

interface EventRepository {

    fun clearEvents()

    fun saveEvents(events: List<EventEntity>)
}
