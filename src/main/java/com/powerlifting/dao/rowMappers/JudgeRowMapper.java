package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Judge;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JudgeRowMapper implements RowMapper<Judge>{
    @Override
    public Judge mapRow(ResultSet rs, int rowNum) throws SQLException {
        Judge judge = new Judge();

        judge.setUserId(rs.getInt("userId"));
        judge.setEmail(rs.getString("email"));
        judge.setPassword(rs.getString("password"));
        judge.setFirstName(rs.getString("firstName"));
        judge.setSecondName(rs.getString("secondName"));
        judge.setMiddleName(rs.getString("middleName"));
        judge.setGender(rs.getShort("gender"));
        judge.setBirthday(rs.getDate("birthday"));
        judge.setPhoto(rs.getString("photo"));
        judge.setTitle(rs.getInt("title/discharge"));
        judge.setRole(rs.getInt("role"));
        judge.setCategory(rs.getInt("category"));
        judge.setAssignmentDate(rs.getDate("assignmentDate"));

        return judge;
    }
}
