package com.freelancer.joblisting.creation.custom.model;

public class BooleanFieldModel extends TemplateFieldModel {

    public BooleanFieldModel() {
        super(FieldType.BOOLEAN);
    }
    
    @Override
    public String toString() {
        return super.toString() + ", BooleanCustomFieldModel{" + '}';
    }
}