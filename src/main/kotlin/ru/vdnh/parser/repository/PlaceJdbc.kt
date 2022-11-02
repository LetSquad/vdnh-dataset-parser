@file:Suppress("DuplicatedCode")

package ru.vdnh.parser.repository

import org.postgresql.util.PGobject
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
                    "(id, title, title_en, title_cn, priority, visit_time_minutes, placement, payment_conditions, url, " +
                    "image_url, tickets_url, is_active, schedule, coordinates_id, type_code, subject_code, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            places,
            100
        ) { ps, place ->
            ps.setLong(1, place.id)
            ps.setString(2, place.title)
            ps.setString(3, place.titleEn)
            ps.setString(4, place.titleCn)
            ps.setObject(5, place.priority)
            ps.setInt(6, place.visitTimeMinutes)
            ps.setString(7, place.placement.name)
            ps.setString(8, place.paymentConditions.name)
            ps.setString(9, place.url)
            ps.setString(10, place.imageUrl)
            ps.setString(11, place.ticketsUrl)
            ps.setBoolean(12, place.isActive)
            val schedule = PGobject().apply {
                type = "jsonb"
                value = place.schedule
            }
            ps.setObject(13, schedule)
            ps.setLong(14, place.coordinatesId)
            ps.setString(15, place.typeCode)
            ps.setString(16, place.subjectCode)
            ps.setTimestamp(17, place.createdAt)
        }
    }
}
