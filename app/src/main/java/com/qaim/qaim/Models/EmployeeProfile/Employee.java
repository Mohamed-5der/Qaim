package com.qaim.qaim.Models.EmployeeProfile;

import com.google.gson.annotations.SerializedName;

public class Employee{

	@SerializedName("country_code")
	private Object countryCode;

	@SerializedName("image")
	private String image;

	@SerializedName("phone")
	private Object phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("token")
	private String token;

	public void setCountryCode(Object countryCode){
		this.countryCode = countryCode;
	}

	public Object getCountryCode(){
		return countryCode;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPhone(Object phone){
		this.phone = phone;
	}

	public Object getPhone(){
		return phone;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Employee{" + 
			"country_code = '" + countryCode + '\'' + 
			",image = '" + image + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}