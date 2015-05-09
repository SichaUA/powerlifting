package com.powerlifting.controllers.registered.model;


import java.sql.Date;

public class Competition {
    private Integer id;
    private Integer author;
    private String name;
    private String city;
    private Date startDate;
    private Date endDate;
    private Short gender;
    private String info;
    private Integer type;
    private Integer broadcasting;
    private Integer broadcastingSequence;
    private Integer broadcastingGroup;
    private Integer broadcastingType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBroadcasting() {
        return broadcasting;
    }

    public void setBroadcasting(Integer broadcasting) {
        this.broadcasting = broadcasting;
    }

    public Integer getBroadcastingSequence() {
        return broadcastingSequence;
    }

    public void setBroadcastingSequence(Integer broadcastingSequence) {
        this.broadcastingSequence = broadcastingSequence;
    }

    public Integer getBroadcastingGroup() {
        return broadcastingGroup;
    }

    public void setBroadcastingGroup(Integer broadcastingGroup) {
        this.broadcastingGroup = broadcastingGroup;
    }

    public Integer getBroadcastingType() {
        return broadcastingType;
    }

    public void setBroadcastingType(Integer broadcastingType) {
        this.broadcastingType = broadcastingType;
    }
}
