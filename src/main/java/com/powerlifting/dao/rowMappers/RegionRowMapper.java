package com.powerlifting.dao.rowMappers;

import com.powerlifting.controllers.registered.model.Region;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionRowMapper implements RowMapper<Region>{
    @Override
    public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
        Region region = new Region();
        region.setRegionId(rs.getInt("regionId"));
        region.setName(rs.getString("name"));

        return region;
    }
}
