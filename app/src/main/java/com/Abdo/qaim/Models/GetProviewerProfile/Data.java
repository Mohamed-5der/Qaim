package com.Abdo.qaim.Models.GetProviewerProfile;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("previewer")
	private Previewer previewer;

	public void setPreviewer(Previewer previewer){
		this.previewer = previewer;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"previewer = '" + previewer + '\'' + 
			"}";
		}
}