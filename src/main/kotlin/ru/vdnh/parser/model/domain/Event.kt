package ru.vdnh.parser.model.domain

import ru.vdnh.parser.model.enums.LocationPlacement
import ru.vdnh.parser.model.enums.LocationSubject
import ru.vdnh.parser.model.enums.PaymentConditions
import java.math.BigDecimal
import java.time.Duration

data class Event(

    val id: Long,

    val title: String,
    val titleEn: String?,
    val titleCn: String?,

    val type: LocationType,
    val subject: LocationSubject?,

    val priority: Int,
    val visitTime: Duration,
    val placement: LocationPlacement,
    val paymentConditions: PaymentConditions,

    val url: String,
    val imageUrl: String?,

    val latitude: BigDecimal?,
    val longitude: BigDecimal?,

    val placeIds: List<Long>
)
