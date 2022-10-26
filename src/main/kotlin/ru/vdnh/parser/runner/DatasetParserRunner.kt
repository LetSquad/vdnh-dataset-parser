package ru.vdnh.parser.runner

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO
import ru.vdnh.parser.model.dto.dataset.VdnhDatasetDTO

@Component
class DatasetParserRunner(private val mapper: ObjectMapper) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val datasetSource = ClassPathResource("dataset/vdnh_dataset.json")
        val dataset: VdnhDatasetDTO = mapper.readValue(datasetSource.inputStream)

        val placesSource = ClassPathResource("dataset/vdnh_places.json")
        val places: VdnhPlacesDTO = mapper.readValue(placesSource.inputStream)

        val eventsSource = ClassPathResource("dataset/vdnh_events.json")
        val events: VdnhEventPlacesDTO = mapper.readValue(eventsSource.inputStream)

        val categories = HashSet<String>()
        val types = HashSet<String>()
        val typeNames = HashSet<String>()

        for (place in places.values) {
            place.properties
                .typeEn
                ?.let { types.add(normalizeType(it)) }
            typeNames.add(place.properties.type)
        }

        for (event in events.values) {
            categories.add(event.properties.cat)
            event.properties.typeEn
                ?.let { types.add(normalizeType(it)) }
            event.properties.type
                ?.let { typeNames.add(it) }
        }

        for (type in types) {
            log.info(type)
        }
    }

    private fun normalizeType(type: String) = type.lowercase()
        .trim()
        .replace(' ', '_')
        .replace("'", "")

    companion object {
        private val log = LoggerFactory.getLogger(DatasetParserRunner::class.java)
    }
}
