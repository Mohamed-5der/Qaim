package com.Abdo.qaim.Models.AssignTeam;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RealEstate{

	@SerializedName("address")
	private String address;

	@SerializedName("distance")
	private Object distance;

	@SerializedName("city")
	private City city;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("files")
	private List<Object> files;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("region")
	private Region region;

	@SerializedName("type")
	private Type type;

	@SerializedName("longitude")
	private String longitude;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setDistance(Object distance){
		this.distance = distance;
	}

	public Object getDistance(){
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

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setRegion(Region region){
		this.region = region;
	}

	public Region getRegion(){
		return region;
	}

	public void setType(Type type){
		this.type = type;
	}

	public Type getType(){
		return type;
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
			"address = '" + address + '\'' + 
			",distance = '" + distance + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",description = '" + description + '\'' + 
			",files = '" + files + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",region = '" + region + '\'' + 
			",type = '" + type + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}