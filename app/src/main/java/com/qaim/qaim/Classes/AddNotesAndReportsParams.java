package com.qaim.qaim.Classes;

public class AddNotesAndReportsParams {
    int order_id ;
    String notes , file ;

    public AddNotesAndReportsParams(int order_id, String notes, String file) {
        this.order_id = order_id;
        this.notes = notes;
        this.file = file;
    }
}
