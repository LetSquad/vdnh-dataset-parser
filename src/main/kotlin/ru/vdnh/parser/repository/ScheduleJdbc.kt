package ru.vdnh.parser.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.entity.ScheduleEntity

@Repository
class ScheduleJdbc(private val jdbcTemplate: JdbcTemplate) : ScheduleRepository {

    override fun clearSchedules() {
        jdbcTemplate.update("TRUNCATE TABLE schedule CASCADE")
    }

    override fun saveSchedules(schedules: List<ScheduleEntity>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO schedule (id, monday, tuesday, wednesday, thursday, friday, saturday, sunday, additional_info) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
            schedules,
            100
        ) { ps, schedule ->
            ps.setLong(1, schedule.id)
            ps.setString(2, schedule.monday)
            ps.setString(3, schedule.tuesday)
            ps.setString(4, schedule.wednesday)
            ps.setString(5, schedule.thursday)
            ps.setString(6, schedule.friday)
            ps.setString(7, schedule.saturday)
            ps.setString(8, schedule.sunday)
            ps.setString(9, schedule.additionalInfo)
        }
    }
}
