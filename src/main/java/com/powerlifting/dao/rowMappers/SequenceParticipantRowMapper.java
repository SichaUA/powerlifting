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
        participantInfo.setDeclaredSQWeight(rs.getFloat("declaredSQWeight"));
        participantInfo.setDeclaredBPWeight(rs.getFloat("declaredBPWeight"));
        participantInfo.setDeclaredDLWeight(rs.getFloat("declaredDLWeight"));
        participantInfo.setCurrentTotal(rs.getFloat("total"));

        return participantInfo;
    }
}
