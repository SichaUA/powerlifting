package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.JudgeCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JudgeCategoryRowMapper implements RowMapper<JudgeCategory>{
    @Override
    public JudgeCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        JudgeCategory category = new JudgeCategory();

        category.setCategoryId(rs.getInt("categoryId"));
        category.setCategory(rs.getString("category"));

        return category;
    }
}
