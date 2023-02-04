package com.Abdo.qaim.Models.MyListTeamReports;

import com.google.gson.annotations.SerializedName;

public class Painter{

	@SerializedName("image")
	private String image;

	@SerializedName("notes")
	private String notes;

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

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
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

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Painter{" + 
			"image = '" + image + '\'' + 
			",notes = '" + notes + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",type = '" + type + '\'' + 
			",email = '" + email + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}