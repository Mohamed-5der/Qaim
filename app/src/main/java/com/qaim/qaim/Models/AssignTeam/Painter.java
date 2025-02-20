package com.qaim.qaim.Models.AssignTeam;

import com.google.gson.annotations.SerializedName;

public class Painter{

	@SerializedName("image")
	private String image;

	@SerializedName("notes")
	private Object notes;

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

	public String getImage(){
		return image;
	}

	public Object getNotes(){
		return notes;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public String getEmail(){
		return email;
	}

	public String getToken(){
		return token;
	}
}