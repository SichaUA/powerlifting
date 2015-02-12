package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category>{
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category category = new Category();

        category.setCategoryId(rs.getInt("categoryId"));
        category.setCategory(rs.getString("category"));

        return category;
    }
}
