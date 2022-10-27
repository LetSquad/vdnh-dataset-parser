@file:Suppress("DuplicatedCode")

package ru.vdnh.parser.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.entity.PlaceEntity

@Repository
class PlaceJdbc(private val jdbcTemplate: JdbcTemplate) : PlaceRepository {

    override fun clearPlaces() {
        jdbcTemplate.update("TRUNCATE place CASCADE")
    }

    override fun savePlaces(places: List<PlaceEntity>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO place " +
                    "(id, title, title_en, title_cn, priority, url, image_url, tickets_url, " +
                    "is_active, coordinates_id, schedule_id, type_code, subject_code, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            places,
            100
        ) { ps, place ->
            ps.setLong(1, place.id)
            ps.setString(2, place.title)
            ps.setString(3, place.titleEn)
            ps.setString(4, place.titleCn)
            ps.setObject(5, place.priority)
            ps.setString(6, place.url)
            ps.setString(7, place.imageUrl)
            ps.setString(8, place.ticketsUrl)
            ps.setBoolean(9, place.isActive)
            ps.setLong(10, place.coordinatesId)
            ps.setObject(11, place.scheduleId)
            ps.setString(12, place.typeCode)
            ps.setString(13, place.subjectCode)
            ps.setTimestamp(14, place.createdAt)
        }
    }
}
