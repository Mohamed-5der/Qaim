package com.qaim.qaim.Models.OrderListUserResponse;

import com.google.gson.annotations.SerializedName;

public class Company{

	@SerializedName("image")
	private String image;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("about")
	private String about;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
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

	public void setAbout(String about){
		this.about = about;
	}

	public String getAbout(){
		return about;
	}

	@Override
 	public String toString(){
		return 
			"Company{" + 
			"image = '" + image + '\'' + 
			",phone = '" + phone + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}