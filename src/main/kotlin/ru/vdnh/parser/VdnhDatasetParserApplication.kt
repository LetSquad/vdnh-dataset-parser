package ru.vdnh.parser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class VdnhDatasetParserApplication

fun main(args: Array<String>) {
    runApplication<VdnhDatasetParserApplication>(*args)
}
