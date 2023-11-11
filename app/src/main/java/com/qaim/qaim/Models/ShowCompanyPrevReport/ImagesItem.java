package com.qaim.qaim.Models.ShowCompanyPrevReport;

import com.google.gson.annotations.SerializedName;

public class ImagesItem{

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
			"ImagesItem{" + 
			"file = '" + file + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}