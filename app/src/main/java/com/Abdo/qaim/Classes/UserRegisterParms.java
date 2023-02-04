package com.Abdo.qaim.Classes;

public class UserRegisterParms {
    public String name , phone , email , password , country_code , user_type  ,  lisence ;
    public int  city_id;

    public UserRegisterParms(String name, String phone, String email, String password, String cuntryCode, String userType, String lisence, int cityId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.country_code = cuntryCode;
        this.user_type = userType;
        this.lisence = lisence;
        this.city_id = cityId;
    }
}
