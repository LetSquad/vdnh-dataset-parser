package ru.vdnh.parser.model.csv

import ru.vdnh.parser.model.enums.LocationPlacement
import ru.vdnh.parser.model.enums.PaymentConditions

data class EventCsv(
    val id: Long,
    val title: String,
    val type: String,
    val priority: Int,
    val subject: String?,
    val placement: LocationPlacement,
    val paymentConditions: PaymentConditions,
    val url: String
)
