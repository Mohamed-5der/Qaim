package com.qaim.qaim.Classes;

public class TeamUpdateParams {
    int team_id ; String name ; String email ; String password ; String type , country_code , phone , notes ;

    public TeamUpdateParams(int team_id, String name, String email, String password, String type, String country_code, String phone, String notes) {
        this.team_id = team_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.country_code = country_code;
        this.phone = phone;
        this.notes = notes;
    }
}
