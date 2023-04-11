package com.freelancer.joblisting.creation.custom.model;

public class FreeformFieldModel extends TemplateFieldModel {

    public FreeformFieldModel() {
        super(FieldType.FREE_FORM);
    }

    @Override
    public String toString() {
        return super.toString() + ", FreeformFieldModel{" + '}';
    }
}