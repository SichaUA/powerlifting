package com.powerlifting.controllers.registered.model;

public class ParticipantAddingInfo {
    private Integer ageGroup;
    private Integer category;
    private String region;
    private Boolean ownParticipation;
    private Float SQ;
    private Float BP;
    private Float DL;
    private Float total;
    private String firstCoach;
    private String personalCoach;
    private String firstAdditionalCoach;
    private String secondAdditionalCoach;

    public Integer getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(Integer ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getOwnParticipation() {
        return ownParticipation;
    }

    public void setOwnParticipation(Boolean ownParticipation) {
        this.ownParticipation = ownParticipation;
    }

    public Float getSQ() {
        return SQ;
    }

    public void setSQ(Float SQ) {
        this.SQ = SQ;
    }

    public Float getBP() {
        return BP;
    }

    public void setBP(Float BP) {
        this.BP = BP;
    }

    public Float getDL() {
        return DL;
    }

    public void setDL(Float DL) {
        this.DL = DL;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
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
}
