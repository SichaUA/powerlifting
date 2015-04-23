package com.powerlifting.dao.rowMappers;


import com.powerlifting.controllers.registered.model.ParticipantStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantStatusRowMapper implements RowMapper<ParticipantStatus> {
    @Override
    public ParticipantStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        ParticipantStatus participantStatus = new ParticipantStatus();

        participantStatus.setStatusId(rs.getInt("statusId"));
        participantStatus.setStatusName(rs.getString("statusName"));

        return participantStatus;
    }
}
