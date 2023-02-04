package com.Abdo.qaim.Models.CompanyMakeReport;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("report")
	private Report report;

	public void setReport(Report report){
		this.report = report;
	}

	public Report getReport(){
		return report;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"report = '" + report + '\'' + 
			"}";
		}
}