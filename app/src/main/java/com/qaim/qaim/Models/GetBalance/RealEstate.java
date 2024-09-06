package com.qaim.qaim.Models.GetBalance;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RealEstate{

	@SerializedName("show_previewer")
	private int showPreviewer;

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
	private Object reviewer;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private Type type;

	@SerializedName("status_txt")
	private String statusTxt;

	@SerializedName("painter")
	private Object painter;

	@SerializedName("previewer_notes_list")
	private List<Object> previewerNotesList;

	@SerializedName("files")
	private List<FilesItem> files;

	@SerializedName("company")
	private Company company;

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

	@SerializedName("notes_list")
	private List<Object> notesList;

	public int getShowPreviewer(){
		return showPreviewer;
	}

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

	public String getLatitude(){
		return latitude;
	}

	public String getDescription(){
		return description;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	public Object getReviewer(){
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

	public Object getPainter(){
		return painter;
	}

	public List<Object> getPreviewerNotesList(){
		return previewerNotesList;
	}

	public List<FilesItem> getFiles(){
		return files;
	}

	public Company getCompany(){
		return company;
	}

	public List<AttributesItem> getAttributes(){
		return attributes;
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

	public String getLongitude(){
		return longitude;
	}

	public List<Object> getNotesList(){
		return notesList;
	}
}