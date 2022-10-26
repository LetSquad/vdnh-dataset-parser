package ru.vdnh.parser.model.dto.event

import com.fasterxml.jackson.annotation.JsonProperty

data class EventPlacePropertiesDTO(
    val cat: String,

    val title: String,

    @JsonProperty("title_en")
    val titleEn: String?,

    @JsonProperty("title_cn")
    val titleCn: String?,

    val type: String?,

    @JsonProperty("type_en")
    val typeEn: String?,

    val coordinates: List<Double>?,

    val url: String,

    val pic: String?
)