package ru.vdnh.parser.model.entity

import java.sql.Date
import java.sql.Timestamp

data class EventEntity(
    val id: Long,
    val title: String,
    val titleEn: String?,
    val titleCn: String?,
    val priority: Int?,
    val url: String,
    val imageUrl: String?,
    val isActive: Boolean,
    val startDate: Date?,
    val finishDate: Date?,

    val coordinatesId: Long?,
    val scheduleId: Long?,

    val typeCode: String,
    val subjectCode: String?,

    val createdAt: Timestamp
)
