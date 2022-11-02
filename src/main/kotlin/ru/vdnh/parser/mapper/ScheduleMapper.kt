package ru.vdnh.parser.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import ru.vdnh.parser.model.domain.Schedule
import ru.vdnh.parser.model.domain.WorkingHours
import ru.vdnh.parser.model.dto.dataset.ScheduleDTO
import java.time.LocalTime

@Component
class ScheduleMapper(private val mapper: ObjectMapper) {

    fun dtoToDomain(id: Long, schedule: List<ScheduleDTO>) = Schedule(
        monday = schedule.findWorkingHours(1),
        tuesday = schedule.findWorkingHours(2),
        wednesday = schedule.findWorkingHours(3),
        thursday = schedule.findWorkingHours(4),
        friday = schedule.findWorkingHours(5),
        saturday = schedule.findWorkingHours(6),
        sunday = schedule.findWorkingHours(7),
        additionalInfo = schedule.retrieveAdditionalInfo()
    )

    private fun List<ScheduleDTO>.findWorkingHours(dayNumber: Int): WorkingHours? {
        val workingHours: List<String> = findDay(dayNumber)?.right
            ?.split(" — ")
            ?: return null

        if (workingHours.first() == DAY_OFF) return WorkingHours(isDayOff = true)
        if (workingHours.first().startsWith("до")) {
            return WorkingHours(
                to = LocalTime.parse(workingHours.first().removePrefix("до ")),
                isDayOff = false
            )
        }

        return WorkingHours(
            from = LocalTime.parse(workingHours.first()),
            to = if (workingHours.last() == END_OF_DAY) {
                LocalTime.MIDNIGHT.minusSeconds(1)
            } else {
                LocalTime.parse(workingHours.last())
            },
            isDayOff = false
        )
    }

    private fun List<ScheduleDTO>.findDay(dayNumber: Int) = find { schedule ->
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
    }

    private fun List<ScheduleDTO>.retrieveAdditionalInfo(): List<String> {
        val additionalInfo: List<ScheduleDTO> = filter { schedule -> !NOT_ADDITIONAL_INFO.any { schedule.left.contains(it) } }
        if (additionalInfo.isEmpty()) return emptyList()

        return additionalInfo.flatMap { listOf(it.left, it.right) }
    }

    companion object {
        private const val DAILY = "Ежедневно"
        private const val WORKDAYS = "Будни"
        private const val DAY_OFF = "Выходной"
        private const val END_OF_DAY = "24:00"

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
