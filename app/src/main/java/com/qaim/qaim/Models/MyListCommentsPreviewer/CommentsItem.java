package com.qaim.qaim.Models.MyListCommentsPreviewer;

import com.qaim.qaim.Models.EmployeeProfile.Employee;
import com.qaim.qaim.Models.MyListTeamReports.Previewer;
import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("company")
	private Company company;

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("comment")
	private String comment;

	@SerializedName("id")
	private int id;

	@SerializedName("employee")
	private Employee employee;

	public void setCompany(Company company){
		this.company = company;
	}

	public Company getCompany(){
		return company;
	}

	public void setPreviewer(Previewer previewer){
		this.previewer = previewer;
	}

	public Previewer getPreviewer(){
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