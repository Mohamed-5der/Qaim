package com.qaim.qaim.Models.UpdatePreviewerProfile;

import com.google.gson.annotations.SerializedName;

public class Previewer{

	@SerializedName("image")
	private String image;

	@SerializedName("cost")
	private Object cost;

	@SerializedName("field_txt")
	private Object fieldTxt;

	@SerializedName("city")
	private City city;

	@SerializedName("about")
	private Object about;

	@SerializedName("experience")
	private Object experience;

	@SerializedName("years")
	private Object years;

	@SerializedName("token")
	private String token;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("region_3")
	private Region3 region3;

	@SerializedName("extra_about")
	private Object extraAbout;

	@SerializedName("phone")
	private String phone;

	@SerializedName("identity")
	private String identity;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("identity_docs")
	private String identityDocs;

	@SerializedName("region_1")
	private Region1 region1;

	@SerializedName("region_2")
	private Region2 region2;

	@SerializedName("email")
	private String email;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCost(Object cost){
		this.cost = cost;
	}

	public Object getCost(){
		return cost;
	}

	public void setFieldTxt(Object fieldTxt){
		this.fieldTxt = fieldTxt;
	}

	public Object getFieldTxt(){
		return fieldTxt;
	}

	public void setCity(City city){
		this.city = city;
	}

	public City getCity(){
		return city;
	}

	public void setAbout(Object about){
		this.about = about;
	}

	public Object getAbout(){
		return about;
	}

	public void setExperience(Object experience){
		this.experience = experience;
	}

	public Object getExperience(){
		return experience;
	}

	public void setYears(Object years){
		this.years = years;
	}

	public Object getYears(){
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

	public void setRegion3(Region3 region3){
		this.region3 = region3;
	}

	public Region3 getRegion3(){
		return region3;
	}

	public void setExtraAbout(Object extraAbout){
		this.extraAbout = extraAbout;
	}

	public Object getExtraAbout(){
		return extraAbout;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setIdentity(String identity){
		this.identity = identity;
	}

	public String getIdentity(){
		return identity;
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

	public void setIdentityDocs(String identityDocs){
		this.identityDocs = identityDocs;
	}

	public String getIdentityDocs(){
		return identityDocs;
	}

	public void setRegion1(Region1 region1){
		this.region1 = region1;
	}

	public Region1 getRegion1(){
		return region1;
	}

	public void setRegion2(Region2 region2){
		this.region2 = region2;
	}

	public Region2 getRegion2(){
		return region2;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Previewer{" + 
			"image = '" + image + '\'' + 
			",cost = '" + cost + '\'' + 
			",field_txt = '" + fieldTxt + '\'' + 
			",city = '" + city + '\'' + 
			",about = '" + about + '\'' + 
			",experience = '" + experience + '\'' + 
			",years = '" + years + '\'' + 
			",token = '" + token + '\'' + 
			",country_code = '" + countryCode + '\'' + 
			",region_3 = '" + region3 + '\'' + 
			",extra_about = '" + extraAbout + '\'' + 
			",phone = '" + phone + '\'' + 
			",identity = '" + identity + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",identity_docs = '" + identityDocs + '\'' + 
			",region_1 = '" + region1 + '\'' + 
			",region_2 = '" + region2 + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}