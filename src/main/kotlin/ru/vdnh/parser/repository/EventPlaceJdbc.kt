package ru.vdnh.parser.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.entity.EventPlaceEntity

@Repository
class EventPlaceJdbc(private val jdbcTemplate: JdbcTemplate) : EventPlaceRepository {

    override fun clearEventPlaces() {
        jdbcTemplate.update("TRUNCATE TABLE event_place CASCADE")
    }

    override fun saveEventPlaces(eventPlaces: List<EventPlaceEntity>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO event_place (event_id, place_id) VALUES (?, ?)",
            eventPlaces,
            100
        ) { ps, eventPlace ->
            ps.setLong(1, eventPlace.eventId)
            ps.setLong(2, eventPlace.placeId)
        }
    }
}
