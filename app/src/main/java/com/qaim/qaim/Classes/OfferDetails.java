package com.qaim.qaim.Classes;

public class OfferDetails {
    String offerCompanyName ,offerPrice , offerDescription ;

    public OfferDetails(String offerCompanyName, String offerPrice, String offerDescription) {
        this.offerCompanyName = offerCompanyName;
        this.offerPrice = offerPrice;
        this.offerDescription = offerDescription;
    }

    public String getOfferCompanyName() {
        return offerCompanyName;
    }

    public void setOfferCompanyName(String offerCompanyName) {
        this.offerCompanyName = offerCompanyName;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }
}
