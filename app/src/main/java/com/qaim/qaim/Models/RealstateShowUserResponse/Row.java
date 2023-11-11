package com.qaim.qaim.Models.RealstateShowUserResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Row{

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

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("reviewer")
	private String reviewer;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private Type type;

	@SerializedName("status_txt")
	private String statusTxt;

	@SerializedName("painter")
	private Painter painter;

	@SerializedName("files")
	private List<FilesItem> files;

	@SerializedName("attributes")
	private List<AttributesItem> attributes;

	@SerializedName("id")
	private int id;

	@SerializedName("region")
	private Region region;

	@SerializedName("status")
	private String status;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("show_previewer")
	private int show_previewer;

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

	public void setPreviewer(Previewer previewer){
		this.previewer = previewer;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	public void setReviewer(String reviewer){
		this.reviewer = reviewer;
	}

	public String getReviewer(){
		return reviewer;
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

	public void setPainter(Painter painter){
		this.painter = painter;
	}

	public Painter getPainter(){
		return painter;
	}

	public void setFiles(List<FilesItem> files){
		this.files = files;
	}

	public List<FilesItem> getFiles(){
		return files;
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

	public int getShow_previewer() {
		return show_previewer;
	}

	public void setShow_previewer(int show_previewer) {
		this.show_previewer = show_previewer;
	}

	@Override
 	public String toString(){
		return 
			"Row{" + 
			"cost = '" + cost + '\'' + 
			",address = '" + address + '\'' + 
			",color = '" + color + '\'' + 
			",distance = '" + distance + '\'' + 
			",city = '" + city + '\'' + 
			",latitude = '" + latitude + '\'' + 
			",description = '" + description + '\'' + 
			",previewer = '" + previewer + '\'' + 
			",reviewer = '" + reviewer + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",status_txt = '" + statusTxt + '\'' + 
			",painter = '" + painter + '\'' + 
			",files = '" + files + '\'' + 
			",attributes = '" + attributes + '\'' + 
			",id = '" + id + '\'' + 
			",region = '" + region + '\'' + 
			",status = '" + status + '\'' + 
			",longitude = '" + longitude + '\'' + 
			"}";
		}
}