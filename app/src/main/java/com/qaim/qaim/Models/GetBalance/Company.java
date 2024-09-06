package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class Company{

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

	public String getImage(){
		return image;
	}

	public int getVerifyVip(){
		return verifyVip;
	}

	public City getCity(){
		return city;
	}

	public String getAbout(){
		return about;
	}

	public int getIsVerified(){
		return isVerified;
	}

	public String getToken(){
		return token;
	}

	public String getPhone(){
		return phone;
	}

	public String getLicenseDoc(){
		return licenseDoc;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public int getRequestVip(){
		return requestVip;
	}

	public int getCityId(){
		return cityId;
	}
}