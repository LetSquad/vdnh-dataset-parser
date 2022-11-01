package ru.vdnh.parser.model.domain

import java.time.LocalTime

data class WorkingHours(
    val from: LocalTime? = null,
    val to: LocalTime? = null,
    val isDayOff: Boolean
)
