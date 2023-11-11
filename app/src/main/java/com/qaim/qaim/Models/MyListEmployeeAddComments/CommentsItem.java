package com.qaim.qaim.Models.MyListEmployeeAddComments;

import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("company")
	private Object company;

	@SerializedName("previewer")
	private Object previewer;

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private int id;

	@SerializedName("employee")
	private Employee employee;

	public void setCompany(Object company){
		this.company = company;
	}

	public Object getCompany(){
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

	public void setEmployee(Employee employee){
		this.employee = employee;
	}

	public Employee getEmployee(){
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