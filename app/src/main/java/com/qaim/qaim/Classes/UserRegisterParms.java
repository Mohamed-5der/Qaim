package com.qaim.qaim.Classes;

public class UserRegisterParms {
    public String name , phone , email , password , country_code , user_type  ;
    public int  country_id, city_id ; String player_id ;

    public UserRegisterParms(String name, String phone, String email, String password, String country_code, String user_type, int country_id, int city_id, String player_id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.country_code = country_code;
        this.user_type = user_type;
        this.country_id = country_id;
        this.city_id = city_id;
        this.player_id = player_id;
    }
}
