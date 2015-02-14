package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.Category;
import com.powerlifting.controllers.registered.model.Judge;
import com.powerlifting.controllers.registered.model.User;
import com.powerlifting.dao.rowMappers.CategoryRowMapper;
import com.powerlifting.dao.rowMappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class JudgeDao {
    private JdbcTemplate jdbcTemplate;

    public JudgeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
        text = "%" + text + "%";
        final String sql =
                "SELECT * " +
                        "FROM user " +
                        "WHERE CONCAT(secondName, \" \", firstName, \" \", middleName, \" \", email) LIKE ? " +
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

    public List<Category> getAllCategory() {
        final String sql =
                "SELECT * " +
                "FROM dictionary_judge_category ";

        return jdbcTemplate.query(sql, new CategoryRowMapper());
    }


    public void assignUserToJudge(String userEmail, Integer categoryId, Date assignmentDate) {
        final String sql =
                "INSERT INTO judge (userId, category, assignmentDate) " +
                "VALUES ((SELECT u.userId " +
                         "FROM user u " +
                         "WHERE u.email = ?), ?, ?)";

        jdbcTemplate.update(sql, userEmail, categoryId, assignmentDate);
    }

    public void createNewJudgeAsUser(Judge judge) {
        final String sql =
                "INSERT INTO user (email, firstName, secondName, middleName, password, birthday, gender) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?); ";

        jdbcTemplate.update(sql, judge.getEmail(), judge.getFirstName(), judge.getSecondName(), judge.getMiddleName(),
                            judge.getPassword(), judge.getBirthday(), judge.getGender());
    }
}
