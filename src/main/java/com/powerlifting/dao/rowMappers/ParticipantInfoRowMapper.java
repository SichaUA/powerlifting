package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.ParticipantInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantInfoRowMapper implements RowMapper<ParticipantInfo> {

    @Override
    public ParticipantInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParticipantInfo participantInfo = new ParticipantInfo();

        participantInfo.setParticipantId(rs.getInt("participantId"));
        participantInfo.setCompetition(rs.getInt("competition"));
        participantInfo.setAgeGroup(new AgeGroupRowMapper().mapRow(rs, rowNum));
        participantInfo.setCategory(rs.getInt("category"));
        participantInfo.setFrom(rs.getInt("from"));
        participantInfo.setFromName(rs.getString("fromName"));
        participantInfo.setSquat(rs.getFloat("squat"));
        participantInfo.setBenchPress(rs.getFloat("benchPress"));
        participantInfo.setDeadLift(rs.getFloat("deadlift"));
        participantInfo.setTotal(rs.getFloat("total"));
        participantInfo.setOwnParticipation(rs.getInt("ownParticipation"));
        participantInfo.setFirstCoach(rs.getString("firstCoach"));
        participantInfo.setPersonalCoach(rs.getString("personalCoach"));
        participantInfo.setFirstAdditionalCoach(rs.getString("firstAdditionalCoach"));
        participantInfo.setSecondAdditionalCoach(rs.getString("secondAdditionalCoach"));
        participantInfo.setUser(new UserRowMapper().mapRow(rs, rowNum));
        participantInfo.setWeightCategory(new WeightCategoryRowMapper().mapRow(rs, rowNum));
        participantInfo.setSelectedGroup(rs.getInt("participantGroup"));

        return participantInfo;
    }
}
