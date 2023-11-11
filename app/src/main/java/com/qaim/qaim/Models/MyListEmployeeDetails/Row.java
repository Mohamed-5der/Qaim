package com.qaim.qaim.Models.MyListEmployeeDetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Row{

	@SerializedName("hide_all_btns")
	private int hideAllBtns;

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("color")
	private String color;

	@SerializedName("company_file")
	private String companyFile;

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("previewr_doc")
	private String previewrDoc;

	@SerializedName("reviewer")
	private Reviewer reviewer;

	@SerializedName("company_notes")
	private String companyNotes;

	@SerializedName("has_completed")
	private int hasCompleted;

	@SerializedName("painter")
	private Painter painter;

	@SerializedName("doc_name")
	private String docName;

	@SerializedName("doc")
	private String doc;

	@SerializedName("company")
	private Company company;

	@SerializedName("attributes")
	private List<AttributesItem> attributes;

	@SerializedName("id")
	private int id;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("has_report")
	private int hasReport;

	@SerializedName("status")
	private String status;

	@SerializedName("real_estate")
	private RealEstate realEstate;

	public void setHideAllBtns(int hideAllBtns){
		this.hideAllBtns = hideAllBtns;
	}

	public int getHideAllBtns(){
		return hideAllBtns;
	}

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

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setCompanyFile(String companyFile){
		this.companyFile = companyFile;
	}

	public String getCompanyFile(){
		return companyFile;
	}

	public void setPreviewer(Previewer previewer){
		this.previewer = previewer;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	public void setPreviewrDoc(String previewrDoc){
		this.previewrDoc = previewrDoc;
	}

	public String getPreviewrDoc(){
		return previewrDoc;
	}

	public void setReviewer(Reviewer reviewer){
		this.reviewer = reviewer;
	}

	public Reviewer getReviewer(){
		return reviewer;
	}

	public void setCompanyNotes(String companyNotes){
		this.companyNotes = companyNotes;
	}

	public String getCompanyNotes(){
		return companyNotes;
	}

	public void setHasCompleted(int hasCompleted){
		this.hasCompleted = hasCompleted;
	}

	public int getHasCompleted(){
		return hasCompleted;
	}

	public void setPainter(Painter painter){
		this.painter = painter;
	}

	public Painter getPainter(){
		return painter;
	}

	public void setDocName(String docName){
		this.docName = docName;
	}

	public String getDocName(){
		return docName;
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
			"hide_all_btns = '" + hideAllBtns + '\'' + 
			",cost = '" + cost + '\'' + 
			",notes = '" + notes + '\'' + 
			",color = '" + color + '\'' + 
			",company_file = '" + companyFile + '\'' + 
			",previewer = '" + previewer + '\'' + 
			",previewr_doc = '" + previewrDoc + '\'' + 
			",reviewer = '" + reviewer + '\'' + 
			",company_notes = '" + companyNotes + '\'' + 
			",has_completed = '" + hasCompleted + '\'' + 
			",painter = '" + painter + '\'' + 
			",doc_name = '" + docName + '\'' + 
			",doc = '" + doc + '\'' + 
			",company = '" + company + '\'' + 
			",attributes = '" + attributes + '\'' + 
			",id = '" + id + '\'' + 
			",status_text = '" + statusText + '\'' + 
			",has_report = '" + hasReport + '\'' + 
			",status = '" + status + '\'' + 
			",real_estate = '" + realEstate + '\'' + 
			"}";
		}
}