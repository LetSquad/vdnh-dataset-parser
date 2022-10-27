package ru.vdnh.parser.model.dto.event

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class EventPlacePropertiesDTO(
    val cat: String,

    val title: String,

    @JsonProperty("title_en")
    val titleEn: String?,

    @JsonProperty("title_cn")
    val titleCn: String?,

    val order: String,

    val type: String?,

    @JsonProperty("type_en")
    val typeEn: String?,

    @JsonProperty("type_cn")
    val typeCn: String?,

    val coordinates: List<BigDecimal>?,

    val attractions: List<Long>,

    val url: String,

    val pic: String?
)
