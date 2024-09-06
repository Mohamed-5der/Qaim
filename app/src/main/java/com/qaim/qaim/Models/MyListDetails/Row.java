package com.qaim.qaim.Models.MyListDetails;

import com.google.gson.annotations.SerializedName;
import com.qaim.qaim.Models.RealstateShowUserResponse.NoteItem;

import java.util.List;

public class Row{

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("hide_all_btns")
	private int hideAllBtns;

	public int getHideAllBtns() {
		return hideAllBtns;
	}

	public void setHideAllBtns(int hideAllBtns) {
		this.hideAllBtns = hideAllBtns;
	}

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

	@SerializedName("has_report")
	private int hasReport;

	public int getHasReport() {
		return hasReport;
	}

	public void setHasReport(int hasReport) {
		this.hasReport = hasReport;
	}

	@SerializedName("painter")
	private Painter painter;

	@SerializedName("doc")
	private String doc;

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	@SerializedName("doc_name")
	private String docName;

	@SerializedName("company")
	private Company company;

	@SerializedName("id")
	private int id;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("status")
	private String status;

	@SerializedName("real_estate")
	private RealEstate realEstate;

	@SerializedName("notes_list")
	private List<NoteItem> notesList;

	@SerializedName("previewer_notes_list")
	private List<NoteItem> previewerNotesList;

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

	public List<NoteItem> getNotesList() {
		return notesList;
	}

	public void setNotesList(List<NoteItem> notesList) {
		this.notesList = notesList;
	}

	public List<NoteItem> getPreviewerNotesList() {
		return previewerNotesList;
	}

	public void setPreviewerNotesList(List<NoteItem> previewerNotesList) {
		this.previewerNotesList = previewerNotesList;
	}

	@Override
 	public String toString(){
		return 
			"Row{" + 
			"cost = '" + cost + '\'' + 
			",notes = '" + notes + '\'' + 
			",color = '" + color + '\'' + 
			",company_file = '" + companyFile + '\'' + 
			",previewer = '" + previewer + '\'' + 
			",previewr_doc = '" + previewrDoc + '\'' + 
			",reviewer = '" + reviewer + '\'' + 
			",company_notes = '" + companyNotes + '\'' + 
			",has_completed = '" + hasCompleted + '\'' + 
			",painter = '" + painter + '\'' + 
			",doc = '" + doc + '\'' + 
			",company = '" + company + '\'' + 
			",id = '" + id + '\'' + 
			",status_text = '" + statusText + '\'' + 
			",status = '" + status + '\'' + 
			",real_estate = '" + realEstate + '\'' + 
			"}";
		}
}