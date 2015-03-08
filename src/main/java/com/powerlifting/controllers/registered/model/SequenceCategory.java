package com.powerlifting.controllers.registered.model;

public class SequenceCategory {
    private Integer sequence;
    private WeightCategory category;
    private AgeGroup ageGroup;

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public WeightCategory getCategory() {
        return category;
    }

    public void setCategory(WeightCategory category) {
        this.category = category;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }
}
