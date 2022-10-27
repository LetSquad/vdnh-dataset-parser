package ru.vdnh.parser.repository

import ru.vdnh.parser.model.entity.ScheduleEntity

interface ScheduleRepository {

    fun clearSchedules()

    fun saveSchedules(schedules: List<ScheduleEntity>)
}
