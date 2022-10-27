package ru.vdnh.parser.mapper

import org.springframework.stereotype.Component
import ru.vdnh.parser.model.domain.Schedule
import ru.vdnh.parser.model.dto.dataset.ScheduleDTO
import ru.vdnh.parser.model.entity.ScheduleEntity

@Component
class ScheduleMapper {

    fun dtoToDomain(id: Long, schedule: List<ScheduleDTO>) = Schedule(
        id = id,
        monday = schedule.findScheduleByDay(1),
        tuesday = schedule.findScheduleByDay(2),
        wednesday = schedule.findScheduleByDay(3),
        thursday = schedule.findScheduleByDay(4),
        friday = schedule.findScheduleByDay(5),
        saturday = schedule.findScheduleByDay(6),
        sunday = schedule.findScheduleByDay(7),
        additionalInfo = schedule.retrieveAdditionalInfo()
    )

    fun domainToEntity(schedule: Schedule) = ScheduleEntity(
        id = schedule.id,
        monday = schedule.monday,
        tuesday = schedule.tuesday,
        wednesday = schedule.wednesday,
        thursday = schedule.thursday,
        friday = schedule.friday,
        saturday = schedule.saturday,
        sunday = schedule.sunday,
        additionalInfo = schedule.additionalInfo
    )

    private fun List<ScheduleDTO>.findScheduleByDay(dayNumber: Int): String? = find { schedule ->
        if (schedule.left == DAILY) return@find true
        if (dayNumber < 6 && schedule.left == WORKDAYS) return@find true

        val isScheduleByDay: Boolean = schedule.left
            .split(",")
            .any { DAY_NUMBERS[it.trim()] == dayNumber }
        if (isScheduleByDay) return@find true

        val scheduleRange: List<String> = schedule.left.split('—')
        val firstDayNumber: Int = DAY_NUMBERS[scheduleRange.first().trim()]
            ?: return@find false
        val lastDayNumber: Int = DAY_NUMBERS[scheduleRange.last().trim()]
            ?: return@find false
        if (dayNumber in firstDayNumber..lastDayNumber) return@find true
        return@find false
    }?.right

    private fun List<ScheduleDTO>.retrieveAdditionalInfo(): String? {
        val additionalInfo: List<ScheduleDTO> = filter { schedule -> !NOT_ADDITIONAL_INFO.any { schedule.left.contains(it) } }
        if (additionalInfo.isEmpty()) return null

        return additionalInfo.joinToString("\n") { scheduleInfo ->
            if (scheduleInfo.right.isBlank()) {
                scheduleInfo.left
            } else {
                "${scheduleInfo.left}: ${scheduleInfo.right}"
            }
        }
    }

    companion object {
        private const val DAILY = "Ежедневно"
        private const val WORKDAYS = "Будни"

        private val DAY_NUMBERS = mapOf(
            "Пн" to 1,
            "Вт" to 2,
            "Ср" to 3,
            "Чт" to 4,
            "Пт" to 5,
            "Сб" to 6,
            "Вс" to 7
        )

        private val NOT_ADDITIONAL_INFO = listOf(
            DAILY,
            WORKDAYS,
            *DAY_NUMBERS.keys.toTypedArray()
        )
    }
}
