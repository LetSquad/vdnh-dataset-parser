package ru.vdnh.parser.runner

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import ru.vdnh.parser.getLogger
import ru.vdnh.parser.service.DatabaseTargetService

@Component
@Profile("database")
class DatabaseTargetRunner(private val databaseTargetService: DatabaseTargetService) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        log.info("Dataset parsing with target = database started")

        databaseTargetService.parseDatasetsToDatabase()

        log.info("Dataset parsing with target = database finished successfully")
    }

    companion object {
        private val log = getLogger<DatabaseTargetRunner>()
    }
}
