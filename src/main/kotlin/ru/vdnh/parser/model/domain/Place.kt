package ru.vdnh.parser.model.domain

import java.math.BigDecimal

data class Place(
    val id: Long,
    val title: String,
    val titleEn: String?,
    val titleCn: String?,
    val priority: Int,
    val url: String,
    val imageUrl: String?,
    val ticketsUrl: String?,
    val isActive: Boolean,

    val latitude: BigDecimal,
    val longitude: BigDecimal,

    val schedule: Schedule?,

    val type: LocationType
)
