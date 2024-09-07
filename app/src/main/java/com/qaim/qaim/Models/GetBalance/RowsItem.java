package com.qaim.qaim.Models.GetBalance;

import com.google.gson.annotations.SerializedName;

public class RowsItem{

	@SerializedName("cost")
	private String cost;

	@SerializedName("previewer_cost")
	private int previewerCost;

//	@SerializedName("qaim_net")
//	private int qaimNet;

	@SerializedName("description")
	private String description;

	@SerializedName("company_cost")
	private int companyCost;

	@SerializedName("id")
	private int id;

	@SerializedName("real_estate")
	private RealEstate realEstate;

	public String getCost(){
		return cost;
	}

	public int getPreviewerCost(){
		return previewerCost;
	}

//	public int getQaimNet(){
//		return qaimNet;
//	}

	public String getDescription(){
		return description;
	}

	public int getCompanyCost(){
		return companyCost;
	}

	public int getId(){
		return id;
	}

	public RealEstate getRealEstate(){
		return realEstate;
	}
}