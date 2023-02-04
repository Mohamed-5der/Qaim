package com.Abdo.qaim.Models.MyListPrevDetails;

import com.google.gson.annotations.SerializedName;

public class Info{

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

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

	public void setCost(String cost){
		this.cost = cost;
	}

	public String getCost(){
		return cost;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setDoc(String doc){
		this.doc = doc;
	}

	public String getDoc(){
		return doc;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatusText(String statusText){
		this.statusText = statusText;
	}

	public String getStatusText(){
		return statusText;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setRealEstate(RealEstate realEstate){
		this.realEstate = realEstate;
	}

	public RealEstate getRealEstate(){
		return realEstate;
	}

	@Override
 	public String toString(){
		return 
			"Info{" + 
			"cost = '" + cost + '\'' + 
			",notes = '" + notes + '\'' + 
			",doc = '" + doc + '\'' + 
			",id = '" + id + '\'' + 
			",status_text = '" + statusText + '\'' + 
			",status = '" + status + '\'' + 
			",real_estate = '" + realEstate + '\'' + 
			"}";
		}
}