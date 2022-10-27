package ru.vdnh.parser.model.domain

import java.math.BigDecimal

data class Coordinates(
    val id: Long,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)
