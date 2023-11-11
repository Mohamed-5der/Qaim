package com.qaim.qaim.Classes;

public class UpdatePreviewerProfileParms {
    String name , phone , email ,password , country_code ;
    int city_id , region_1_id , region_2_id , region_3_id ;
    String identity , identity_docs , about , extra_about , years , experienceString, image;

    public UpdatePreviewerProfileParms(String name, String phone, String email, String password, String country_code, int city_id, int region_1_id, int region_2_id, int region_3_id, String identity, String identity_docs, String about, String extra_about, String years, String experienceString, String image) {
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
        this.about = about;
        this.extra_about = extra_about;
        this.years = years;
        this.experienceString = experienceString;
        this.image = image;
    }
}
