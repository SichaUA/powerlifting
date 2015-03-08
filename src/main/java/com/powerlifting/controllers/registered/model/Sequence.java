package com.powerlifting.controllers.registered.model;

import javafx.util.Pair;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Sequence {
    private Integer sequenceId;
    private Integer competition;
    private Date date;
    private String info;
    private List< Pair<WeightCategory, AgeGroup>> categories;

    public Integer getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(Integer sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getCompetition() {
        return competition;
    }

    public void setCompetition(Integer competition) {
        this.competition = competition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Pair<WeightCategory, AgeGroup>> getCategories() {
        return categories;
    }

    public void setCategories(List<Pair<WeightCategory, AgeGroup>> categories) {
        this.categories = categories;
    }
}
