package com.powerlifting.controllers.registered.model;

public class SequenceJudge {
    private Judge judge;
    private Integer typeId;

    public Judge getJudge() {
        return judge;
    }

    public void setJudge(Judge judge) {
        this.judge = judge;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
