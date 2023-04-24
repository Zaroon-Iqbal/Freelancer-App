package com.freelancer.ui.bidding;

//The info for customers that are bidding.
public class BiddingCustomerInfo {
    private String contractorID;
    private String auctionID;
    private String bidID;
    private String image;
    private String userName;
    private String bidPrice;
    private boolean selected = false;

    public BiddingCustomerInfo(){
        //
    }

    public BiddingCustomerInfo(String contractorID, String auctionID, String bidID, String image, String userName, String bidPrice) {
        this.contractorID = contractorID;
        this.auctionID = auctionID;
        this.bidID = bidID;
        this.image = image;
        this.userName = userName;
        this.bidPrice = bidPrice;
    }

    public String getContractorID() {
        return contractorID;
    }

    public void setContractorID(String contractorID) {
        this.contractorID = contractorID;
    }

    public String getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(String auctionID) {
        this.auctionID = auctionID;
    }

    public String getBidID() {
        return bidID;
    }

    public void setBidID(String bidID){
        this.bidID = bidID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
