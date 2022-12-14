package ru.vdnh.parser.service

import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Service
import ru.vdnh.parser.getLogger
import javax.sql.DataSource

@Service
@Profile("database")
class RouteDataService(private val dataSource: DataSource, private val jdbcTemplate: JdbcTemplate) {

    fun fillRouteData() {
        log.info("Filling route data")
        val databasePopulator = ResourceDatabasePopulator(ClassPathResource("sql/insert_route_data.sql"))
        databasePopulator.execute(dataSource)
    }

    fun clearRouteData() {
        jdbcTemplate.update("TRUNCATE TABLE route_place CASCADE ")
        jdbcTemplate.update("TRUNCATE TABLE route CASCADE ")
    }

    companion object {
        private val log = getLogger<RouteDataService>()
    }
}
