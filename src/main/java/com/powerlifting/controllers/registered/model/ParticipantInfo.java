package com.powerlifting.controllers.registered.model;

public class ParticipantInfo {
    private Integer participantId;
    private Integer competition;
    private AgeGroup ageGroup;
    private Integer category;
    private Integer from;
    private String fromName;
    private Float squat;
    private Float benchPress;
    private Float deadLift;
    private Float total;
    private Integer ownParticipation;
    private String firstCoach;
    private String personalCoach;
    private String firstAdditionalCoach;
    private String secondAdditionalCoach;
    private User user;
    private WeightCategory weightCategory;
    private Integer selectedGroup;

    private Integer groupParticipantId;
    private Group group;
    private Float participantWeight;
    private Integer ordinalNumber;
    private Integer statusId;
    private Float weight;
    private Float declaredSQWeight;
    private Float declaredBPWeight;
    private Float declaredDLWeight;
    private Float currentTotal;

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public Integer getCompetition() {
        return competition;
    }

    public void setCompetition(Integer competition) {
        this.competition = competition;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Float getSquat() {
        return squat;
    }

    public void setSquat(Float squat) {
        this.squat = squat;
    }

    public Float getBenchPress() {
        return benchPress;
    }

    public void setBenchPress(Float benchPress) {
        this.benchPress = benchPress;
    }

    public Float getDeadLift() {
        return deadLift;
    }

    public void setDeadLift(Float deadLift) {
        this.deadLift = deadLift;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getOwnParticipation() {
        return ownParticipation;
    }

    public void setOwnParticipation(Integer ownParticipation) {
        this.ownParticipation = ownParticipation;
    }

    public String getFirstCoach() {
        return firstCoach;
    }

    public void setFirstCoach(String firstCoach) {
        this.firstCoach = firstCoach;
    }

    public String getPersonalCoach() {
        return personalCoach;
    }

    public void setPersonalCoach(String personalCoach) {
        this.personalCoach = personalCoach;
    }

    public String getFirstAdditionalCoach() {
        return firstAdditionalCoach;
    }

    public void setFirstAdditionalCoach(String firstAdditionalCoach) {
        this.firstAdditionalCoach = firstAdditionalCoach;
    }

    public String getSecondAdditionalCoach() {
        return secondAdditionalCoach;
    }

    public void setSecondAdditionalCoach(String secondAdditionalCoach) {
        this.secondAdditionalCoach = secondAdditionalCoach;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WeightCategory getWeightCategory() {
        return weightCategory;
    }

    public void setWeightCategory(WeightCategory weightCategory) {
        this.weightCategory = weightCategory;
    }

    public Integer getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Integer selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }

    public void setOrdinalNumber(Integer ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public Integer getGroupParticipantId() {
        return groupParticipantId;
    }

    public void setGroupParticipantId(Integer groupParticipantId) {
        this.groupParticipantId = groupParticipantId;
    }

    public Float getParticipantWeight() {
        return participantWeight;
    }

    public void setParticipantWeight(Float participantWeight) {
        this.participantWeight = participantWeight;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getDeclaredSQWeight() {
        return declaredSQWeight;
    }

    public void setDeclaredSQWeight(Float declaredSQWeight) {
        this.declaredSQWeight = declaredSQWeight;
    }

    public Float getDeclaredBPWeight() {
        return declaredBPWeight;
    }

    public void setDeclaredBPWeight(Float declaredBPWeight) {
        this.declaredBPWeight = declaredBPWeight;
    }

    public Float getDeclaredDLWeight() {
        return declaredDLWeight;
    }

    public void setDeclaredDLWeight(Float declaredDLWeight) {
        this.declaredDLWeight = declaredDLWeight;
    }

    public Float getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(Float currentTotal) {
        this.currentTotal = currentTotal;
    }
}
