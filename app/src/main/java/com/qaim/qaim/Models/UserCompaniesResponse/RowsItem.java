package com.qaim.qaim.Models.UserCompaniesResponse;

import com.google.gson.annotations.SerializedName;

public class RowsItem{

	@SerializedName("image")
	private String image;

	@SerializedName("verify_vip")
	private int verifyVip;

	@SerializedName("city")
	private City city;

	@SerializedName("about")
	private String about;

	@SerializedName("is_verified")
	private int isVerified;

	@SerializedName("token")
	private String token;

	@SerializedName("phone")
	private String phone;

	@SerializedName("license_doc")
	private String licenseDoc;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("request_vip")
	private int requestVip;

	@SerializedName("city_id")
	private int cityId;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setVerifyVip(int verifyVip){
		this.verifyVip = verifyVip;
	}

	public int getVerifyVip(){
		return verifyVip;
	}

	public void setCity(City city){
		this.city = city;
	}

	public City getCity(){
		return city;
	}

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	public void setIsVerified(int isVerified){
		this.isVerified = isVerified;
	}

	public int getIsVerified(){
		return isVerified;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
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

	public void setRequestVip(int requestVip){
		this.requestVip = requestVip;
	}

	public int getRequestVip(){
		return requestVip;
	}

	public void setCityId(int cityId){
		this.cityId = cityId;
	}

	public int getCityId(){
		return cityId;
	}
}