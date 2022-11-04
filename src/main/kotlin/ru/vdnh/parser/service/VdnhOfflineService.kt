package ru.vdnh.parser.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO
import ru.vdnh.parser.repository.DatasetSourceRepository

@Service
@Profile("!online")
class VdnhOfflineService(private val datasetSourceRepository: DatasetSourceRepository) : VdnhService {

    override fun getVdnhPlaces(): VdnhPlacesDTO = datasetSourceRepository.getPlaces()

    override fun getVdnhEventPlaces(): VdnhEventPlacesDTO = datasetSourceRepository.getEventPlaces()
}
