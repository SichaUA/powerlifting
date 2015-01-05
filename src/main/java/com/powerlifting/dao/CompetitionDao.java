package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.dao.rowMappers.CompetitionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CompetitionDao {
    private JdbcTemplate jdbcTemplate;

    public CompetitionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Competition> getAllCompetitions() {
        final String sql = "SELECT *" +
                           "FROM competition c";

        return jdbcTemplate.query(sql, new CompetitionRowMapper());
    }
}
