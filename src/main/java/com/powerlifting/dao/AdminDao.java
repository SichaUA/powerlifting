package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.rowMappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AdminDao {
    private JdbcTemplate jdbcTemplate;

    public AdminDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getModerRequests() {
        final String sql = "SELECT * " +
                           "FROM user u " +
                           "WHERE u.role = 1";

        return jdbcTemplate.query(sql, new UserRowMapper());
    }
}
