package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.ParticipantAllInf;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.controllers.registered.model.WeightCategory;
import com.powerlifting.dao.rowMappers.ParticipantAllInfRowMapper;
import com.powerlifting.dao.rowMappers.UserRowMapper;
import com.powerlifting.dao.rowMappers.WeightCategoryRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ParticipantDao {
    private JdbcTemplate jdbcTemplate;

    public ParticipantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllParticipantOfCompetition(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM participant p JOIN user u ON p.user = u.userId " +
                "WHERE p.competition = ?";

        return jdbcTemplate.query(sql, new UserRowMapper(), competitionId);
    }

    public List<ParticipantAllInf> getAllParticipantOfCompetitionWithAllInf(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM (participant p JOIN user u ON p.user = u.userId) JOIN dictionary_region d ON p.from = d.regionId " +
                "WHERE p.competition = ?";

        return jdbcTemplate.query(sql, new ParticipantAllInfRowMapper(), competitionId);
    }

    public void deleteParticipantFromCompetition(Integer participantId, Integer competitionId) {
        final String sql =
                "DELETE FROM participant " +
                "WHERE user = ? AND competition = ?";

        jdbcTemplate.update(sql, participantId, competitionId);
    }

    public List<User> getUsersLikeWhichNotInCompetition(String text, Integer competitionId, Integer limit) {
        text = "%" + text + "%";

        final String sql =
                "SELECT * " +
                "FROM user u " +
                "WHERE CONCAT(u.secondName, \" \", u.firstName, \" \", u.middleName, \" \", u.email) LIKE ? AND " +
                "u.userId NOT IN (SELECT p.user " +
                                 "FROM participant p " +
                                 "WHERE p.competition = ?) " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, new UserRowMapper(), text, competitionId, limit);
    }

    public void addParticipantToCompetition(Integer userId, Integer competitionId, Integer category, String from, Float sq, Float bp, Float dl, Integer ownParticipation) {
        final String sql =
                "INSERT INTO participant (user, competition, category, `from`, squat, benchPress, deadlift, ownParticipation) " +
                "VALUES (?, ?, ?, (SELECT regionId " +
                                  "FROM dictionary_region " +
                                  "WHERE name = ?), ?, ?, ?, ?)";

        jdbcTemplate.update(sql, userId, competitionId, category, from, sq, bp, dl, ownParticipation);
    }

    public List<WeightCategory> getAllWeightCategories() {
        final String sql =
                "SELECT * " +
                "FROM dictionary_weight_category ";

        return jdbcTemplate.query(sql, new WeightCategoryRowMapper());
    }
}
