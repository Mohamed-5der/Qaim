package com.qaim.qaim.Models.EmployeeComments;

import com.qaim.qaim.Models.AddListComments.Previewer;
import com.google.gson.annotations.SerializedName;

public class CommentsItem{

	@SerializedName("company")
	private Company company;

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("comment")
	private String comment;

	@SerializedName("type")
	private String type;

	@SerializedName("file_type")
	private String fileType;

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

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setFileType(String fileType){
		this.fileType = fileType;
	}

	public String getFileType(){
		return fileType;
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