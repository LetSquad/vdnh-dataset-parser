package ru.vdnh.parser.model.domain

data class Schedule(
    val monday: String?,
    val tuesday: String?,
    val wednesday: String?,
    val thursday: String?,
    val friday: String?,
    val saturday: String?,
    val sunday: String?,
    val additionalInfo: String?
)
