package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.ParticipantAddingInfo;
import com.powerlifting.controllers.registered.model.ParticipantAllInf;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.controllers.registered.model.WeightCategory;
import com.powerlifting.dao.rowMappers.ParticipantAllInfRowMapper;
import com.powerlifting.dao.rowMappers.UserRowMapper;
import com.powerlifting.dao.rowMappers.WeightCategoryRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ParticipantDao {
    private JdbcTemplate jdbcTemplate;

    public ParticipantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAllParticipantOfCompetition(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM participant p JOIN user u ON p.user = u.userId " +
                "WHERE p.competition = ?";

        return jdbcTemplate.query(sql, new UserRowMapper(), competitionId);
    }

    public List<ParticipantAllInf> getAllParticipantOfCompetitionWithAllInf(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM (participant p JOIN user u ON p.user = u.userId) JOIN dictionary_region d ON p.from = d.regionId JOIN dictionary_age_group da ON da.groupId = p.ageGroup " +
                "WHERE p.competition = ? " +
                "ORDER BY gender, category, total DESC ";

        return jdbcTemplate.query(sql, new ParticipantAllInfRowMapper(), competitionId);
    }

    public void deleteParticipantFromCompetition(Integer participantId, Integer competitionId) {
        final String sql =
                "DELETE FROM participant " +
                "WHERE user = ? AND competition = ?";

        jdbcTemplate.update(sql, participantId, competitionId);
    }

    public List<User> getUsersLikeWhichNotInCompetition(String text, Integer competitionId, Integer limit) {
        text = "%" + text + "%";

        final String sql =
                "SELECT * " +
                "FROM user u " +
                "WHERE CONCAT(u.secondName, \" \", u.firstName, \" \", u.middleName) LIKE ? AND " +
                "u.userId NOT IN (SELECT p.user " +
                                 "FROM participant p " +
                                 "WHERE p.competition = ?) " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, new UserRowMapper(), text, competitionId, limit);
    }

    public void addParticipantToCompetition(Integer userId, Integer competitionId, ParticipantAddingInfo participantAddingInfo) {
        final String sql =
                "INSERT INTO participant (user, competition, ageGroup, category, `from`, squat, benchPress, deadlift, total, ownParticipation, firstCoach, " +
                                            "personalCoach, firstAdditionalCoach, secondAdditionalCoach) " +
                "VALUES (?, ?, ?, ?, (SELECT regionId " +
                                     "FROM dictionary_region " +
                                     "WHERE name = ?), ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, userId, competitionId, participantAddingInfo.getAgeGroup(), participantAddingInfo.getCategory(), participantAddingInfo.getRegion(),
                participantAddingInfo.getSQ(), participantAddingInfo.getBP(), participantAddingInfo.getDL(), participantAddingInfo.getTotal(),
                participantAddingInfo.getOwnParticipation()? 1 : 0, participantAddingInfo.getFirstCoach(), participantAddingInfo.getPersonalCoach(),
                participantAddingInfo.getFirstAdditionalCoach(), participantAddingInfo.getSecondAdditionalCoach());
    }

    public List<WeightCategory> getAllWeightCategories() {
        final String sql =
                "SELECT * " +
                "FROM dictionary_weight_category ";

        return jdbcTemplate.query(sql, new WeightCategoryRowMapper());
    }

    public List<WeightCategory> getWeightCategoriesUserInCompetition(Integer competitionId) {
        final String sql =
                "SELECT DISTINCT categoryId, gender, minWeight, maxWeight, name " +
                "FROM dictionary_weight_category d LEFT JOIN participant p ON (d.categoryId = p.category) " +
                "WHERE p.competition = ? ";

        return jdbcTemplate.query(sql, new WeightCategoryRowMapper(), competitionId);
    }
}
