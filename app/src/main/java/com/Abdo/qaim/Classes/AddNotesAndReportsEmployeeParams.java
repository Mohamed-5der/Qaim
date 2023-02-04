package com.Abdo.qaim.Classes;

public class AddNotesAndReportsEmployeeParams {
    int order_id ;
    String emp_notes , emp_file ;

    public AddNotesAndReportsEmployeeParams(int order_id, String emp_notes, String emp_file) {
        this.order_id = order_id;
        this.emp_notes = emp_notes;
        this.emp_file = emp_file;
    }
}
