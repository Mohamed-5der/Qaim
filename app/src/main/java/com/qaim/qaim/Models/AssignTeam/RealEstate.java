package com.qaim.qaim.Models.AssignTeam;

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
	private Object latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("previewer")
	private Object previewer;

	@SerializedName("reviewer")
	private Reviewer reviewer;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private Type type;

	@SerializedName("status_txt")
	private String statusTxt;

	@SerializedName("painter")
	private Painter painter;

	@SerializedName("files")
	private List<Object> files;

	@SerializedName("id")
	private int id;

	@SerializedName("region")
	private Region region;

	@SerializedName("status")
	private String status;

	@SerializedName("longitude")
	private Object longitude;

	public String getCost(){
		return cost;
	}

	public String getAddress(){
		return address;
	}

	public String getColor(){
		return color;
	}

	public String getDistance(){
		return distance;
	}

	public City getCity(){
		return city;
	}

	public Object getLatitude(){
		return latitude;
	}

	public String getDescription(){
		return description;
	}

	public Object getPreviewer(){
		return previewer;
	}

	public Reviewer getReviewer(){
		return reviewer;
	}

	public String getTitle(){
		return title;
	}

	public Type getType(){
		return type;
	}

	public String getStatusTxt(){
		return statusTxt;
	}

	public Painter getPainter(){
		return painter;
	}

	public List<Object> getFiles(){
		return files;
	}

	public int getId(){
		return id;
	}

	public Region getRegion(){
		return region;
	}

	public String getStatus(){
		return status;
	}

	public Object getLongitude(){
		return longitude;
	}
}