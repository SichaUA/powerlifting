package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.ParticipantAllInf;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantAllInfRowMapper implements RowMapper<ParticipantAllInf>{
    @Override
    public ParticipantAllInf mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParticipantAllInf participantAllInf = new ParticipantAllInf();

        participantAllInf.setParticipantId(rs.getInt("participantId"));
        participantAllInf.setUserId(rs.getInt("userId"));
        participantAllInf.setCompetition(rs.getInt("competition"));
        participantAllInf.setCategory(rs.getInt("category"));
        participantAllInf.setFrom(rs.getInt("from"));
        participantAllInf.setFromName(rs.getString("name"));
        participantAllInf.setSquat(rs.getFloat("squat"));
        participantAllInf.setBenchPress(rs.getFloat("benchPress"));
        participantAllInf.setDeadLift(rs.getFloat("deadlift"));
        participantAllInf.setTotal(rs.getFloat("total"));
        participantAllInf.setOwnParticipation(rs.getInt("ownParticipation"));
        participantAllInf.setEmail(rs.getString("email"));
        participantAllInf.setPassword(rs.getString("password"));
        participantAllInf.setFirstName(rs.getString("firstName"));
        participantAllInf.setSecondName(rs.getString("secondName"));
        participantAllInf.setMiddleName(rs.getString("middleName"));
        participantAllInf.setGender(rs.getInt("gender"));
        participantAllInf.setBirthday(rs.getDate("birthday"));
        participantAllInf.setPhoto(rs.getString("photo"));
        participantAllInf.setTitle(rs.getInt("title/discharge"));
        participantAllInf.setRole(rs.getInt("role"));

        return participantAllInf;
    }
}
