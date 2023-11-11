package com.qaim.qaim.Models.MyListPrevDetails;

import com.google.gson.annotations.SerializedName;

public class Row{

	@SerializedName("notes")
	private String notes;

	@SerializedName("cost")
	private String cost;

	@SerializedName("color")
	private String color;

	@SerializedName("company")
	private Company company;

	@SerializedName("id")
	private int id;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("has_report")
	private int hasReport;

	@SerializedName("status")
	private String status;

	@SerializedName("info")
	private Info info;

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
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

	public void setStatusText(String statusText){
		this.statusText = statusText;
	}

	public String getStatusText(){
		return statusText;
	}

	public void setHasReport(int hasReport){
		this.hasReport = hasReport;
	}

	public int getHasReport(){
		return hasReport;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setInfo(Info info){
		this.info = info;
	}

	public Info getInfo(){
		return info;
	}

	@Override
 	public String toString(){
		return 
			"Row{" + 
			"notes = '" + notes + '\'' + 
			",cost = '" + cost + '\'' + 
			",color = '" + color + '\'' + 
			",company = '" + company + '\'' + 
			",id = '" + id + '\'' + 
			",status_text = '" + statusText + '\'' + 
			",has_report = '" + hasReport + '\'' + 
			",status = '" + status + '\'' + 
			",info = '" + info + '\'' + 
			"}";
		}
}