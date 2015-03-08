package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.AgeGroupWithWeightCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgeGroupWithWeightCategoryRowMapper implements RowMapper<AgeGroupWithWeightCategory> {
    @Override
    public AgeGroupWithWeightCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        AgeGroupWithWeightCategory ageGroupWithWeightCategory = new AgeGroupWithWeightCategory();

//        ageGroupWithWeightCategory.setId(rs.getInt("number"));
        ageGroupWithWeightCategory.setAgeGroup(new AgeGroupRowMapper().mapRow(rs, rowNum));
        ageGroupWithWeightCategory.setWeightCategory(new WeightCategoryRowMapper().mapRow(rs, rowNum));
        ageGroupWithWeightCategory.setParticipantCount(rs.getInt("participants"));

        return ageGroupWithWeightCategory;
    }
}
