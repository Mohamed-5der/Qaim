package com.qaim.qaim.Models.TeamPreviewerList;

import com.google.gson.annotations.SerializedName;

public class LinksItem{

	@SerializedName("active")
	private boolean active;

	@SerializedName("label")
	private String label;

	@SerializedName("url")
	private String url;

	public void setActive(boolean active){
		this.active = active;
	}

	public boolean isActive(){
		return active;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"LinksItem{" + 
			"active = '" + active + '\'' + 
			",label = '" + label + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}