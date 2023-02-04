package com.Abdo.qaim.Models.RealsteatListUserResponse;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image")
	private String image;

	@SerializedName("cost")
	private String cost;

	@SerializedName("color")
	private String color;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("status_txt")
	private String statusTxt;

	@SerializedName("status")
	private String status;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setCost(String cost){
		this.cost = cost;
	}

	public String getCost(){
		return cost;
	}

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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setStatusTxt(String statusTxt){
		this.statusTxt = statusTxt;
	}

	public String getStatusTxt(){
		return statusTxt;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",cost = '" + cost + '\'' + 
			",color = '" + color + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",status_txt = '" + statusTxt + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}