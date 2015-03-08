package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.SequenceCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceCategoryRowMapper implements RowMapper<SequenceCategory> {
    @Override
    public SequenceCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        SequenceCategory sequenceCategory = new SequenceCategory();

        sequenceCategory.setSequence(rs.getInt("sequence"));
        sequenceCategory.setCategory(new WeightCategoryRowMapper().mapRow(rs, rowNum));
        sequenceCategory.setAgeGroup(new AgeGroupRowMapper().mapRow(rs, rowNum));

        return sequenceCategory;
    }
}
