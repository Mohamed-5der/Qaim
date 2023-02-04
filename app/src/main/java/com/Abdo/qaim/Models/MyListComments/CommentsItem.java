package com.Abdo.qaim.Models.MyListComments;

import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("company")
	private Company company;

	@SerializedName("previewer")
	private Object previewer;

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private int id;

	@SerializedName("employee")
	private Object employee;

	public void setCompany(Company company){
		this.company = company;
	}

	public Company getCompany(){
		return company;
	}

	public void setPreviewer(Object previewer){
		this.previewer = previewer;
	}

	public Object getPreviewer(){
		return previewer;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return comment;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmployee(Object employee){
		this.employee = employee;
	}

	public Object getEmployee(){
		return employee;
	}

	@Override
 	public String toString(){
		return 
			"CommentsItem{" + 
			"company = '" + company + '\'' + 
			",previewer = '" + previewer + '\'' + 
			",comment = '" + comment + '\'' + 
			",id = '" + id + '\'' + 
			",employee = '" + employee + '\'' + 
			"}";
		}
}