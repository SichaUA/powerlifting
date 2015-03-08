package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.Region;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.rowMappers.RegionRowMapper;
import com.powerlifting.dao.rowMappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AdminDao {
    private JdbcTemplate jdbcTemplate;

    public AdminDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public List<User> getModerRequests() {
//        final String sql = "SELECT * " +
//                           "FROM user u " +
//                           "WHERE u.role = 1";
//
//        return jdbcTemplate.query(sql, new UserRowMapper());
//    }

    public void assignUserToNewStatus(String email, int status) {
        final String sql = "UPDATE user u " +
                           "SET u.role = ? " +
                           "WHERE u.email = ?";

        jdbcTemplate.update(sql, status, email);
    }

    public List<Region> getRegionsLike(String term, Integer limit) {
        if(!term.isEmpty()){
            term += "%";
        }
        final String sql =
                "SELECT * " +
                "FROM dictionary_region " +
                "WHERE name LIKE ? " +
                "LIMIT ? ";

        return jdbcTemplate.query(sql, new RegionRowMapper(), term, limit);
    }

    public void deleteRegion(Region region) {
        final String sql =
                "DELETE FROM dictionary_region " +
                "WHERE regionId = ? ";

        jdbcTemplate.update(sql, region.getRegionId());
    }

    public void addNewRegion(String regionName) {
        final String sql = "INSERT INTO dictionary_region (name) VALUES (?)";

        jdbcTemplate.update(sql, regionName);
    }
}
