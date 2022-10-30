package ru.vdnh.parser.model.dto

import ru.vdnh.parser.model.dto.event.EventPlaceDTO
import ru.vdnh.parser.model.dto.place.PlaceDTO
import ru.vdnh.parser.model.dto.workload.WorkloadDataDTO

typealias VdnhPlacesDTO = Map<String, PlaceDTO>

typealias VdnhEventPlacesDTO = Map<String, EventPlaceDTO>

typealias VdnhWorkloadDTO = Map<String, WorkloadDataDTO>
