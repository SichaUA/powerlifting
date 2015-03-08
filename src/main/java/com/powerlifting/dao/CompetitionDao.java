package com.powerlifting.dao;

import com.powerlifting.controllers.registered.model.*;
import com.powerlifting.dao.rowMappers.*;
import com.powerlifting.utils.CommonUtils;
import javafx.util.Pair;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class CompetitionDao {
    private JdbcTemplate jdbcTemplate;

    public CompetitionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Competition> getAllCompetitions() {
        final String sql = "SELECT *" +
                           "FROM competition c";

        return jdbcTemplate.query(sql, new CompetitionRowMapper());
    }

    public List<Competition> getCurrentCompetitions() {
        final String sql = "SELECT *" +
                           "FROM competition c " +
                           "WHERE c.endDate > ?";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), new Date());
    }

    public List<Competition> getPastCompetitions() {
        final String sql = "SELECT *" +
                           "FROM competition c " +
                           "WHERE c.endDate < ?";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), new Date());
    }

    public Optional<Competition> getCompetition(Integer competitionId) {
        final String sql = "SELECT * " +
                           "FROM competition c " +
                           "WHERE c.competitionId = ?";

        return CommonUtils.selectOne(jdbcTemplate, sql, new CompetitionRowMapper(), competitionId);
    }

    public List<Competition> getCompetitionsCreatedByUser(Integer userId) {
        final String sql = "SELECT * " +
                           "FROM competition c " +
                           "WHERE c.author = ?";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), userId);
    }

    public List<Competition> getCompetitionsUserParticipate(Integer userId) {
        final String sql = "SELECT * " +
                           "FROM competition c " +
                           "WHERE c.competitionId IN(SELECT cp.competition " +
                           "FROM participant cp " +
                           "WHERE cp.user = ?)";

        return jdbcTemplate.query(sql, new CompetitionRowMapper(), userId);
    }

    public Integer createNewCompetitionReturningId(Competition competition, Integer userId) {
        final String sql = "INSERT INTO competition " +
                           "(author, city, name, startDate, endDate, gender, info, type) " +
                           "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"competitionId"});

                ps.setInt(1, userId);
                ps.setString(2, competition.getCity());
                ps.setString(3, competition.getName());
                ps.setDate(4, competition.getStartDate());
                ps.setDate(5, competition.getEndDate());
                ps.setInt(6, competition.getGender());
                ps.setString(7, competition.getInfo());
                ps.setInt(8, competition.getType());

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void addAgeGroupsToCompetition(Integer competitionId, List<Integer> ageGroups) {
        final String sql = "INSERT INTO competition_age_group (competition, ageGroup) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, competitionId);
                ps.setInt(2, ageGroups.get(i));
            }

            @Override
            public int getBatchSize() {
                return ageGroups.size();
            }
        });
    }

    public void deleteCompetition(Integer competitionId) {
        String sql = "DELETE FROM competition WHERE competitionId = ?";

        jdbcTemplate.update(sql, competitionId);
    }

    public List<String> getCompetitionAgeGroupsById(Integer ageGroupId) {
        final String sql =
                "SELECT description " +
                "FROM dictionary_age_group " +
                "WHERE groupId IN (SELECT ageGroup " +
                                  "FROM competition_age_group " +
                                  "WHERE competition = ?) ";

        return jdbcTemplate.queryForList(sql, String.class, ageGroupId);
    }

    public List<AgeGroup> getAgeGroupsOfCompetition(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM dictionary_age_group " +
                "WHERE groupId IN (SELECT ageGroup " +
                                  "FROM competition_age_group " +
                                  "WHERE competition = ?) ";

        return jdbcTemplate.query(sql, new AgeGroupRowMapper(), competitionId);
    }

    public List<AgeGroup> getAllAgeGroups() {
        final String sql =
                "SELECT * " +
                "FROM dictionary_age_group";

        return jdbcTemplate.query(sql, new AgeGroupRowMapper());
    }

    private List<SequenceCategory> getSequenceCategories(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM sequence_category sc JOIN dictionary_weight_category d ON (sc.category = d.categoryId) JOIN dictionary_age_group da ON da.groupId = sc.ageGroup " +
                "WHERE sequence IN (SELECT sequenceId " +
                                 "FROM sequence s " +
                                 "WHERE s.competition = ?)";

        List<SequenceCategory> sequenceCategories = jdbcTemplate.query(sql, new SequenceCategoryRowMapper(), competitionId);
        List<CategoryParticipantCount> categoryParticipantCounts = getParticipantCountInCategory(competitionId);
        for(Iterator<SequenceCategory> i = sequenceCategories.iterator(); i.hasNext(); ) {
            SequenceCategory sequenceCategory = i.next();
            for(Iterator<CategoryParticipantCount> j = categoryParticipantCounts.iterator(); j.hasNext(); ) {
                CategoryParticipantCount categoryParticipantCount = j.next();
                if(categoryParticipantCount.getCategory() == sequenceCategory.getCategory().getCategoryId() &&
                        categoryParticipantCount.getAgeGroup() == sequenceCategory.getAgeGroup().getGroupId()) {
                    sequenceCategory.getCategory().setParticipantCount(categoryParticipantCount.getCount());
                }
            }
        }

        return sequenceCategories;
    }

    private List<CategoryParticipantCount> getParticipantCountInCategory(Integer competitionId) {
        final String sql =
                "SELECT p.category, p.ageGroup, count(1) as \"count\" " +
                "FROM participant p " +
                "WHERE p.competition = ? " +
                "GROUP BY p.category ";
        return jdbcTemplate.query(sql, new CategoryParticipantCountRowMapper(), competitionId);
    }

    public List<Sequence> getCompetitionSequences(Integer competitionId) {
        final String sql =
                "SELECT * " +
                "FROM sequence " +
                "WHERE competition = ? ";

        List<Sequence> sequences = jdbcTemplate.query(sql, new SequenceRowMapper(), competitionId);

        List<SequenceCategory> sequenceCategories = getSequenceCategories(competitionId);
        for(Iterator<Sequence> i = sequences.iterator(); i.hasNext(); ) {
            Sequence sequence = i.next();
            List<Pair<WeightCategory, AgeGroup>> categories = new ArrayList<>();

            for(Iterator<SequenceCategory> j = sequenceCategories.iterator(); j.hasNext(); ) {
                SequenceCategory sequenceCategory = j.next();

                if(sequence.getSequenceId() == sequenceCategory.getSequence()) {
                    categories.add(new Pair<>(sequenceCategory.getCategory(), sequenceCategory.getAgeGroup()));
                }
            }

            sequence.setCategories(categories);
        }

        return sequences;
    }

    public List<WeightCategory> getCompetitionWeightCategories(Integer competitionId, Integer[] ageGroups) {
        final String sql =
                "SELECT * " +
                "FROM dictionary_weight_category dw JOIN sequence_category sc ON dw.categoryId = sc.category JOIN sequence s ON s.sequenceId = sc.sequence " +
                "WHERE s.competition = ? AND sc.ageGroup IN (?) ";

        return jdbcTemplate.query(sql, new WeightCategoryRowMapper(), competitionId, ageGroups);
    }

    public List<AgeGroupWithWeightCategory> getAllAgeGroupAndWeightCategoriesNotUsedInSequences(Integer competitionId) {
        final String sql =
                "SELECT da.groupId, da.`group`, da.description, dw.categoryId, dw.gender, dw.minWeight, dw.maxWeight, dw.name, count(1) as participants " +
                "FROM dictionary_age_group da JOIN participant p ON da.groupId = p.ageGroup JOIN dictionary_weight_category dw ON dw.categoryId = p.category " +
                "WHERE p.competition = ? AND (p.ageGroup NOT IN (SELECT ageGroup " +
                                                                "FROM sequence_category sc " +
                                                                "WHERE sc.category = p.category AND sc.sequence IN (SELECT sequenceId " +
                                                                                                                    "FROM sequence s " +
                                                                                                                    "WHERE s.competition = p.competition))) " +
                "GROUP BY da.groupId, dw.categoryId ";

        return jdbcTemplate.query(sql, new AgeGroupWithWeightCategoryRowMapper(), competitionId);
    }

    public void addNewSequenceWithCategories(AddSequenceRequest addSequenceRequest, Integer competitionId) {
//        Add new sequence returning generated sequenceId
        final String sql =
                "INSERT INTO sequence (competition, date, info)\n" +
                "VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"competitionId"});

                ps.setInt(1, competitionId);
                ps.setDate(2, new java.sql.Date(addSequenceRequest.getDate().getTime()));
                ps.setString(3, addSequenceRequest.getInfo());

                return ps;
            }
        }, keyHolder);
        final Integer sequenceId = keyHolder.getKey().intValue();

//        Add list if sequence_categories
        final String sql2 = "INSERT INTO sequence_category (sequence, category, ageGroup) VALUES (?, ?, ?)";

        final List<AgeGroupWithWeightCategory> ageGroupWithWeightCategories = addSequenceRequest.getSelectedElements();

        jdbcTemplate.batchUpdate(sql2, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                final AgeGroupWithWeightCategory category = ageGroupWithWeightCategories.get(i);
                ps.setInt(1, sequenceId);
                ps.setInt(2, category.getWeightCategory().getCategoryId());
                ps.setInt(3, category.getAgeGroup().getGroupId());
            }

            @Override
            public int getBatchSize() {
                return ageGroupWithWeightCategories.size();
            }
        });
    }
}
