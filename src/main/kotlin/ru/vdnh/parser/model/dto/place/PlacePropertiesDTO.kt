package ru.vdnh.parser.model.dto.place

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class PlacePropertiesDTO(
    val title: String,

    @JsonProperty("title_en")
    val titleEn: String?,

    @JsonProperty("title_cn")
    val titleCn: String?,

    val order: String,

    val type: String,

    @JsonProperty("type_en")
    val typeEn: String?,

    @JsonProperty("type_cn")
    val typeCn: String?,

    val coordinates: List<BigDecimal>,

    val url: String,

    @JsonProperty("tickets_link")
    val ticketsLink: String,

    val pic: String?,

    val icon: String,

    val color: String
)
