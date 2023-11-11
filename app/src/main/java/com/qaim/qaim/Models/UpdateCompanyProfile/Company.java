package com.qaim.qaim.Models.UpdateCompanyProfile;

import com.google.gson.annotations.SerializedName;

public class Company{

	@SerializedName("image")
	private String image;

	@SerializedName("phone")
	private String phone;

	@SerializedName("license_doc")
	private String licenseDoc;

	@SerializedName("city")
	private City city;

	@SerializedName("name")
	private String name;

	@SerializedName("about")
	private String about;

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

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setLicenseDoc(String licenseDoc){
		this.licenseDoc = licenseDoc;
	}

	public String getLicenseDoc(){
		return licenseDoc;
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

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
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
			"Company{" + 
			"image = '" + image + '\'' + 
			",phone = '" + phone + '\'' + 
			",license_doc = '" + licenseDoc + '\'' + 
			",city = '" + city + '\'' + 
			",name = '" + name + '\'' + 
			",about = '" + about + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",city_id = '" + cityId + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}