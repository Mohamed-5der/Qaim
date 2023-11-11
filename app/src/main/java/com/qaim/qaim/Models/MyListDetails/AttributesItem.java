package com.qaim.qaim.Models.MyListDetails;

import com.google.gson.annotations.SerializedName;

public class AttributesItem {

	@SerializedName("color")
	private String color;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"AttributesItem{" + 
			"color = '" + color + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}