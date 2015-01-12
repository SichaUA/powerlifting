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

//    public List<User> getModerRequests() {
//        final String sql = "SELECT * " +
//                           "FROM user u " +
//                           "WHERE u.role = 1";
//
//        return jdbcTemplate.query(sql, new UserRowMapper());
//    }

    public void assignUserToNewStatus(String email, int status) {
        final String sql = "UPDATE user u " +
                           "SET u.role = ? " +
                           "WHERE u.email = ?";

        jdbcTemplate.update(sql, status, email);
    }
}
