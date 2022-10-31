package ru.vdnh.parser.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.vdnh.parser.model.entity.LocationSubjectEntity

@Repository
class LocationSubjectJdbc(private val jdbcTemplate: JdbcTemplate) : LocationSubjectRepository {

    override fun clearLocationSubjects() {
        jdbcTemplate.update("TRUNCATE TABLE location_subject CASCADE")
    }

    override fun saveLocationSubjects(locationSubjects: List<LocationSubjectEntity>) {
        jdbcTemplate.batchUpdate(
            "INSERT INTO location_subject (code, name, name_en, name_cn) VALUES (?, ?, ?, ?)",
            locationSubjects,
            100
        ) { ps, locationSubject ->
            ps.setString(1, locationSubject.code)
            ps.setString(2, locationSubject.name)
            ps.setString(3, locationSubject.nameEn)
            ps.setString(4, locationSubject.nameCn)
        }
    }
}
