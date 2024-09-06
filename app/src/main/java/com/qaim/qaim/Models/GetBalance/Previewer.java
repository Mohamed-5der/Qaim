package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class Previewer{

	@SerializedName("image")
	private String image;

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("field_txt")
	private String fieldTxt;

	@SerializedName("areas")
	private String areas;

	@SerializedName("experience")
	private Object experience;

	@SerializedName("is_verified")
	private int isVerified;

	@SerializedName("years")
	private Object years;

	@SerializedName("first_login")
	private int firstLogin;

	@SerializedName("token")
	private String token;

	@SerializedName("previewer_document")
	private String previewerDocument;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("extra_about")
	private Object extraAbout;

	@SerializedName("previewer_doc")
	private String previewerDoc;

	@SerializedName("phone")
	private String phone;

	@SerializedName("rate")
	private String rate;

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

	public String getFieldTxt(){
		return fieldTxt;
	}

	public String getAreas(){
		return areas;
	}

	public Object getExperience(){
		return experience;
	}

	public int getIsVerified(){
		return isVerified;
	}

	public Object getYears(){
		return years;
	}

	public int getFirstLogin(){
		return firstLogin;
	}

	public String getToken(){
		return token;
	}

	public String getPreviewerDocument(){
		return previewerDocument;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public Object getExtraAbout(){
		return extraAbout;
	}

	public String getPreviewerDoc(){
		return previewerDoc;
	}

	public String getPhone(){
		return phone;
	}

	public String getRate(){
		return rate;
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