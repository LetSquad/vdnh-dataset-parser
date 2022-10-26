package ru.vdnh.parser.model.dto.place

import com.fasterxml.jackson.annotation.JsonProperty

data class PlacePropertiesDTO(
    val title: String,

    @JsonProperty("title_en")
    val titleEn: String?,

    @JsonProperty("title_cn")
    val titleCn: String?,

    val type: String,

    @JsonProperty("type_en")
    val typeEn: String?,

    val coordinates: List<Double>,

    val url: String,

    @JsonProperty("tickets_link")
    val ticketsLink: String,

    val pic: String?
)
