package com.qaim.qaim.Models.AssignTeam;

import com.google.gson.annotations.SerializedName;

public class Row{

	@SerializedName("cost")
	private String cost;

	@SerializedName("notes")
	private String notes;

	@SerializedName("color")
	private String color;

	@SerializedName("company_file")
	private String companyFile;

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("previewr_doc")
	private String previewrDoc;

	@SerializedName("reviewer")
	private Reviewer reviewer;

	@SerializedName("company_notes")
	private Object companyNotes;

	@SerializedName("painter")
	private Painter painter;

	@SerializedName("doc")
	private String doc;

	@SerializedName("company")
	private Company company;

	@SerializedName("id")
	private int id;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("status")
	private String status;

	@SerializedName("real_estate")
	private RealEstate realEstate;

	public String getCost(){
		return cost;
	}

	public String getNotes(){
		return notes;
	}

	public String getColor(){
		return color;
	}

	public String getCompanyFile(){
		return companyFile;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	public String getPreviewrDoc(){
		return previewrDoc;
	}

	public Reviewer getReviewer(){
		return reviewer;
	}

	public Object getCompanyNotes(){
		return companyNotes;
	}

	public Painter getPainter(){
		return painter;
	}

	public String getDoc(){
		return doc;
	}

	public Company getCompany(){
		return company;
	}

	public int getId(){
		return id;
	}

	public String getStatusText(){
		return statusText;
	}

	public String getStatus(){
		return status;
	}

	public RealEstate getRealEstate(){
		return realEstate;
	}
}