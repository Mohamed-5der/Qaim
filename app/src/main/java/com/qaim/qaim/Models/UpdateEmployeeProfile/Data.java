package com.qaim.qaim.Models.UpdateEmployeeProfile;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("employee")
	private Employee employee;

	public void setEmployee(Employee employee){
		this.employee = employee;
	}

	public Employee getEmployee(){
		return employee;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"employee = '" + employee + '\'' + 
			"}";
		}
}