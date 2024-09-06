package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class FilesItem{

	@SerializedName("file")
	private String file;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public String getFile(){
		return file;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}