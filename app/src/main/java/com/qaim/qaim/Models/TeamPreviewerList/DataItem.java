package com.qaim.qaim.Models.TeamPreviewerList;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("field_txt")
	private String fieldTxt;

	@SerializedName("experience")
	private String experience;

	@SerializedName("years")
	private String years;

	@SerializedName("areas")
	private String area;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

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

	@SerializedName("rate")
	private String rate;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCost(String cost){
		this.cost = cost;
	}

	public String getCost(){
		return cost;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setFieldTxt(String fieldTxt){
		this.fieldTxt = fieldTxt;
	}

	public String getFieldTxt(){
		return fieldTxt;
	}

	public void setExperience(String experience){
		this.experience = experience;
	}

	public String getExperience(){
		return experience;
	}

	public void setYears(String years){
		this.years = years;
	}

	public String getYears(){
		return years;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setExtraAbout(String extraAbout){
		this.extraAbout = extraAbout;
	}

	public String getExtraAbout(){
		return extraAbout;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",cost = '" + cost + '\'' + 
			",notes = '" + notes + '\'' + 
			",field_txt = '" + fieldTxt + '\'' + 
			",experience = '" + experience + '\'' + 
			",years = '" + years + '\'' + 
			",token = '" + token + '\'' + 
			",country_code = '" + countryCode + '\'' + 
			",extra_about = '" + extraAbout + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}