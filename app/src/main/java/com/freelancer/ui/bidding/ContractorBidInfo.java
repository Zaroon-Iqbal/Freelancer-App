package com.freelancer.ui.bidding;

//Contractor Info when creating a bidding system.
public class ContractorBidInfo {
    private String bID;
    private String url;
    private String activityName;
    private String startingPrice;
    private String description;

    public ContractorBidInfo(){
        //
    }
    public ContractorBidInfo(String bID, String url, String activityName, String startingPrice, String description) {
        this.bID = bID;
        this.url = url;
        this.activityName = activityName;
        this.startingPrice = startingPrice;
        this.description = description;
    }

    public String getBID() {return bID;}

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

    public void setBID(String bID) {this.bID = bID;}
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
