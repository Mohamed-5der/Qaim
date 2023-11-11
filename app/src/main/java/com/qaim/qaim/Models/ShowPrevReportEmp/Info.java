package com.qaim.qaim.Models.ShowPrevReportEmp;

import com.google.gson.annotations.SerializedName;

public class Info{

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("color")
	private String color;

	@SerializedName("doc")
	private String doc;

	@SerializedName("id")
	private int id;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("status")
	private String status;

	@SerializedName("real_estate")
	private RealEstate realEstate;

	public String getCost(){
		return cost;
	}

	public String getNotes(){
		return notes;
	}

	public String getColor(){
		return color;
	}

	public String getDoc(){
		return doc;
	}

	public int getId(){
		return id;
	}

	public String getStatusText(){
		return statusText;
	}

	public String getStatus(){
		return status;
	}

	public RealEstate getRealEstate(){
		return realEstate;
	}
}