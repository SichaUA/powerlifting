package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.dao.rowMappers.CompetitionRowMapper;
import com.powerlifting.utils.CommonUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public List<Competition> getCurrentCompetitions() {
        final String sql = "SELECT *" +
                           "FROM competition c " +
                           "WHERE c.endDate > ?";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), new Date());
    }

    public List<Competition> getPastCompetitions() {
        final String sql = "SELECT *" +
                           "FROM competition c " +
                           "WHERE c.endDate < ?";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), new Date());
    }

    public Optional<Competition> getCompetition(Integer competitionId) {
        final String sql = "SELECT * " +
                           "FROM competition c " +
                           "WHERE c.competitionId = ?";

        return CommonUtils.selectOne(jdbcTemplate, sql, new CompetitionRowMapper(), competitionId);
    }

    public List<Competition> getCompetitionsCreatedByUser(Integer userId) {
        final String sql = "SELECT * " +
                           "FROM competition c " +
                           "WHERE c.author = ?";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), userId);
    }

    public List<Competition> getCompetitionsUserParticipate(Integer userId) {
        final String sql = "SELECT * " +
                           "FROM competition c " +
                           "WHERE c.competitionId IN(SELECT cp.competition " +
                           "FROM competition_participant cp " +
                           "WHERE cp.user = ?)";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), userId);
    }

    public void createNewCompetition(Competition competition, Integer userId) {
        final String sql = "INSERT INTO competition " +
                           "(city, name, startDate, endDate, gender, info, author) " +
                           "VALUES(?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, competition.getCity(), competition.getName(), competition.getStartDate(),
                competition.getEndDate(), competition.getGender(), competition.getInfo(), userId);
    }

    public void deleteCompetition(Integer competitionId) {
        String sql = "DELETE FROM competition WHERE competitionId = ?";

        jdbcTemplate.update(sql, competitionId);
    }
}
