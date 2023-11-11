package com.qaim.qaim.Models.TeamReport;

import com.google.gson.annotations.SerializedName;

public class Row{

	@SerializedName("painter")
	private Painter painter;

	@SerializedName("previewer_file")
	private String previewerFile;

	@SerializedName("painter_file")
	private String painterFile;

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("id")
	private int id;

	@SerializedName("reviewer")
	private Reviewer reviewer;

	@SerializedName("reviewer_file")
	private String reviewerFile;

	public void setPainter(Painter painter){
		this.painter = painter;
	}

	public Painter getPainter(){
		return painter;
	}

	public void setPreviewerFile(String previewerFile){
		this.previewerFile = previewerFile;
	}

	public String getPreviewerFile(){
		return previewerFile;
	}

	public void setPainterFile(String painterFile){
		this.painterFile = painterFile;
	}

	public String getPainterFile(){
		return painterFile;
	}

	public void setPreviewer(Previewer previewer){
		this.previewer = previewer;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setReviewer(Reviewer reviewer){
		this.reviewer = reviewer;
	}

	public Reviewer getReviewer(){
		return reviewer;
	}

	public void setReviewerFile(String reviewerFile){
		this.reviewerFile = reviewerFile;
	}

	public String getReviewerFile(){
		return reviewerFile;
	}

	@Override
 	public String toString(){
		return 
			"Row{" + 
			"painter = '" + painter + '\'' + 
			",previewer_file = '" + previewerFile + '\'' + 
			",painter_file = '" + painterFile + '\'' + 
			",previewer = '" + previewer + '\'' + 
			",id = '" + id + '\'' + 
			",reviewer = '" + reviewer + '\'' + 
			",reviewer_file = '" + reviewerFile + '\'' + 
			"}";
		}
}