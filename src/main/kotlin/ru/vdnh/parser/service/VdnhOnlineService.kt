package ru.vdnh.parser.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.vdnh.parser.cllient.VdnhClient
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO

@Service
@Profile("online")
class VdnhOnlineService(
    private val mapper: ObjectMapper,
    private val vdnhClient: VdnhClient
) : VdnhService {

    override fun getVdnhPlaces(): VdnhPlacesDTO {
        val placesJs: String = runBlocking { vdnhClient.getPlaces() }
        return placesJs.removePrefix(EVENT_PLACES_PREFIX)
            .let { mapper.readValue(it) }
    }

    override fun getVdnhEventPlaces(): VdnhEventPlacesDTO {
        val eventPlacesJs: String = runBlocking { vdnhClient.getEventPlaces() }
        return eventPlacesJs.removePrefix(EVENT_PLACES_PREFIX)
            .removeSuffix(EVENT_PLACES_SUFFIX)
            .let { mapper.readValue(it) }
    }

    companion object {
        private const val EVENT_PLACES_PREFIX = "var places = "
        private const val EVENT_PLACES_SUFFIX = ";"
    }
}
