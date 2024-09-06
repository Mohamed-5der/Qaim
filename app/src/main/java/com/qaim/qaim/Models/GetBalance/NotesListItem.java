package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class NotesListItem{

	@SerializedName("notes")
	private String notes;

	@SerializedName("by")
	private String by;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	public String getNotes(){
		return notes;
	}

	public String getBy(){
		return by;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}
}