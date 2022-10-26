package ru.vdnh.parser.runner

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import ru.vdnh.parser.getLogger
import ru.vdnh.parser.service.CsvTargetService

@Component
@Profile("csv")
class CsvTargetRunner(private val csvTargetService: CsvTargetService) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        log.info("Dataset parsing with target = csv started")

        csvTargetService.parsePlacesToCsv()
        csvTargetService.parseEventsToCsv()

        log.info("Dataset parsing with target = csv finished successfully")
    }

    companion object {
        private val log = getLogger<CsvTargetRunner>()
    }
}
