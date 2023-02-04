package com.Abdo.qaim.Classes;

public class UserUpdateParams {
    String name ; String phone ; String email ; String password ; String country_code ; int city_id ; String lisence;

    public UserUpdateParams(String name, String phone, String email, String password, String country_code, int city_id, String lisence) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.country_code = country_code;
        this.city_id = city_id;
        this.lisence = lisence;
    }
}
