package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.CategoryParticipantCount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryParticipantCountRowMapper implements RowMapper<CategoryParticipantCount> {
    @Override
    public CategoryParticipantCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoryParticipantCount categoryParticipantCount = new CategoryParticipantCount();

        categoryParticipantCount.setCategory(rs.getInt("category"));
        categoryParticipantCount.setAgeGroup(rs.getInt("ageGroup"));
        categoryParticipantCount.setCount(rs.getInt("count"));

        return categoryParticipantCount;
    }
}
