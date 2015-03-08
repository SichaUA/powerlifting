package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Sequence;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceRowMapper implements RowMapper<Sequence> {
    @Override
    public Sequence mapRow(ResultSet rs, int rowNum) throws SQLException {
        Sequence sequence = new Sequence();

        sequence.setSequenceId(rs.getInt("sequenceId"));
        sequence.setCompetition(rs.getInt("competition"));
        sequence.setDate(rs.getDate("date"));
        sequence.setInfo(rs.getString("info"));

        return sequence;
    }
}
