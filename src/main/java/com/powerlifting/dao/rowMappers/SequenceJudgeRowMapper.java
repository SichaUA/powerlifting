package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.SequenceJudge;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceJudgeRowMapper implements RowMapper<SequenceJudge> {
    @Override
    public SequenceJudge mapRow(ResultSet rs, int rowNum) throws SQLException {
        SequenceJudge sequenceJudge = new SequenceJudge();

        sequenceJudge.setJudge(new JudgeRowMapper().mapRow(rs, rowNum));
        sequenceJudge.setTypeId(rs.getInt("judgeType"));

        return sequenceJudge;
    }
}
