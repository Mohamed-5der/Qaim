package com.qaim.qaim.Models.CompanyUserRegisterResponse;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("image")
	private String image;

	@SerializedName("license")
	private Object license;

	@SerializedName("phone")
	private String phone;

	@SerializedName("city")
	private City city;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("city_id")
	private String cityId;

	@SerializedName("token")
	private String token;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setLicense(Object license){
		this.license = license;
	}

	public Object getLicense(){
		return license;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setCity(City city){
		this.city = city;
	}

	public City getCity(){
		return city;
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

	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return cityId;
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
			"User{" + 
			"image = '" + image + '\'' + 
			",license = '" + license + '\'' + 
			",phone = '" + phone + '\'' + 
			",city = '" + city + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",city_id = '" + cityId + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}