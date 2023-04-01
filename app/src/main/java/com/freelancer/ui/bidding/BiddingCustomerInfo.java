package com.freelancer.ui.bidding;

//The info for customers that are bidding.
public class BiddingCustomerInfo {
    private int image;
    private String userName;
    private String bidPrice;
    private boolean selected = false;

    public BiddingCustomerInfo(int image, String userName, String bidPrice) {
        this.image = image;
        this.userName = userName;
        this.bidPrice = bidPrice;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
