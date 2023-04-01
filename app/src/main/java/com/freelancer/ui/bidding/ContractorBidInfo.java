package com.freelancer.ui.bidding;

public class ContractorBidInfo {
    int Image;
    String ActivityName;
    String StartingPrice;
    String Description;

    public ContractorBidInfo(int image, String activityName, String startingPrice, String description) {
        Image = image;
        ActivityName = activityName;
        StartingPrice = startingPrice;
        Description = description;
    }

    public int getImage() {
        return Image;
    }

    public String getActivityName() {
        return ActivityName;
    }

    public String getStartingPrice() {
        return StartingPrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setActivityName(String activityName) {
        ActivityName = activityName;
    }

    public void setStartingPrice(String startingPrice) {
        StartingPrice = startingPrice;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
