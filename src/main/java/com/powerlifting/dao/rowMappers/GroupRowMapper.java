package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group group = new Group();

        group.setGroupId(rs.getInt("groupId"));
        group.setSequenceId(rs.getInt("sequenceId"));
        group.setGroupNum(rs.getInt("groupNum"));

        return group;
    }
}
