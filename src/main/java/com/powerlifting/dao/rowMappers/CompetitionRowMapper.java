package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Competition;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetitionRowMapper implements RowMapper<Competition> {
    @Override
    public Competition mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Competition competition = new Competition();

        competition.setId(rs.getInt("competitionId"));
        competition.setAuthor(rs.getInt("author"));
        competition.setName(rs.getString("name"));
        competition.setCity(rs.getString("city"));
        competition.setStartDate(rs.getDate("startDate"));
        competition.setEndDate(rs.getDate("endDate"));
        competition.setGender(rs.getShort("gender"));
        competition.setInfo(rs.getString("info"));
        competition.setType(rs.getInt("type"));
        competition.setBroadcasting(rs.getInt("broadcasting"));
        competition.setBroadcastingSequence(rs.getInt("broadcastingSequence"));
        competition.setBroadcastingGroup(rs.getInt("broadcastingGroup"));
        competition.setBroadcastingType(rs.getInt("broadcastingType"));

        return competition;
    }
}
