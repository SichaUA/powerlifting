package com.powerlifting.controllers.registered.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AddSequenceRequest {
    private List<AgeGroupWithWeightCategory> ageGroupsAndWeightCategories;
    private List<AgeGroupWithWeightCategory> selectedElements;
    private List<Integer> selectedAgeGroupsAndWeightCategories;
    private Date date;
    private String info;

    public List<AgeGroupWithWeightCategory> getAgeGroupsAndWeightCategories() {
        return ageGroupsAndWeightCategories;
    }

    public void setAgeGroupsAndWeightCategories(List<AgeGroupWithWeightCategory> ageGroupsAndWeightCategories) {
        this.ageGroupsAndWeightCategories = ageGroupsAndWeightCategories;
    }

    public List<Integer> getSelectedAgeGroupsAndWeightCategories() {
        return selectedAgeGroupsAndWeightCategories;
    }

    public void setSelectedAgeGroupsAndWeightCategories(List<Integer> selectedAgeGroupsAndWeightCategories) {
        this.selectedAgeGroupsAndWeightCategories = selectedAgeGroupsAndWeightCategories;
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

    public List<AgeGroupWithWeightCategory> getSelectedElements() {
        List<AgeGroupWithWeightCategory> ageGroupWithWeightCategories = new ArrayList<>();

        for(Iterator<Integer> i = selectedAgeGroupsAndWeightCategories.iterator(); i.hasNext(); ) {
            Integer selectedId = i.next();
            for(Iterator<AgeGroupWithWeightCategory> j = ageGroupsAndWeightCategories.iterator(); j.hasNext(); ) {
                AgeGroupWithWeightCategory ageGroupWithWeightCategory = j.next();
                if(ageGroupWithWeightCategory.getId() == selectedId) {
                    ageGroupWithWeightCategories.add(ageGroupWithWeightCategory);
                }
            }
        }

        return ageGroupWithWeightCategories;
    }
}
