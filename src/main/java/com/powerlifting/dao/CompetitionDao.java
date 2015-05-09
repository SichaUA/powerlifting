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

    public Integer getCompetitionIdBySequence(Integer sequenceId) {
        final String sql =
                "SELECT s.competition " +
                "FROM sequence s " +
                "WHERE s.sequenceId = ? ";

        return jdbcTemplate.queryForObject(sql, Integer.class, sequenceId);
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

    public void deleteSequence(Integer sequenceId) {
        String sql = "DELETE FROM sequence WHERE sequenceId = ? ";

        jdbcTemplate.update(sql, sequenceId);
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

    public Integer addNewSequenceWithCategories(AddSequenceRequest addSequenceRequest, Integer competitionId) {
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

        return sequenceId;
    }

    public List<Pair<WeightCategory, AgeGroup>> getCategoriesOfSequence(Integer sequenceId) {
        final String sql =
                "SELECT * " +
                "FROM sequence_category sc JOIN dictionary_weight_category wc ON (sc.category = wc.categoryId) JOIN dictionary_age_group ag ON (sc.ageGroup = ag.groupId) " +
                "WHERE sequence = ? ";

        List<SequenceCategory> sequenceCategories = jdbcTemplate.query(sql, new SequenceCategoryRowMapper(), sequenceId);
        List<Pair<WeightCategory, AgeGroup>> pairList = new ArrayList<>();
        for(Iterator<SequenceCategory> i = sequenceCategories.iterator(); i.hasNext(); ) {
            final SequenceCategory sequenceCategory = i.next();
            pairList.add(new Pair<>(sequenceCategory.getCategory(), sequenceCategory.getAgeGroup()));
        }

        return pairList;
    }

    public Optional<Sequence> getSequenceById(Integer sequenceId) {
        final String sql =
                "SELECT * " +
                "FROM sequence " +
                "WHERE sequenceId = ? ";

        Optional<Sequence> sequence = CommonUtils.selectOne(jdbcTemplate, sql, new SequenceRowMapper(), sequenceId);
        if(sequence.isPresent()) {
            sequence.get().setCategories(getCategoriesOfSequence(sequenceId));
        }

        return sequence;
    }

    public Integer insertFirstSequenceGroup(Integer sequenceId) {
        final String sql = "INSERT INTO `group` (sequenceId, groupNum) VALUES(?, 1)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"groupId"});

                ps.setInt(1, sequenceId);

                return ps;
            }
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void insertAllSequenceParticipantToFirstGroup(Integer sequenceId, Integer groupId) {
        List<ParticipantInfo> sequenceParticipants = getAllSequenceParticipant(sequenceId);
        List<Integer> participantsOrdinalNumbers = new ArrayList<>();
        for(int i = 1; i <= sequenceParticipants.size(); i++) {
            participantsOrdinalNumbers.add(i);
        }
        Collections.shuffle(participantsOrdinalNumbers);

        final String sql =
                "INSERT INTO group_participant (groupId, participant, ordinalNumber) " +
                "VALUES (?, ?, ?) ";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, groupId);
                ps.setInt(2, sequenceParticipants.get(i).getParticipantId());
                ps.setInt(3, participantsOrdinalNumbers.get(i));
            }

            @Override
            public int getBatchSize() {
                return sequenceParticipants.size();
            }
        });
    }

    public Integer getSequenceGroupCount(Integer sequenceId) {
        final String sql =
                "SELECT count(groupNum) " +
                "FROM `group` " +
                "WHERE sequenceId = ? ";

        return jdbcTemplate.queryForObject(sql, Integer.class, sequenceId);
    }

    public List<Group> getSequenceGroups(Integer sequenceId) {
        final String sql =
                "SELECT * " +
                "FROM `group` g " +
                "WHERE g.sequenceId = ? ";

        return jdbcTemplate.query(sql, new GroupRowMapper(), sequenceId);
    }

    public List<ParticipantInfo> getAllSequenceParticipant(Integer sequenceId) {
        final String sql =
                "SELECT p.*, dr.name as fromName, u.*, da.*, dw.*, gp.groupId as participantGroup " +
                "FROM participant p JOIN dictionary_region dr ON (p.`from` = dr.regionId) JOIN user u ON (p.user = u.userId) " +
                    "JOIN dictionary_age_group da ON (p.ageGroup = da.groupId) JOIN dictionary_weight_category dw ON (p.category = dw.categoryId) " +
                    "JOIN sequence s ON (s.competition = p.competition) " +
                    "JOIN `group` g ON (g.sequenceId = s.sequenceId) " +
                    "LEFT JOIN  `group_participant` gp ON (gp.participant = p.participantId) " +
                "WHERE s.sequenceId = ? AND p.ageGroup IN (SELECT ageGroup " +
                                                          "FROM sequence s1 JOIN sequence_category sc ON (s1.sequenceId = sc.sequence) " +
                                                          "WHERE s1.sequenceId = s.sequenceId AND p.category IN (SELECT category " +
                                                                                                                "FROM sequence s2 JOIN sequence_category sc1 ON (s2.sequenceId = sc1.sequence) " +
                                                                                                                "WHERE s2.sequenceId = s1.sequenceId)) " +
                "GROUP BY p.participantId " +
                "ORDER BY u.gender, category, total DESC";

        //change groupId in participantInfoList to groupNum
        List<ParticipantInfo> participantInfoList = jdbcTemplate.query(sql, new ParticipantInfoRowMapper(), sequenceId);
        List<Group> groups = getSequenceGroups(sequenceId);

        for(Iterator<ParticipantInfo> i = participantInfoList.iterator(); i.hasNext(); ) {
            ParticipantInfo participantInfo = i.next();
            for(Iterator<Group> j = groups.iterator(); j.hasNext(); ) {
                Group group = j.next();
                if(participantInfo.getSelectedGroup() == group.getGroupId()) {
                    participantInfo.setSelectedGroup(group.getGroupNum());
                }
            }
        }

        return participantInfoList;
    }

    public void deleteGroupFromSequence(Integer sequenceId, List<Integer> groupNums) {
        final String sql =
                "DELETE FROM `group` " +
                "WHERE sequenceId = ? AND groupNum = ? ";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, sequenceId);
                ps.setInt(2, groupNums.get(i));
            }

            @Override
            public int getBatchSize() {
                return groupNums.size();
            }
        });
    }

    public void insertGroupsToSequence(Integer sequenceId, List<Integer> groups) {
        final String sql =
                "INSERT INTO `group` (sequenceId, groupNum) " +
                "VALUES (?, ?) ";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, sequenceId);
                ps.setInt(2, groups.get(i));
            }

            @Override
            public int getBatchSize() {
                return groups.size();
            }
        });
    }

    public void deleteParticipantFromGroup(Integer sequenceId, Integer participantId) {
        final String sql =
                "DELETE FROM group_participant " +
                "WHERE participant = ? AND groupId IN (SELECT g.groupId " +
                                                      "FROM `group` g " +
                                                      "WHERE g.sequenceId = ?) ";

        jdbcTemplate.update(sql, participantId, sequenceId);
    }

    public void insertParticipantToGroup(Integer sequenceId, Integer groupId, Integer participantId) {
        final String sql =
                "INSERT INTO group_participant (groupId, participant) " +
                "VALUES((SELECT g.groupId " +
                        "FROM `group` g " +
                        "WHERE g.sequenceId = ? AND g.groupNum = ?), ?) ";

        jdbcTemplate.update(sql, sequenceId, groupId, participantId);
    }

    public void updateParticipantGroup(Integer sequenceId, Integer groupNum, Integer participantId) {
        final String sql =
                "UPDATE group_participant gp SET gp.groupId = (SELECT g.groupId " +
                                                              "FROM `group` g " +
                                                              "WHERE g.sequenceId = ? AND g.groupNum = ?) " +
                "WHERE gp.participant = ? AND gp.groupId IN (SELECT g.groupId " +
                                                            "FROM `group` g " +
                                                            "WHERE g.sequenceId = ?)";

        jdbcTemplate.update(sql, sequenceId, groupNum, participantId, sequenceId);
    }

    public List<ParticipantInfo> getSequenceParticipant(Integer sequenceId) {
        final String sql =
                "SELECT * " +
                "FROM group_participant_with_attempt gpa JOIN `group` g ON (gpa.groupId = g.groupId) JOIN participant p ON (gpa.participant = p.participantId) " +
                    "JOIN user u ON (p.user = u.userId) JOIN dictionary_age_group da ON (p.ageGroup = da.groupId) " +
                    "JOIN dictionary_weight_category dw ON (p.category = dw.categoryId)\n" +
                "WHERE g.sequenceId = ? " +
                "ORDER BY p.category, gpa.ordinalNumber ";

        return jdbcTemplate.query(sql, new SequenceParticipantRowMapper(), sequenceId);
    }

    public List<ParticipantInfo> getGroupParticipants(Integer groupId) {
        final String sql =
                "SELECT * " +
                "FROM group_participant_with_attempt gpa JOIN `group` g ON (gpa.groupId = g.groupId) JOIN participant p ON (gpa.participant = p.participantId) " +
                    "JOIN user u ON (p.user = u.userId) JOIN dictionary_age_group da ON (p.ageGroup = da.groupId) " +
                    "JOIN dictionary_weight_category dw ON (p.category = dw.categoryId) " +
                "WHERE gpa.groupId = ? " +
                "ORDER BY p.category, gpa.ordinalNumber ";

        return jdbcTemplate.query(sql, new SequenceParticipantRowMapper(), groupId);
    }

    public void updateParticipantWeight(Integer groupParticipantId, Float weight) {
        final String sql =
                "UPDATE group_participant SET participantWeight = ? " +
                "WHERE groupParticipantId = ? ";

        jdbcTemplate.update(sql, weight, groupParticipantId);
    }

    public void deleteAttempt(Integer groupParticipantId, Integer attemptNumber, Integer exerciseId) {
        final String sql =
                "DELETE FROM attempt " +
                "WHERE groupParticipant = ? AND attemptNumber = ? AND exercise = ? ";

        jdbcTemplate.update(sql, groupParticipantId, attemptNumber, exerciseId);
    }

    public void setAttempt(Integer groupParticipantId, Integer attemptNumber, Float weight, Integer exerciseId, Integer statusId) {
        final String sql =
                "INSERT INTO attempt (groupParticipant, attemptNumber, weight, exercise, `status`) " +
                "VALUES(?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, groupParticipantId, attemptNumber, weight, exerciseId, statusId);
    }

    public void updateAttemptStatus(Integer groupParticipantId, Integer attemptNumber, Float weight, Integer exerciseId, Integer newStatusId) {
        final String sql =
                "UPDATE attempt SET `status` = ? " +
                "WHERE groupParticipant = ? AND attemptNumber = ? AND weight = ? AND exercise = ? ";

        jdbcTemplate.update(sql, newStatusId, groupParticipantId, attemptNumber, weight, exerciseId);
    }

    public void updateGroupParticipantStatus(Integer groupParticipantId, Integer newStatusId) {
        final String sql =
                "UPDATE group_participant SET `status` = ? " +
                "WHERE groupParticipantId = ? ";

        jdbcTemplate.update(sql, newStatusId, groupParticipantId);
    }

    public ParticipantStatus getGroupParticipantStatus(Integer groupParticipantId) {
        final String sql =
                "SELECT dp.* " +
                "FROM group_participant gp JOIN dictionary_participant_status dp ON (gp.`status` = dp.statusId) " +
                "WHERE gp.groupParticipantId = ? ";

        return jdbcTemplate.queryForObject(sql, new ParticipantStatusRowMapper(), groupParticipantId);
    }

    public void startBroadcasting(Integer competitionId) {
        final String sql =
                "UPDATE competition SET broadcasting = 1 " +
                "WHERE competitionId = ? ";

        jdbcTemplate.update(sql, competitionId);
    }

    public void stopBroadcasting(Integer competitionId) {
        final String sql =
                "UPDATE competition SET broadcasting = 0 " +
                "WHERE competitionId = ? ";

        jdbcTemplate.update(sql, competitionId);
    }

    public void updateBroadcastingInfo(Integer competitionId, Integer sequenceId, Integer groupId, Integer type) {
        final String sql =
                "UPDATE competition SET broadcastingSequence = ?, broadcastingGroup = ?, broadcastingType = ? " +
                "WHERE competitionId = ? ";

        jdbcTemplate.update(sql, sequenceId, groupId, type, competitionId);
    }
}
