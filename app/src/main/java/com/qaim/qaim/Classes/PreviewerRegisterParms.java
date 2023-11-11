package com.qaim.qaim.Classes;

public class PreviewerRegisterParms {
    String name , phone , email ,password , country_code ;
    int city_id , region_1_id , region_2_id , region_3_id ;
    String identity , identity_docs , cost ;

    public PreviewerRegisterParms(String name, String phone, String email, String password, String country_code, int city_id, int region_1_id, int region_2_id, int region_3_id, String identity, String identity_docs, String cost) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.country_code = country_code;
        this.city_id = city_id;
        this.region_1_id = region_1_id;
        this.region_2_id = region_2_id;
        this.region_3_id = region_3_id;
        this.identity = identity;
        this.identity_docs = identity_docs;
        this.cost = cost;
    }
}
