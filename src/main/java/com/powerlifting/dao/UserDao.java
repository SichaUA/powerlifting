package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.Title;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.rowMappers.TitleRowMapper;
import com.powerlifting.dao.rowMappers.UserRowMapper;
import com.powerlifting.utils.CommonUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user) {
        final String sql =
                "INSERT INTO user " +
                "(email, password, firstName, secondName, middleName, gender, birthday) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getFirstName(),
                user.getSecondName(), user.getMiddleName(), user.getGender(), user.getBirthday());
    }

    public Optional<User> getUserByCredentials(User user) {
        final String sql =
                "SELECT * " +
                "FROM user u " +
                "WHERE u.email = ? AND u.password = ?";

        return CommonUtils.selectOne(jdbcTemplate, sql, new UserRowMapper(), user.getEmail(), user.getPassword());
    }

    public void participateTheCompetition(Integer competitionId, Integer userId) {
        final String sql =
                "INSERT INTO competition_participant " +
                "(user, competition) VALUES (?, ?);";

        jdbcTemplate.update(sql, userId, competitionId);
    }

    public void updateUserImage(String image, Integer userId) {
        final String sql =
                "UPDATE user " +
                "SET photo = ?" +
                "WHERE userId = ?";

        jdbcTemplate.update(sql, image, userId);
    }

    public void changeUserPassword(Integer userId, String newPassword) {
        final String sql =
                "UPDATE user " +
                "SET password = ? " +
                "WHERE userId = ?";

        jdbcTemplate.update(sql, newPassword, userId);
    }

    public List<Title> getListOfTitles() {
        final String sql =
                "SELECT * " +
                "FROM `dictionary_title/discharge` ";

        return jdbcTemplate.query(sql, new TitleRowMapper());
    }

    public void changeUserTitle(Integer userId, Integer newTitle) {
        final String sql =
                "UPDATE user " +
                "SET `dictionary_title/discharge` = ? " +
                "WHERE userId = ?";

        jdbcTemplate.update(sql, newTitle, userId);
    }

    public List<User> getAllJudgeOfCompetition(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM competition_judge cj JOIN user u ON cj.user = u.userId " +
                "WHERE cj.competition = ? ";

        return jdbcTemplate.query(sql, new UserRowMapper(), competitionId);
    }

    public void deleteJudgeFromCompetition(Integer competitionId, Integer judgeId) {
        final String sql =
                "DELETE FROM competition_judge " +
                "WHERE user = ? AND competition = ? ";

        jdbcTemplate.update(sql, judgeId, competitionId);
    }

    public List<User> getJudgesLikeWhichNotJudgeInCompetition(String text, Integer competitionId) {
        text += "%";
        final String sql =
                "SELECT * " +
                "FROM user " +
                "WHERE secondName LIKE ? " +
                    "AND userId IN (SELECT j.userId " +
                                   "FROM judge j) " +
                    "AND userId NOT IN (SELECT user " +
                                       "FROM competition_judge " +
                                       "WHERE competition = ?)";

        return jdbcTemplate.query(sql, new UserRowMapper(), text, competitionId);
    }

    public void addJudgeToCompetition(String judgeEmail, Integer competitionId) {
        final String sql =
                "INSERT INTO competition_judge (user, competition) " +
                "VALUES((SELECT userId " +
                        "FROM judge j " +
                        "WHERE j.userId = (SELECT u.userId " +
                                          "FROM user u " +
                                          "WHERE u.email = ?)), ?)";

        jdbcTemplate.update(sql, judgeEmail, competitionId);
    }
}
