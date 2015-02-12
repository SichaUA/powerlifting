package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.AgeGroup;
import com.powerlifting.controllers.registered.model.Competition;
import com.powerlifting.dao.rowMappers.AgeGroupRowMapper;
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
                           "(author, city, name, startDate, endDate, gender, info, type, ageGroup) " +
                           "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, userId, competition.getCity(), competition.getName(), competition.getStartDate(),
                competition.getEndDate(), competition.getGender(), competition.getInfo(), competition.getType(), competition.getAgeGroup());
    }

    public void deleteCompetition(Integer competitionId) {
        String sql = "DELETE FROM competition WHERE competitionId = ?";

        jdbcTemplate.update(sql, competitionId);
    }

    public String getCompetitionAgeGroupById(Integer ageGroupId) {
        final String sql =
                "SELECT description " +
                "FROM dictionary_age_group " +
                "WHERE groupId = ?";

        return jdbcTemplate.queryForObject(sql, String.class, ageGroupId);
    }

    public List<AgeGroup> getAllAgeGroups() {
        final String sql =
                "SELECT * " +
                "FROM dictionary_age_group";

        return jdbcTemplate.query(sql, new AgeGroupRowMapper());
    }
}
