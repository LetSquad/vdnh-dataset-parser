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
                    "(id, title, title_en, title_cn, priority, visit_time_minutes, placement, payment_conditions, url, image_url, " +
                    "is_active, start_date, finish_date, coordinates_id, schedule_id, type_code, subject_code, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            events,
            100
        ) { ps, event ->
            ps.setLong(1, event.id)
            ps.setString(2, event.title)
            ps.setString(3, event.titleEn)
            ps.setString(4, event.titleCn)
            ps.setObject(5, event.priority)
            ps.setInt(6, event.visitTimeMinutes)
            ps.setString(7, event.placement.name)
            ps.setString(8, event.paymentConditions.name)
            ps.setString(9, event.url)
            ps.setString(10, event.imageUrl)
            ps.setBoolean(11, event.isActive)
            ps.setDate(12, event.startDate)
            ps.setDate(13, event.finishDate)
            ps.setObject(14, event.coordinatesId)
            ps.setObject(15, event.scheduleId)
            ps.setString(16, event.typeCode)
            ps.setString(17, event.subjectCode)
            ps.setTimestamp(18, event.createdAt)
        }
    }
}
