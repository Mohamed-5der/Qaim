package com.qaim.qaim.Models.ShowPrevReportEmp;

import com.google.gson.annotations.SerializedName;

public class Previewer{

	@SerializedName("image")
	private String image;

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("field_txt")
	private Object fieldTxt;

	@SerializedName("experience")
	private String experience;

	@SerializedName("years")
	private String years;

	@SerializedName("token")
	private String token;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("extra_about")
	private String extraAbout;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	public String getImage(){
		return image;
	}

	public String getCost(){
		return cost;
	}

	public String getNotes(){
		return notes;
	}

	public Object getFieldTxt(){
		return fieldTxt;
	}

	public String getExperience(){
		return experience;
	}

	public String getYears(){
		return years;
	}

	public String getToken(){
		return token;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public String getExtraAbout(){
		return extraAbout;
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

	public String getEmail(){
		return email;
	}
}