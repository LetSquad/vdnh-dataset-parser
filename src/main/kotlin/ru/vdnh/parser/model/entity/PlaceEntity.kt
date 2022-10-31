package ru.vdnh.parser.model.entity

import ru.vdnh.parser.model.enums.LocationPlacement
import ru.vdnh.parser.model.enums.PaymentConditions
import java.sql.Timestamp

data class PlaceEntity(
    val id: Long,
    val title: String,
    val titleEn: String?,
    val titleCn: String?,
    val priority: Int,
    val visitTimeMinutes: Int,
    val placement: LocationPlacement,
    val paymentConditions: PaymentConditions,
    val url: String,
    val imageUrl: String?,
    val ticketsUrl: String?,
    val isActive: Boolean,

    val coordinatesId: Long,
    val scheduleId: Long?,

    val typeCode: String,
    val subjectCode: String?,

    val createdAt: Timestamp
)
