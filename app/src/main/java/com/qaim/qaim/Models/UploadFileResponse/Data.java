package com.qaim.qaim.Models.UploadFileResponse;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("file_url")
	private String fileUrl;

	@SerializedName("file_name")
	private String fileName;

	public void setFileUrl(String fileUrl){
		this.fileUrl = fileUrl;
	}

	public String getFileUrl(){
		return fileUrl;
	}

	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getFileName(){
		return fileName;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"file_url = '" + fileUrl + '\'' + 
			",file_name = '" + fileName + '\'' + 
			"}";
		}
}