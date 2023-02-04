package com.Abdo.qaim.Classes;

public class UpdateEmployeeProfileParams {
    String name , phone , email ,password , country_code ;
    String sendImage ;


    public UpdateEmployeeProfileParams(String name, String phone, String email, String password, String country_code , String sendImage) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.country_code = country_code;
        this.sendImage = sendImage;
    }
}
