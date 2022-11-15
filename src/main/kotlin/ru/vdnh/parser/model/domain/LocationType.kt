package ru.vdnh.parser.model.domain

import ru.vdnh.parser.model.enums.LocationPlacement
import java.time.Duration

data class LocationType(

    val code: String,
    val name: String,
    val nameEn: String,
    val nameCn: String?,

    val iconCode: String,
    val iconColor: String,

    val placement: LocationPlacement
) {

    fun retrieveVisitTime(): Duration = when (code) {
        "MUSEUM", "CONCERTS_AND_SHOWS", "EXHIBITION" -> Duration.ofMinutes(60)
        "PAVILION", "EXCURSION", "ENTERTAINMENT" -> Duration.ofMinutes(30)
        "MONUMENT" -> Duration.ofMinutes(5)
        "ENTRY", "ENTRANCE" -> Duration.ZERO
        else -> Duration.ofMinutes(15)
    }
}
