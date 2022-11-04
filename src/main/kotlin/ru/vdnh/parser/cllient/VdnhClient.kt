package ru.vdnh.parser.cllient

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import ru.vdnh.parser.config.properties.VdnhClientProperties

@Component
@Profile("online")
class VdnhClient(properties: VdnhClientProperties, builder: WebClient.Builder) {

    val client: WebClient = builder.baseUrl(properties.vdnhUrl).build()

    suspend fun getPlaces(): String = client.get()
        .uri("/local/js/vdnh_events/places.js")
        .retrieve()
        .awaitBody()

    suspend fun getEventPlaces(): String = client.get()
        .uri("/local/templates/v3_new_header/js/places.js")
        .retrieve()
        .awaitBody()
}
