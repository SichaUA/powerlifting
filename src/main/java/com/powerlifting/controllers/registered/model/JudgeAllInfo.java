package com.powerlifting.controllers.registered.model;

public class JudgeAllInfo {
    private User user;
    private Judge judge;
    private SequenceJudge sequenceJudge;
    private JudgeType judgeType;
    private JudgeCategory judgeCategory;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Judge getJudge() {
        return judge;
    }

    public void setJudge(Judge judge) {
        this.judge = judge;
    }

    public SequenceJudge getSequenceJudge() {
        return sequenceJudge;
    }

    public void setSequenceJudge(SequenceJudge sequenceJudge) {
        this.sequenceJudge = sequenceJudge;
    }

    public JudgeType getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(JudgeType judgeType) {
        this.judgeType = judgeType;
    }

    public JudgeCategory getJudgeCategory() {
        return judgeCategory;
    }

    public void setJudgeCategory(JudgeCategory judgeCategory) {
        this.judgeCategory = judgeCategory;
    }
}
