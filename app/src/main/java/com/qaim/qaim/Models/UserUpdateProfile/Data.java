package com.qaim.qaim.Models.UserUpdateProfile;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("user")
	private User user;

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
			"user = '" + user + '\'' + 
			"}";
		}
}