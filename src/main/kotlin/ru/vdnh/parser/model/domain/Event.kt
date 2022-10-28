package ru.vdnh.parser.model.domain

import java.math.BigDecimal

data class Event(
    val id: Long,
    val title: String,
    val titleEn: String?,
    val titleCn: String?,
    val priority: Int,
    val url: String,
    val imageUrl: String?,

    val latitude: BigDecimal?,
    val longitude: BigDecimal?,

    val placeIds: List<Long>,

    val type: LocationType
)
