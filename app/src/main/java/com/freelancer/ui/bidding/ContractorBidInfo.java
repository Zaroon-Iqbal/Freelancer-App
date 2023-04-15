package com.freelancer.ui.bidding;

//Contractor Info when creating a bidding system.
public class ContractorBidInfo {
    private String url;
    private String activityName;
    private String startingPrice;
    private String description;

    public ContractorBidInfo(){
        //
    }
    public ContractorBidInfo(String url, String activityName, String startingPrice, String description) {
        this.url = url;
        this.activityName = activityName;
        this.startingPrice = startingPrice;
        this.description = description;
    }

    public String getURL() {
        return url;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getStartingPrice() {
        return startingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
