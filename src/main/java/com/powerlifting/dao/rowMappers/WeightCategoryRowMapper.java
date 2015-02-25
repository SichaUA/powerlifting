package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.WeightCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeightCategoryRowMapper implements RowMapper<WeightCategory>{
    @Override
    public WeightCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        WeightCategory category = new WeightCategory();
        category.setCategoryId(rs.getInt("categoryId"));
        category.setGender(rs.getInt("gender"));
        category.setMinWeight(rs.getInt("minWeight"));
        category.setMaxWeight(rs.getInt("maxWeight"));
        category.setName(rs.getString("name"));

        return category;
    }
}
