package com.qaim.qaim.Models.AssignTeam;

import com.google.gson.annotations.SerializedName;

public class Region{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}