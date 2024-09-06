package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class AttributesItem{

	@SerializedName("color")
	private String color;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	public String getColor(){
		return color;
	}

	public String getDescription(){
		return description;
	}

	public String getTitle(){
		return title;
	}
}