package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.JudgeType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JudgeTypeRowMapper implements RowMapper<JudgeType> {

    @Override
    public JudgeType mapRow(ResultSet rs, int rowNum) throws SQLException {
        JudgeType judgeType = new JudgeType();

        judgeType.setTypeId(rs.getInt("typeId"));
        judgeType.setName(rs.getString("name"));

        return judgeType;
    }
}
