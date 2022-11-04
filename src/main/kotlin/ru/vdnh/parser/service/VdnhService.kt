package ru.vdnh.parser.service

import ru.vdnh.parser.model.dto.VdnhEventPlacesDTO
import ru.vdnh.parser.model.dto.VdnhPlacesDTO

interface VdnhService {

    fun getVdnhPlaces(): VdnhPlacesDTO

    fun getVdnhEventPlaces(): VdnhEventPlacesDTO
}
