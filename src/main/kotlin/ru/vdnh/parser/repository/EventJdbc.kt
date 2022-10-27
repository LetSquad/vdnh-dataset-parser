@file:Suppress("DuplicatedCode")

package ru.vdnh.parser.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.entity.EventEntity

@Repository
class EventJdbc(private val jdbcTemplate: JdbcTemplate) : EventRepository {

    override fun clearEvents() {
        jdbcTemplate.update("TRUNCATE TABLE event CASCADE")
    }

    override fun saveEvents(events: List<EventEntity>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO event " +
                    "(id, title, title_en, title_cn, priority, url, image_url, " +
                    "is_active, start_date, finish_date, coordinates_id, schedule_id, type_code, subject_code, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            events,
            100
        ) { ps, event ->
            ps.setLong(1, event.id)
            ps.setString(2, event.title)
            ps.setString(3, event.titleEn)
            ps.setString(4, event.titleCn)
            ps.setObject(5, event.priority)
            ps.setString(6, event.url)
            ps.setString(7, event.imageUrl)
            ps.setBoolean(8, event.isActive)
            ps.setDate(9, event.startDate)
            ps.setDate(10, event.finishDate)
            ps.setObject(11, event.coordinatesId)
            ps.setObject(12, event.scheduleId)
            ps.setString(13, event.typeCode)
            ps.setString(14, event.subjectCode)
            ps.setTimestamp(15, event.createdAt)
        }
    }
}
