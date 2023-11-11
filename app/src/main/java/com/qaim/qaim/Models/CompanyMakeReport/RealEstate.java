package com.qaim.qaim.Models.CompanyMakeReport;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RealEstate{

	@SerializedName("cost")
	private String cost;

	@SerializedName("address")
	private String address;

	@SerializedName("color")
	private String color;

	@SerializedName("distance")
	private String distance;

	@SerializedName("city")
	private City city;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private Type type;

	@SerializedName("status_txt")
	private String statusTxt;

	@SerializedName("files")
	private List<Object> files;

	@SerializedName("id")
	private int id;

	@SerializedName("region")
	private Region region;

	@SerializedName("status")
	private String status;

	@SerializedName("longitude")
	private String longitude;

	public void setCost(String cost){
		this.cost = cost;
	}

	public String getCost(){
		return cost;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
	}

	public void setCity(City city){
		this.city = city;
	}

	public City getCity(){
		return city;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
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

	public void setType(Type type){
		this.type = type;
	}

	public Type getType(){
		return type;
	}

	public void setStatusTxt(String statusTxt){
		this.statusTxt = statusTxt;
	}

	public String getStatusTxt(){
		return statusTxt;
	}

	public void setFiles(List<Object> files){
		this.files = files;
	}

	public List<Object> getFiles(){
		return files;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setRegion(Region region){
		this.region = region;
	}

	public Region getRegion(){
		return region;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}

	@Override
 	public String toString(){
		return 
			"RealEstate{" + 
			"cost = '" + cost + '\'' + 
			",address = '" + address + '\'' + 
			",color = '" + color + '\'' + 
			",distance = '" + distance + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",description = '" + description + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",status_txt = '" + statusTxt + '\'' + 
			",files = '" + files + '\'' + 
			",id = '" + id + '\'' + 
			",region = '" + region + '\'' + 
			",status = '" + status + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}