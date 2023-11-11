package com.qaim.qaim.Classes;

public class CompanyUpdateProfileParams {
    String name ; String phone ; String email ; String password ; String country_code ; int city_id ; String about ;

    public CompanyUpdateProfileParams(String name, String phone, String email, String password, String country_code, int city_id, String about) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.country_code = country_code;
        this.city_id = city_id;
        this.about = about;
    }
}
