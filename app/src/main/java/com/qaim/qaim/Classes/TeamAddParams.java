package com.qaim.qaim.Classes;

public class TeamAddParams {
    String name ; String email ; String password ; String type , country_code , phone ;


    public TeamAddParams(String name, String email, String password, String type, String country_code, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.country_code = country_code;
        this.phone = phone;
    }
}
