package ru.vdnh.parser.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO
import ru.vdnh.parser.model.dto.dataset.VdnhDatasetDTO

@Repository
class DatasetSourceRepository(private val mapper: ObjectMapper) {

    fun getDataset(): VdnhDatasetDTO {
        val datasetSource = ClassPathResource(DATASET_SOURCE)
        return mapper.readValue(datasetSource.inputStream)
    }

    fun getPlaces(): VdnhPlacesDTO {
        val placesSource = ClassPathResource(PLACES_SOURCE)
        return mapper.readValue(placesSource.inputStream)
    }

    fun getEventPlaces(): VdnhEventPlacesDTO {
        val eventPlacesSource = ClassPathResource(EVENT_PLACES_SOURCE)
        return mapper.readValue(eventPlacesSource.inputStream)
    }

    companion object {
        private const val DATASET_SOURCE = "dataset/vdnh_dataset.json"
        private const val PLACES_SOURCE = "dataset/vdnh_places.json"
        private const val EVENT_PLACES_SOURCE = "dataset/vdnh_events.json"
    }
}
