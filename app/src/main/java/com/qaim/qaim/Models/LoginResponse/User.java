package com.qaim.qaim.Models.LoginResponse;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("image")
	private String image;

	@SerializedName("is_verified")
	private int isVerified;

	@SerializedName("license")
	private String license;

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
	private int cityId;

	@SerializedName("token")
	private String token;

	@SerializedName("first_login")
	private int firstLogin;

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public void setLicense(String license){
		this.license = license;
	}

	public String getLicense(){
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

	public void setCityId(int cityId){
		this.cityId = cityId;
	}

	public int getCityId(){
		return cityId;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public int getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(int firstLogin) {
		this.firstLogin = firstLogin;
	}


	@Override
 	public String toString(){
		return 
			"User{" + 
			"country_code = '" + countryCode + '\'' + 
			",image = '" + image + '\'' + 
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