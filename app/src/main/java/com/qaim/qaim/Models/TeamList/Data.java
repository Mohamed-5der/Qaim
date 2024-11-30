package com.qaim.qaim.Models.TeamList;

import com.google.gson.annotations.SerializedName;
import com.qaim.qaim.Models.MyListTeamReports.Painter;
import com.qaim.qaim.Models.MyListTeamReports.Reviewer;

import java.util.List;

public class Data{

	@SerializedName("painters")
	private List<Painter> painters;

	@SerializedName("reviewers")
	private List<Reviewer> reviewers;

	public void setPainters(List<Painter> painters){
		this.painters = painters;
	}

	public List<Painter> getPainters(){
		return painters;
	}

	public void setReviewers(List<Reviewer> reviewers){
		this.reviewers = reviewers;
	}

	public List<Reviewer> getReviewers(){
		return reviewers;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"painters = '" + painters + '\'' + 
			",reviewers = '" + reviewers + '\'' + 
			"}";
		}
}