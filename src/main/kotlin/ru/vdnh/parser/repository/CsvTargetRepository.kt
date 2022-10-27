package ru.vdnh.parser.repository

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import ru.vdnh.parser.config.properties.CsvProperties
import ru.vdnh.parser.model.csv.EventCsv
import ru.vdnh.parser.model.csv.PlaceCsv
import java.io.FileWriter

@Repository
@Profile("csv")
class CsvTargetRepository(private val csvProperties: CsvProperties) {

    fun savePlaces(places: List<PlaceCsv>) {
        val writer = FileWriter(csvProperties.placesTarget)
        CSVPrinter(writer, CSVFormat.EXCEL).use { printer ->
            for (place in places) {
                printer.printRecord(
                    place.id,
                    place.title,
                    place.type,
                    place.priority
                )
            }
        }
    }

    fun saveEvents(events: List<EventCsv>) {
        val writer = FileWriter(csvProperties.eventsTarget)
        CSVPrinter(writer, CSVFormat.EXCEL).use { printer ->
            for (event in events) {
                printer.printRecord(
                    event.id,
                    event.title,
                    event.type,
                    event.priority
                )
            }
        }
    }
}
