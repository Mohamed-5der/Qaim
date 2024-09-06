package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class Reviewer{

	@SerializedName("country_code")
	private Object countryCode;

	@SerializedName("image")
	private String image;

	@SerializedName("notes")
	private String notes;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	@SerializedName("email")
	private String email;

	@SerializedName("token")
	private String token;

	public Object getCountryCode(){
		return countryCode;
	}

	public String getImage(){
		return image;
	}

	public String getNotes(){
		return notes;
	}

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public String getEmail(){
		return email;
	}

	public String getToken(){
		return token;
	}
}