package ru.vdnh.parser.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("csv")
@ConstructorBinding
class CsvProperties(
    val placesTarget: String,
    val eventsTarget: String
)
