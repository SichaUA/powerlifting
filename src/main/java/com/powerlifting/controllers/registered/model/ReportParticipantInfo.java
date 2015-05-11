package com.powerlifting.controllers.registered.model;


import java.sql.Date;

public class ReportParticipantInfo {
    private Integer participantStatus;

    private Integer number;
    private String name;
    private Date birthday;
    private String title;
    private String from;
    private Float ownWeight;
    private Float wilks;
    private Float SQ;
    private Float BP;
    private Float DL;
    private Float totalSum;
    private Float totalWilks;
    private Integer points;
    private String coaches;

    public Integer getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(Integer participantStatus) {
        this.participantStatus = participantStatus;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Float getOwnWeight() {
        return ownWeight;
    }

    public void setOwnWeight(Float ownWeight) {
        this.ownWeight = ownWeight;
    }

    public Float getWilks() {
        return wilks;
    }

    public void setWilks(Float wilks) {
        this.wilks = wilks;
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

    public Float getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Float totalSum) {
        this.totalSum = totalSum;
    }

    public Float getTotalWilks() {
        return totalWilks;
    }

    public void setTotalWilks(Float totalWilks) {
        this.totalWilks = totalWilks;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getCoaches() {
        return coaches;
    }

    public void setCoaches(String coaches) {
        this.coaches = coaches;
    }
}
