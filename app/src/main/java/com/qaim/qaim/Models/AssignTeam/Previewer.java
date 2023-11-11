package com.qaim.qaim.Models.AssignTeam;

import com.google.gson.annotations.SerializedName;

public class Previewer{

	@SerializedName("image")
	private String image;

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("notes")
	private String notes;

	@SerializedName("previewer_doc")
	private String prevDoc;

	public String getPrevDoc() {
		return prevDoc;
	}

	public void setPrevDoc(String prevDoc) {
		this.prevDoc = prevDoc;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}



	public String getImage(){
		return image;
	}

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}
}