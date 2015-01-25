package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.rowMappers.UserRowMapper;
import com.powerlifting.utils.CommonUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user) {
        final String sql =
                "INSERT INTO user " +
                "(email, password, firstName, secondName, middleName, gender, birthday) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getFirstName(),
                user.getSecondName(), user.getMiddleName(), user.getGender(), user.getBirthday());
    }

    public Optional<User> getUserByCredentials(User user) {
        final String sql =
                "SELECT * " +
                "FROM user u " +
                "WHERE u.email = ? AND u.password = ?";

        return CommonUtils.selectOne(jdbcTemplate, sql, new UserRowMapper(), user.getEmail(), user.getPassword());
    }

    public void participateTheCompetition(Integer competitionId, Integer userId) {
        final String sql = "INSERT INTO competition_participant " +
                           "(user, competition) VALUES (?, ?);";

        jdbcTemplate.update(sql, userId, competitionId);
    }
}
