package com.freelancer.joblisting.creation.model;

import com.freelancer.joblisting.creation.custom.model.TemplateFieldModel;

import java.io.Serializable;
import java.util.ArrayList;

public class JobListingModel implements Serializable {
    public JobInfoModel jobInfoModel;
    public ArrayList<TemplateFieldModel> customFields;

    public JobListingModel(JobInfoModel jobInfoModel, ArrayList<TemplateFieldModel> customFields) {
        this.jobInfoModel = jobInfoModel;
        this.customFields = customFields;
    }

    @Override
    public String toString() {
        return "JobListingModel{" +
                "jobInfoModel=" + jobInfoModel +
                ", customFields=" + customFields +
                '}';
    }
}
