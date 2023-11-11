package com.qaim.qaim.Models.CompanyRegister;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("company")
	private Company company;

	public void setCompany(Company company){
		this.company = company;
	}

	public Company getCompany(){
		return company;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"company = '" + company + '\'' + 
			"}";
		}
}