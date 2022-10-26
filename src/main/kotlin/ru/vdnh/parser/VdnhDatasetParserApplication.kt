package ru.vdnh.parser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VdnhDatasetParserApplication

fun main(args: Array<String>) {
    runApplication<VdnhDatasetParserApplication>(*args)
}
