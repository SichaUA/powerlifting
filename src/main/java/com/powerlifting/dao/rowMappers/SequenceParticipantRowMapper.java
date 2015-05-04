package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Group;
import com.powerlifting.controllers.registered.model.ParticipantInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceParticipantRowMapper implements RowMapper<ParticipantInfo> {

    @Override
    public ParticipantInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParticipantInfo participantInfo = new ParticipantInfo();

        participantInfo.setGroupParticipantId(rs.getInt("groupParticipantId"));
        participantInfo.setParticipantId(rs.getInt("participantId"));
        participantInfo.setCompetition(rs.getInt("competition"));
        participantInfo.setAgeGroup(new AgeGroupRowMapper().mapRow(rs, rowNum));
        participantInfo.setCategory(rs.getInt("category"));
        participantInfo.setFrom(rs.getInt("from"));
        participantInfo.setUser(new UserRowMapper().mapRow(rs, rowNum));
        participantInfo.setWeightCategory(new WeightCategoryRowMapper().mapRow(rs, rowNum));
        participantInfo.setGroup(new GroupRowMapper().mapRow(rs, rowNum));
        participantInfo.setParticipantWeight(rs.getFloat("participantWeight"));
        participantInfo.setOrdinalNumber(rs.getInt("ordinalNumber"));
        participantInfo.setStatusId(rs.getInt("status"));
        participantInfo.setWeight(rs.getFloat("participantWeight"));
        participantInfo.setCurrentTotal(rs.getFloat("total"));
        participantInfo.setFirstSQAttempt(rs.getFloat("firstAttemptSQ"));
        participantInfo.setFirstSQAttemptStatus(rs.getInt("firstAttemptSQStatus"));
        participantInfo.setSecondSQAttempt(rs.getFloat("secondAttemptSQ"));
        participantInfo.setSecondSQAttemptStatus(rs.getInt("secondAttemptSQStatus"));
        participantInfo.setThirdSQAttempt(rs.getFloat("thirdAttemptSQ"));
        participantInfo.setThirdSQAttemptStatus(rs.getInt("thirdAttemptSQStatus"));
        participantInfo.setFirstBPAttempt(rs.getFloat("firstAttemptBP"));
        participantInfo.setFirstBPAttemptStatus(rs.getInt("firstAttemptBPStatus"));
        participantInfo.setSecondBPAttempt(rs.getFloat("secondAttemptBP"));
        participantInfo.setSecondBPAttemptStatus(rs.getInt("secondAttemptBPStatus"));
        participantInfo.setThirdBPAttempt(rs.getFloat("thirdAttemptBP"));
        participantInfo.setThirdBPAttemptStatus(rs.getInt("thirdAttemptBPStatus"));
        participantInfo.setFirstDLAttempt(rs.getFloat("firstAttemptDL"));
        participantInfo.setFirstDLAttemptStatus(rs.getInt("firstAttemptDLStatus"));
        participantInfo.setSecondDLAttempt(rs.getFloat("secondAttemptDL"));
        participantInfo.setSecondDLAttemptStatus(rs.getInt("secondAttemptDLStatus"));
        participantInfo.setThirdDLAttempt(rs.getFloat("thirdAttemptDL"));
        participantInfo.setThirdDLAttemptStatus(rs.getInt("thirdAttemptDLStatus"));

        return participantInfo;
    }
}
