package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.ReportParticipantInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportParticipantInfoRowMapper implements RowMapper<ReportParticipantInfo> {
    @Override
    public ReportParticipantInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        ReportParticipantInfo reportParticipantInfo = new ReportParticipantInfo();

        reportParticipantInfo.setParticipantStatus(rs.getInt("participantStatus"));
        reportParticipantInfo.setName(rs.getString("participantName"));
        reportParticipantInfo.setBirthday(rs.getDate("birthday"));
        reportParticipantInfo.setTitle(rs.getString("title"));
        reportParticipantInfo.setFrom(rs.getString("region"));
        reportParticipantInfo.setOwnWeight(rs.getFloat("ownWeight"));
        reportParticipantInfo.setWilks(rs.getFloat("wilks"));
        reportParticipantInfo.setSQ(rs.getFloat("SQ"));
        reportParticipantInfo.setBP(rs.getFloat("BP"));
        reportParticipantInfo.setDL(rs.getFloat("DL"));
        reportParticipantInfo.setTotalSum(rs.getFloat("totalSum"));
        reportParticipantInfo.setTotalWilks(rs.getFloat("totalWilks"));
        reportParticipantInfo.setCoaches(rs.getString("coaches"));

        return reportParticipantInfo;
    }
}
