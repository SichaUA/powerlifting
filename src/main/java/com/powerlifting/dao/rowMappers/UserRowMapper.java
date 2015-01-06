package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        final User user = new User();

        user.setUserId(rs.getInt("userId"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("firstName"));
        user.setSecondName(rs.getString("secondName"));
        user.setMiddleName(rs.getString("middleName"));
        user.setGender(rs.getShort("gender"));
        user.setBirthday(rs.getDate("birthday"));
        user.setPhoto(rs.getString("photo"));
        user.setTitle(rs.getInt("title/discharge"));
        user.setRole(rs.getInt("role"));

        return user;
    }
}
