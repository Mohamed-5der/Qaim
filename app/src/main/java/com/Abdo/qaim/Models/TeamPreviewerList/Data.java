package com.Abdo.qaim.Models.TeamPreviewerList;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("previewers")
	private Previewers previewers;

	public void setPreviewers(Previewers previewers){
		this.previewers = previewers;
	}

	public Previewers getPreviewers(){
		return previewers;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"previewers = '" + previewers + '\'' + 
			"}";
		}
}