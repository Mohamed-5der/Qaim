package com.Abdo.qaim.Models.MyListEmployeeDetails;

import com.google.gson.annotations.SerializedName;

public class Row{

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("company_file")
	private String companyFile;

	@SerializedName("doc")
	private String doc;

	@SerializedName("company")
	private Company company;

	@SerializedName("previewer")
	private Object previewer;

	@SerializedName("previewr_doc")
	private String previewrDoc;

	@SerializedName("id")
	private int id;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("company_notes")
	private Object companyNotes;

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

	public void setCompanyFile(String companyFile){
		this.companyFile = companyFile;
	}

	public String getCompanyFile(){
		return companyFile;
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

	public void setPreviewer(Object previewer){
		this.previewer = previewer;
	}

	public Object getPreviewer(){
		return previewer;
	}

	public void setPreviewrDoc(String previewrDoc){
		this.previewrDoc = previewrDoc;
	}

	public String getPreviewrDoc(){
		return previewrDoc;
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

	public void setCompanyNotes(Object companyNotes){
		this.companyNotes = companyNotes;
	}

	public Object getCompanyNotes(){
		return companyNotes;
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
			"Row{" + 
			"cost = '" + cost + '\'' + 
			",notes = '" + notes + '\'' + 
			",company_file = '" + companyFile + '\'' + 
			",doc = '" + doc + '\'' + 
			",company = '" + company + '\'' + 
			",previewer = '" + previewer + '\'' + 
			",previewr_doc = '" + previewrDoc + '\'' + 
			",id = '" + id + '\'' + 
			",status_text = '" + statusText + '\'' + 
			",company_notes = '" + companyNotes + '\'' + 
			",status = '" + status + '\'' + 
			",real_estate = '" + realEstate + '\'' + 
			"}";
		}
}