package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.*;
import com.powerlifting.dao.rowMappers.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public List<User> getJudgesLikeWhichNotJudgeInCompetition(String text, Integer competitionId, Integer limit) {
        text = "%" + text + "%";
        final String sql =
                "SELECT * " +
                "FROM user u " +
                "WHERE CONCAT(secondName, \" \", firstName, \" \", middleName) LIKE ? " +
                "AND userId IN (SELECT j.userId " +
                               "FROM judge j) " +
                "AND userId NOT IN (SELECT user " +
                                   "FROM competition_judge " +
                                   "WHERE competition = ?) " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, new UserRowMapper(), text, competitionId, limit);
    }

    public void addJudgeToCompetition(Integer judgeId, Integer competitionId) {
        final String sql =
                "INSERT INTO competition_judge (user, competition) " +
                "VALUES(?, ?)";

        jdbcTemplate.update(sql, judgeId, competitionId);
    }

    public List<JudgeCategory> getAllCategory() {
        final String sql =
                "SELECT * " +
                "FROM dictionary_judge_category ";

        return jdbcTemplate.query(sql, new JudgeCategoryRowMapper());
    }


    public void assignUserToJudge(Integer userId, Integer categoryId, Date assignmentDate) {
        final String sql =
                "INSERT INTO judge (userId, category, assignmentDate) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, userId, categoryId, assignmentDate);
    }

    public Integer createNewJudgeAsUserReturningId(Judge judge) {
        final String sql =
                "INSERT INTO user (email, firstName, secondName, middleName, password, birthday, gender) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?); ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"userId"});

                ps.setString(1, judge.getEmail().isEmpty() ? null : judge.getEmail());
                ps.setString(2, judge.getFirstName());
                ps.setString(3, judge.getSecondName());
                ps.setString(4, judge.getMiddleName());
                ps.setString(5, judge.getPassword());
                ps.setDate(6, new java.sql.Date(judge.getBirthday().getTime()));
                ps.setInt(7, judge.getGender());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<SequenceJudge> getJudgesOfSequence(Integer sequenceId) {
        final String sql =
                "SELECT * " +
                "FROM judge j JOIN sequence_judge sj ON j.userId = sj.judge JOIN user u ON j.userId = u.userId " +
                "WHERE sj.sequenceId = ? ";

        return jdbcTemplate.query(sql, new SequenceJudgeRowMapper(), sequenceId);
    }

    public List<Judge> getCompetitionJudgeWhichNotInSequence(Integer sequenceId) {
        final String sql =
                "SELECT * " +
                "FROM sequence s JOIN competition_judge cj ON (s.competition = cj.competition) JOIN user u ON (cj.user = u.userId) JOIN judge j ON (j.userId = u.userId) " +
                "WHERE s.sequenceId = ? AND cj.user NOT IN " +
                    "(SELECT judge " +
                    " FROM sequence_judge sj " +
                    " WHERE sj.sequenceId = s.sequenceId" +
                    ")";

        return jdbcTemplate.query(sql, new JudgeRowMapper(), sequenceId);
    }

    public List<JudgeType> getAllJudgeTypes() {
        final String sql =
                "SELECT * " +
                "FROM dictionary_judge_type ";

        return jdbcTemplate.query(sql, new JudgeTypeRowMapper());
    }

    public void addJudgeToSequence(Integer sequenceId, Integer judgeId, Integer typeId) {
        final String sql = "REPLACE INTO sequence_judge (sequenceId, judge, judgeType) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, sequenceId, judgeId, typeId);
    }

    public void deleteJudgeFromSequence(Integer sequenceId, Integer judgeId) {
        final String sql =
                "DELETE FROM sequence_judge " +
                "WHERE sequenceId = ? AND judge = ?";

        jdbcTemplate.update(sql, sequenceId, judgeId);
    }

    public List<JudgeAllInfo> getAllSequenceJudges(Integer sequenceId) {
        final String sql =
                "SELECT *\n" +
                "FROM sequence_judge sj JOIN judge j ON(sj.judge = j.userId) JOIN user u ON (j.userId = u.userId)\n" +
                "\tJOIN dictionary_judge_type djt ON (djt.typeId = sj.judgeType)\n" +
                "\tJOIN dictionary_judge_category djc ON (j.category = djc.categoryId)\n" +
                "WHERE sj.sequenceId = ?\n" +
                "ORDER BY sj.judgeType\n";

        return jdbcTemplate.query(sql, new JudgeAllInfoRowMapper(), sequenceId);
    }
}
