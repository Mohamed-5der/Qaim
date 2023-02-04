package com.Abdo.qaim.Models.OrderListUserResponse;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("doc")
	private String doc;

	@SerializedName("company")
	private Company company;

	@SerializedName("id")
	private int id;

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

	public void setCompany(Company company){
		this.company = company;
	}

	public Company getCompany(){
		return company;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"cost = '" + cost + '\'' + 
			",notes = '" + notes + '\'' + 
			",doc = '" + doc + '\'' + 
			",company = '" + company + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}