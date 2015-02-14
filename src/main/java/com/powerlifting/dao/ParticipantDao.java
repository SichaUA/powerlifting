package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.rowMappers.UserRowMapper;
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

    public void deleteParticipantFromCompetition(Integer participantId, Integer competitionId) {
        final String sql =
                "DELETE FROM participant " +
                "WHERE user = ? AND competition = ?";

        jdbcTemplate.update(sql, participantId, competitionId);
    }

    public List<User> getUsersLikeWhichNotInCompetition(String text, Integer competitionId) {
        text = "%" + text + "%";

        final String sql =
                "SELECT * " +
                "FROM user u " +
                "WHERE CONCAT(u.secondName, \" \", u.firstName, \" \", u.middleName, \" \", u.email) LIKE ? AND " +
                "u.userId NOT IN (SELECT p.user " +
                                 "FROM participant p " +
                                 "WHERE p.competition = ?)";

        return jdbcTemplate.query(sql, new UserRowMapper(), text, competitionId);
    }

    public void AddParticipantToCompetition(String userEmail, Integer competitionId) {
        final String sql =
                "INSERT INTO participant (user, competition) " +
                "VALUES( " +
                    "(SELECT userId " +
                     "FROM user u " +
                     "WHERE u.email = ?), ?)";

        jdbcTemplate.update(sql, userEmail, competitionId);
    }
}
