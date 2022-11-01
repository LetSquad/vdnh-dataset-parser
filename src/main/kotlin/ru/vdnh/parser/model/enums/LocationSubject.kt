package ru.vdnh.parser.model.enums

enum class LocationSubject(val nameRu: String, val nameEn: String, val nameCn: String? = null) {
    TECH("Наука и техника", "Science and Technology"),
    HISTORY("История", "History"),
    KIDS("Детям", "For kids"),
    MOSCOW("Моя Москва", "Moscow"),
    CINEMA("Кино", "Cinema"),
    TRANSPORT("Транспорт", "Transport"),
    NATURE("Природа", "Nature"),
    ANIMALS("Мир животных", "Animals"),
    SPORT("Спорт", "Sport"),
    ART("Искусство", "Art"),
    ARCHITECTURE("Архитектура ВДНХ", "Architecture");

    companion object {

        fun forPlace(placeId: Long): LocationSubject? {
            for ((subject, placeIds) in PLACE_IDS_BY_SUBJECTS) {
                if (placeIds.contains(placeId)) return subject
            }
            return null
        }

        private val PLACE_IDS_BY_SUBJECTS = listOf(
            TECH to setOf<Long>(
                269,
                303,
                280,
                293,
                284,
                325,
                17259
            ),

            HISTORY to setOf<Long>(
                351,
                2878,
                398
            ),

            KIDS to setOf<Long>(
                258,
                363,
                350,
                478
            ),

            MOSCOW to setOf<Long>(
                277,
                323,
                243,
                261
            ),

            CINEMA to setOf<Long>(
                267,
                311
            ),

            TRANSPORT to setOf<Long>(
                268,
                286
            ),

            NATURE to setOf<Long>(
                345,
                295,
                292
            ),

            ANIMALS to setOf<Long>(
                331,
                253,
                340,
                285,
                287,
                432
            ),

            SPORT to setOf<Long>(
                283,
                329,
                341
            ),

            ART to setOf<Long>(
                275,
                20186
            ),

            ARCHITECTURE to setOf<Long>(
                348,
                368,
                290,
                372,
                276,
                344,
                358,
                361,
                362,
                272,
                273,
                278,
                342,
                270,
                281,
                289,
                355,
                357,
                360,
                366,
                327,
                336,
                3261,
                6788,
                242,
                294,
                4551,
                347,
                22231,
                431,
                2980,
                338,
                259,
                271,
                274,
                282,
                343,
                353
            )
        )
    }
}
