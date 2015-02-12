package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.AgeGroup;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgeGroupRowMapper implements RowMapper<AgeGroup>{
    @Override
    public AgeGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        AgeGroup ageGroup = new AgeGroup();

        ageGroup.setGroupId(rs.getInt("groupId"));
        ageGroup.setGroup(rs.getString("group"));
        ageGroup.setDescription(rs.getString("description"));

        return ageGroup;
    }
}
