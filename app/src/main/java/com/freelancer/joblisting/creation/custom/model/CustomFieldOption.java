package com.freelancer.joblisting.creation.custom.model;

public class CustomFieldOption {

    private final String optionName;
    private final Double additionalPrice;

    public CustomFieldOption(String optionName, Double additionalPrice) {
        this.optionName = optionName;
        this.additionalPrice = additionalPrice;
    }

    public String getOptionName() {
        return optionName;
    }

    public Double getAdditionalPrice() {
        return additionalPrice;
    }
}
