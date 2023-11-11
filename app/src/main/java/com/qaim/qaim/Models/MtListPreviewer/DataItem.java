package com.qaim.qaim.Models.MtListPreviewer;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("notes")
	private String notes;

	@SerializedName("company")
	private Company company;

	@SerializedName("id")
	private int id;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("status")
	private String status;

	@SerializedName("info")
	private Info info;

	@SerializedName("can_add_report")
	private int canAddReport;

	public int getCanAddReport() {
		return canAddReport;
	}

	public void setCanAddReport(int canAddReport) {
		this.canAddReport = canAddReport;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
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
			"DataItem{" + 
			"notes = '" + notes + '\'' + 
			",company = '" + company + '\'' + 
			",id = '" + id + '\'' + 
			",status_text = '" + statusText + '\'' + 
			",status = '" + status + '\'' + 
			",info = '" + info + '\'' + 
			"}";
		}
}