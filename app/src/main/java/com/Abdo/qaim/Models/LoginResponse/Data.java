package com.Abdo.qaim.Models.LoginResponse;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("account_type")
	private String accountType;

	@SerializedName("user")
	private User user;

	public void setAccountType(String accountType){
		this.accountType = accountType;
	}

	public String getAccountType(){
		return accountType;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"account_type = '" + accountType + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}