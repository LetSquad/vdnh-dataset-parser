package ru.vdnh.parser.model.dto.workload

data class WorkloadDataDTO(
    val id: Long,
    val workload: Map<String, Map<String, Double>>
)
