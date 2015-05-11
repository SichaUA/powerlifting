package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.JudgeAllInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JudgeAllInfoRowMapper implements RowMapper<JudgeAllInfo> {
    @Override
    public JudgeAllInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        JudgeAllInfo judgeAllInfo = new JudgeAllInfo();

        judgeAllInfo.setUser(new UserRowMapper().mapRow(rs, rowNum));
        judgeAllInfo.setJudge(new JudgeRowMapper().mapRow(rs, rowNum));
        judgeAllInfo.setSequenceJudge(new SequenceJudgeRowMapper().mapRow(rs, rowNum));
        judgeAllInfo.setJudgeType(new JudgeTypeRowMapper().mapRow(rs, rowNum));
        judgeAllInfo.setJudgeCategory(new JudgeCategoryRowMapper().mapRow(rs, rowNum));

        return judgeAllInfo;
    }
}
