package com.qaim.qaim.Models.AprovedList;

import java.util.List;
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

	@SerializedName("attributes")
	private List<AttributesItem> attributes;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("status_txt")
	private String statusTxt;

	@SerializedName("has_report")
	private int hasReport;

	@SerializedName("has_completed")
	private int hasCompleted;

	@SerializedName("status")
	private String status;

	@SerializedName("order_description")
	private String orderDescription;

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

	public void setAttributes(List<AttributesItem> attributes){
		this.attributes = attributes;
	}

	public List<AttributesItem> getAttributes(){
		return attributes;
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

	public void setHasReport(int hasReport){
		this.hasReport = hasReport;
	}

	public int getHasReport(){
		return hasReport;
	}

	public void setHasCompleted(int hasCompleted){
		this.hasCompleted = hasCompleted;
	}

	public int getHasCompleted(){
		return hasCompleted;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setOrderDescription(String orderDescription){
		this.orderDescription = orderDescription;
	}

	public String getOrderDescription(){
		return orderDescription;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"image = '" + image + '\'' + 
			",cost = '" + cost + '\'' + 
			",color = '" + color + '\'' + 
			",description = '" + description + '\'' + 
			",attributes = '" + attributes + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",status_txt = '" + statusTxt + '\'' + 
			",has_report = '" + hasReport + '\'' + 
			",has_completed = '" + hasCompleted + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}