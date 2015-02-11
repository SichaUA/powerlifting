package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Title;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TitleRowMapper implements RowMapper<Title> {
    @Override
    public Title mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Title title = new Title();

        title.setTitleId(rs.getInt("id"));
        title.setName(rs.getString("name"));

        return title;
    }
}
