package ru.vdnh.parser.model.dto.dataset

data class DatasetPlaceDTO(
    val id: Long,
    val schedule: List<ScheduleDTO>?
)
