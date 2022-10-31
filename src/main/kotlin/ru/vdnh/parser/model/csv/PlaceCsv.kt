package ru.vdnh.parser.model.csv

import ru.vdnh.parser.model.enums.LocationPlacement
import ru.vdnh.parser.model.enums.PaymentConditions

data class PlaceCsv(
    val id: Long,
    val title: String,
    val type: String,
    val priority: Int,
    val placement: LocationPlacement,
    val paymentConditions: PaymentConditions
)
