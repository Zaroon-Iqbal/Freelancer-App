package com.freelancer.joblisting.creation.custom.model;

import java.util.ArrayList;

public class SelectionFieldModel extends TemplateFieldModel {
    private final ArrayList<String> options;

    public SelectionFieldModel(SelectionType selectionType) {
        super((selectionType.equals(SelectionType.MULTIPLE)
                ? FieldType.MULTI_SELECT : FieldType.SINGLE_SELECT));
        options = new ArrayList<>();
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void removeOption(String option) {
        options.remove(option);
    }

    @Override
    public String toString() {
        return super.toString() + ", SelectionCustomFieldModel{" +
                "options=" + options +
                '}';
    }
}