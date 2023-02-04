package com.Abdo.qaim.Classes;

public class SendOfferParams {
    int real_estate_id ; String cost ; String doc ; String notes ;

    public SendOfferParams(int real_estate_id, String cost, String doc, String notes) {
        this.real_estate_id = real_estate_id;
        this.cost = cost;
        this.doc = doc;
        this.notes = notes;
    }
}
