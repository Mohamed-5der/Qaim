package com.qaim.qaim.Models.EmployeeComments;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("comments")
	private List<CommentsItem> comments;

	public void setComments(List<CommentsItem> comments){
		this.comments = comments;
	}

	public List<CommentsItem> getComments(){
		return comments;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"comments = '" + comments + '\'' + 
			"}";
		}
}