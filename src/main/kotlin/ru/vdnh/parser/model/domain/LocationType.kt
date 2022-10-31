package ru.vdnh.parser.model.domain

import ru.vdnh.parser.model.enums.LocationPlacement

data class LocationType(

    val code: String,
    val name: String,
    val nameEn: String,
    val nameCn: String?,
    val iconCode: String,

    val placement: LocationPlacement
)
