package com.qaim.qaim.Models.UserCompaniesResponse;

import java.util.List;
import com.google.gson.annotations.SerializedName;

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