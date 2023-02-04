package com.Abdo.qaim.Classes;

public class CompanyUserRegisterParms {
    public String name , phone , email , password , country_code , user_type  ,  lisence , presenter_doc ;
    public int  city_id;

    public CompanyUserRegisterParms(String name, String phone, String email, String password, String country_code, String user_type, String lisence, String presenter_doc, int city_id) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.country_code = country_code;
        this.user_type = user_type;
        this.lisence = lisence;
        this.presenter_doc = presenter_doc;
        this.city_id = city_id;
    }
}
