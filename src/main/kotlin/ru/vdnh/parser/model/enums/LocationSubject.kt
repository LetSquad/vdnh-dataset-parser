package ru.vdnh.parser.model.enums

enum class LocationSubject(val nameRu: String, val nameEn: String) {
    TECH("Наука и техника", "Science and Technology"),
    HISTORY("История", "History"),
    ARCHITECTURE("Архитектура ВДНХ", "Architecture"),
    KIDS("Детям", "For kids"),
    MOSCOW("Моя Москва", "Moscow"),
    CINEMA("Кино", "Cinema"),
    TRANSPORT("Транспорт", "Transport"),
    NATURE("Природа", "Nature"),
    ANIMALS("Мир животных", "Animals"),
    SPORT("Спорт", "Sport");

    companion object {

        fun forPlace(placeId: Long): LocationSubject? = when {
            TECH_PLACE_IDS.contains(placeId) -> TECH
            HISTORY_PLACE_IDS.contains(placeId) -> HISTORY
            else -> null
        }

        private val TECH_PLACE_IDS = setOf<Long>(
            269,
            303,
            280,
            293,
            284,
            325,
            17259
        )

        private val HISTORY_PLACE_IDS = setOf<Long>(
            351,
            2878,
            398
        )
    }
}
