package com.freelancer.joblisting.creation.custom.model;

import java.util.ArrayList;

public class SelectionFieldModel extends TemplateFieldModel {
    private final ArrayList<String> options;
    private SelectionType selectionType;

    public SelectionFieldModel() {
        super(FieldType.SELECTION);
        options = new ArrayList<>();
        selectionType = SelectionType.SINGLE;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void removeOption(String option) {
        options.remove(option);
    }

    @Override
    public String toString() {
        return super.toString() + ", SelectionFieldModel{" +
                "options=" + options +
                ", selectionType=" + selectionType +
                '}';
    }
}