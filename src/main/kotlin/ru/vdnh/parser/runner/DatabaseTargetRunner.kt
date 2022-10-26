package ru.vdnh.parser.runner

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import ru.vdnh.parser.getLogger
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO
import ru.vdnh.parser.model.dto.dataset.VdnhDatasetDTO
import ru.vdnh.parser.repository.DatasetSourceRepository

@Component
@Profile("database")
class DatabaseTargetRunner(private val datasetRepository: DatasetSourceRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val dataset: VdnhDatasetDTO = datasetRepository.getDataset()
        val places: VdnhPlacesDTO = datasetRepository.getPlaces()
        val events: VdnhEventPlacesDTO = datasetRepository.getEventPlaces()

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
        private val log = getLogger<DatabaseTargetRunner>()
    }
}
