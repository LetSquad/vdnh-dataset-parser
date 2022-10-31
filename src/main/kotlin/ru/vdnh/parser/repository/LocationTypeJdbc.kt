package ru.vdnh.parser.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.entity.LocationTypeEntity

@Repository
class LocationTypeJdbc(private val jdbcTemplate: JdbcTemplate) : LocationTypeRepository {

    override fun clearLocationTypes() {
        jdbcTemplate.update("TRUNCATE TABLE location_type CASCADE")
    }

    override fun saveLocationTypes(locationTypes: List<LocationTypeEntity>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO location_type (code, name, name_en, name_cn, icon_code, icon_color) VALUES (?, ?, ?, ?, ?, ?)",
            locationTypes,
            100
        ) { ps, locationType ->
            ps.setString(1, locationType.code)
            ps.setString(2, locationType.name)
            ps.setString(3, locationType.nameEn)
            ps.setString(4, locationType.nameCn)
            ps.setString(5, locationType.iconCode)
            ps.setString(6, locationType.iconColor)
        }
    }
}
