package ru.vdnh.parser.repository

import org.postgresql.util.PGobject
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.entity.CoordinatesEntity

@Repository
class CoordinatesJdbc(private val jdbcTemplate: JdbcTemplate) : CoordinatesRepository {

    override fun clearCoordinates() {
        jdbcTemplate.update("TRUNCATE TABLE coordinates CASCADE")
    }

    override fun saveCoordinates(coordinatesList: List<CoordinatesEntity>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO coordinates (id, latitude, longitude, connections, load_factor) VALUES (?, ?, ?, ?, ?)",
            coordinatesList,
            100
        ) { ps, coordinates ->
            ps.setLong(1, coordinates.id)
            ps.setBigDecimal(2, coordinates.latitude)
            ps.setBigDecimal(3, coordinates.longitude)
            val connections = PGobject().apply {
                type = "jsonb"
                value = coordinates.connections
            }
            ps.setObject(4, connections)
            val loadFactor = PGobject().apply {
                type = "jsonb"
                value = coordinates.loadFactor
            }
            ps.setObject(5, loadFactor)
        }
    }
}
