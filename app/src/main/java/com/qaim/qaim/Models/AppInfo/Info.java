package com.qaim.qaim.Models.AppInfo;

import com.google.gson.annotations.SerializedName;

public class Info{

	@SerializedName("phone")
	private String phone;

	@SerializedName("terms")
	private String terms;

	@SerializedName("commission_company")
	private String commissionCompany;

	@SerializedName("is_free")
	private String isFree;

	@SerializedName("about")
	private String about;

	@SerializedName("commission_prev")
	private String commissionPrev;

	@SerializedName("email")
	private String email;

	@SerializedName("commission_user")
	private String commissionUser;

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setTerms(String terms){
		this.terms = terms;
	}

	public String getTerms(){
		return terms;
	}

	public void setCommissionCompany(String commissionCompany){
		this.commissionCompany = commissionCompany;
	}

	public String getCommissionCompany(){
		return commissionCompany;
	}

	public void setIsFree(String isFree){
		this.isFree = isFree;
	}

	public String getIsFree(){
		return isFree;
	}

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	public void setCommissionPrev(String commissionPrev){
		this.commissionPrev = commissionPrev;
	}

	public String getCommissionPrev(){
		return commissionPrev;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setCommissionUser(String commissionUser){
		this.commissionUser = commissionUser;
	}

	public String getCommissionUser(){
		return commissionUser;
	}

	@Override
 	public String toString(){
		return 
			"Info{" + 
			"phone = '" + phone + '\'' + 
			",terms = '" + terms + '\'' + 
			",commission_company = '" + commissionCompany + '\'' + 
			",is_free = '" + isFree + '\'' + 
			",about = '" + about + '\'' + 
			",commission_prev = '" + commissionPrev + '\'' + 
			",email = '" + email + '\'' + 
			",commission_user = '" + commissionUser + '\'' + 
			"}";
		}
}