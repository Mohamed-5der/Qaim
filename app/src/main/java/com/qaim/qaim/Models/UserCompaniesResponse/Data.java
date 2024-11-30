package com.qaim.qaim.Models.UserCompaniesResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

	@SerializedName("rows")
	private List<RowsItem> rows;

	public void setRows(List<RowsItem> rows){
		this.rows = rows;
	}

	public List<RowsItem> getRows(){
		return rows;
	}
}