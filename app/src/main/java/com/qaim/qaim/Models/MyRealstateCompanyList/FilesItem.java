package com.qaim.qaim.Models.MyRealstateCompanyList;

import com.google.gson.annotations.SerializedName;

public class FilesItem{

	@SerializedName("file")
	private String file;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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
			"FilesItem{" + 
			"file = '" + file + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}