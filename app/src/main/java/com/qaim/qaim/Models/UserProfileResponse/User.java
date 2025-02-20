package com.qaim.qaim.Models.UserProfileResponse;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("image")
	private String image;

	@SerializedName("license")
	private Object license;

	@SerializedName("phone")
	private String phone;

	@SerializedName("country")
	private Country country;

	@SerializedName("city")
	private City city;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("country_id")
	private int countryId;

	@SerializedName("city_id")
	private int cityId;

	@SerializedName("token")
	private String token;

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

	public void setCountry(Country country){
		this.country = country;
	}

	public Country getCountry(){
		return country;
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

	public void setCountryId(int countryId){
		this.countryId = countryId;
	}

	public int getCountryId(){
		return countryId;
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

	@Override
 	public String toString(){
		return 
			"User{" + 
			"country_code = '" + countryCode + '\'' + 
			",image = '" + image + '\'' + 
			",license = '" + license + '\'' + 
			",phone = '" + phone + '\'' + 
			",country = '" + country + '\'' +
			",city = '" + city + '\'' +
			",name = '" + name + '\'' +
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",country_id = '" + countryId + '\'' +
			",city_id = '" + cityId + '\'' +
			",token = '" + token + '\'' +
			"}";
		}
}