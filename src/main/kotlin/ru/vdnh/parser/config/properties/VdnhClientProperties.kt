package ru.vdnh.parser.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("vdnh-client")
@ConstructorBinding
data class VdnhClientProperties(
    val vdnhUrl: String
)
