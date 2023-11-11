package com.qaim.qaim.Models.ShowCompanyPrevReport;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Row{

	@SerializedName("rate_report_date")
	private String rateReportDate;

	@SerializedName("notes")
	private String notes;

	@SerializedName("rate_report_type")
	private String rateReportType;

	@SerializedName("client_reasons")
	private String clientReasons;

	@SerializedName("client_type")
	private Object clientType;

	@SerializedName("rate_ref_no")
	private String rateRefNo;

	@SerializedName("previewer_rate")
	private Object previewerRate;

	@SerializedName("client_username")
	private String clientUsername;

	@SerializedName("rate_type_done")
	private String rateTypeDone;

	@SerializedName("nerest_commerial_street_distance")
	private String nerestCommerialStreetDistance;

	@SerializedName("id")
	private int id;

	@SerializedName("info")
	private Info info;

	@SerializedName("rate_independant_txt")
	private String rateIndependantTxt;

	@SerializedName("rate_final_file_name")
	private String rateFinalFileName;

	@SerializedName("client_owner_type")
	private String clientOwnerType;

	@SerializedName("is_accepted")
	private String isAccepted;

	@SerializedName("images")
	private List<ImagesItem> images;

	@SerializedName("is_accepted_text")
	private String isAcceptedText;

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("rate_steps")
	private String rateSteps;

	@SerializedName("rate_amount")
	private String rateAmount;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("region")
	private String region;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("status")
	private String status;

	@SerializedName("nerest_commerial_street")
	private String nerestCommerialStreet;

	@SerializedName("client_city")
	private Object clientCity;

	@SerializedName("color")
	private String color;

	@SerializedName("city")
	private String city;

	@SerializedName("rate_terms")
	private String rateTerms;

	@SerializedName("document")
	private String document;

	@SerializedName("street_name")
	private String streetName;

	@SerializedName("rate_day_date")
	private String rateDayDate;

	@SerializedName("rate_type")
	private String rateType;

	@SerializedName("document_name")
	private String documentName;

	@SerializedName("company")
	private Company company;

	@SerializedName("boundries")
	private String boundries;

	@SerializedName("client_name")
	private String clientName;

	@SerializedName("company_rate")
	private Object companyRate;

	@SerializedName("interfaces")
	private String interfaces;

	@SerializedName("rate_amount_usage")
	private String rateAmountUsage;

	@SerializedName("distance_to_city_center")
	private String distanceToCityCenter;

	@SerializedName("attributed")
	private String attributed;

	@SerializedName("services")
	private String services;

	@SerializedName("location_txt")
	private String locationTxt;

	@SerializedName("rate_preview_date")
	private String ratePreviewDate;

	@SerializedName("land_lines")
	private String landLines;

	@SerializedName("attributes")
	private List<AttributesItem> attributes;

	@SerializedName("rate_final_file")
	private String rateFinalFile;

	public void setRateReportDate(String rateReportDate){
		this.rateReportDate = rateReportDate;
	}

	public String getRateReportDate(){
		return rateReportDate;
	}

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setRateReportType(String rateReportType){
		this.rateReportType = rateReportType;
	}

	public String getRateReportType(){
		return rateReportType;
	}

	public void setClientReasons(String clientReasons){
		this.clientReasons = clientReasons;
	}

	public String getClientReasons(){
		return clientReasons;
	}

	public void setClientType(Object clientType){
		this.clientType = clientType;
	}

	public Object getClientType(){
		return clientType;
	}

	public void setRateRefNo(String rateRefNo){
		this.rateRefNo = rateRefNo;
	}

	public String getRateRefNo(){
		return rateRefNo;
	}

	public void setPreviewerRate(Object previewerRate){
		this.previewerRate = previewerRate;
	}

	public Object getPreviewerRate(){
		return previewerRate;
	}

	public void setClientUsername(String clientUsername){
		this.clientUsername = clientUsername;
	}

	public String getClientUsername(){
		return clientUsername;
	}

	public void setRateTypeDone(String rateTypeDone){
		this.rateTypeDone = rateTypeDone;
	}

	public String getRateTypeDone(){
		return rateTypeDone;
	}

	public void setNerestCommerialStreetDistance(String nerestCommerialStreetDistance){
		this.nerestCommerialStreetDistance = nerestCommerialStreetDistance;
	}

	public String getNerestCommerialStreetDistance(){
		return nerestCommerialStreetDistance;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setInfo(Info info){
		this.info = info;
	}

	public Info getInfo(){
		return info;
	}

	public void setRateIndependantTxt(String rateIndependantTxt){
		this.rateIndependantTxt = rateIndependantTxt;
	}

	public String getRateIndependantTxt(){
		return rateIndependantTxt;
	}

	public void setRateFinalFileName(String rateFinalFileName){
		this.rateFinalFileName = rateFinalFileName;
	}

	public String getRateFinalFileName(){
		return rateFinalFileName;
	}

	public void setClientOwnerType(String clientOwnerType){
		this.clientOwnerType = clientOwnerType;
	}

	public String getClientOwnerType(){
		return clientOwnerType;
	}

	public void setIsAccepted(String isAccepted){
		this.isAccepted = isAccepted;
	}

	public String getIsAccepted(){
		return isAccepted;
	}

	public void setImages(List<ImagesItem> images){
		this.images = images;
	}

	public List<ImagesItem> getImages(){
		return images;
	}

	public void setIsAcceptedText(String isAcceptedText){
		this.isAcceptedText = isAcceptedText;
	}

	public String getIsAcceptedText(){
		return isAcceptedText;
	}

	public void setPreviewer(Previewer previewer){
		this.previewer = previewer;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	public void setRateSteps(String rateSteps){
		this.rateSteps = rateSteps;
	}

	public String getRateSteps(){
		return rateSteps;
	}

	public void setRateAmount(String rateAmount){
		this.rateAmount = rateAmount;
	}

	public String getRateAmount(){
		return rateAmount;
	}

	public void setStatusText(String statusText){
		this.statusText = statusText;
	}

	public String getStatusText(){
		return statusText;
	}

	public void setRegion(String region){
		this.region = region;
	}

	public String getRegion(){
		return region;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setNerestCommerialStreet(String nerestCommerialStreet){
		this.nerestCommerialStreet = nerestCommerialStreet;
	}

	public String getNerestCommerialStreet(){
		return nerestCommerialStreet;
	}

	public void setClientCity(Object clientCity){
		this.clientCity = clientCity;
	}

	public Object getClientCity(){
		return clientCity;
	}

	public void setColor(String color){
		this.color = color;
	}

	public String getColor(){
		return color;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setRateTerms(String rateTerms){
		this.rateTerms = rateTerms;
	}

	public String getRateTerms(){
		return rateTerms;
	}

	public void setDocument(String document){
		this.document = document;
	}

	public String getDocument(){
		return document;
	}

	public void setStreetName(String streetName){
		this.streetName = streetName;
	}

	public String getStreetName(){
		return streetName;
	}

	public void setRateDayDate(String rateDayDate){
		this.rateDayDate = rateDayDate;
	}

	public String getRateDayDate(){
		return rateDayDate;
	}

	public void setRateType(String rateType){
		this.rateType = rateType;
	}

	public String getRateType(){
		return rateType;
	}

	public void setDocumentName(String documentName){
		this.documentName = documentName;
	}

	public String getDocumentName(){
		return documentName;
	}

	public void setCompany(Company company){
		this.company = company;
	}

	public Company getCompany(){
		return company;
	}

	public void setBoundries(String boundries){
		this.boundries = boundries;
	}

	public String getBoundries(){
		return boundries;
	}

	public void setClientName(String clientName){
		this.clientName = clientName;
	}

	public String getClientName(){
		return clientName;
	}

	public void setCompanyRate(Object companyRate){
		this.companyRate = companyRate;
	}

	public Object getCompanyRate(){
		return companyRate;
	}

	public void setInterfaces(String interfaces){
		this.interfaces = interfaces;
	}

	public String getInterfaces(){
		return interfaces;
	}

	public void setRateAmountUsage(String rateAmountUsage){
		this.rateAmountUsage = rateAmountUsage;
	}

	public String getRateAmountUsage(){
		return rateAmountUsage;
	}

	public void setDistanceToCityCenter(String distanceToCityCenter){
		this.distanceToCityCenter = distanceToCityCenter;
	}

	public String getDistanceToCityCenter(){
		return distanceToCityCenter;
	}

	public void setAttributed(String attributed){
		this.attributed = attributed;
	}

	public String getAttributed(){
		return attributed;
	}

	public void setServices(String services){
		this.services = services;
	}

	public String getServices(){
		return services;
	}

	public void setLocationTxt(String locationTxt){
		this.locationTxt = locationTxt;
	}

	public String getLocationTxt(){
		return locationTxt;
	}

	public void setRatePreviewDate(String ratePreviewDate){
		this.ratePreviewDate = ratePreviewDate;
	}

	public String getRatePreviewDate(){
		return ratePreviewDate;
	}

	public void setLandLines(String landLines){
		this.landLines = landLines;
	}

	public String getLandLines(){
		return landLines;
	}

	public void setAttributes(List<AttributesItem> attributes){
		this.attributes = attributes;
	}

	public List<AttributesItem> getAttributes(){
		return attributes;
	}

	public void setRateFinalFile(String rateFinalFile){
		this.rateFinalFile = rateFinalFile;
	}

	public String getRateFinalFile(){
		return rateFinalFile;
	}

	@Override
 	public String toString(){
		return 
			"Row{" + 
			"rate_report_date = '" + rateReportDate + '\'' + 
			",notes = '" + notes + '\'' + 
			",rate_report_type = '" + rateReportType + '\'' + 
			",client_reasons = '" + clientReasons + '\'' + 
			",client_type = '" + clientType + '\'' + 
			",rate_ref_no = '" + rateRefNo + '\'' + 
			",previewer_rate = '" + previewerRate + '\'' + 
			",client_username = '" + clientUsername + '\'' + 
			",rate_type_done = '" + rateTypeDone + '\'' + 
			",nerest_commerial_street_distance = '" + nerestCommerialStreetDistance + '\'' + 
			",id = '" + id + '\'' + 
			",info = '" + info + '\'' + 
			",rate_independant_txt = '" + rateIndependantTxt + '\'' + 
			",rate_final_file_name = '" + rateFinalFileName + '\'' + 
			",client_owner_type = '" + clientOwnerType + '\'' + 
			",is_accepted = '" + isAccepted + '\'' + 
			",images = '" + images + '\'' + 
			",is_accepted_text = '" + isAcceptedText + '\'' + 
			",previewer = '" + previewer + '\'' + 
			",rate_steps = '" + rateSteps + '\'' + 
			",rate_amount = '" + rateAmount + '\'' + 
			",status_text = '" + statusText + '\'' + 
			",region = '" + region + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",status = '" + status + '\'' + 
			",nerest_commerial_street = '" + nerestCommerialStreet + '\'' + 
			",client_city = '" + clientCity + '\'' + 
			",color = '" + color + '\'' + 
			",city = '" + city + '\'' + 
			",rate_terms = '" + rateTerms + '\'' + 
			",document = '" + document + '\'' + 
			",street_name = '" + streetName + '\'' + 
			",rate_day_date = '" + rateDayDate + '\'' + 
			",rate_type = '" + rateType + '\'' + 
			",document_name = '" + documentName + '\'' + 
			",company = '" + company + '\'' + 
			",boundries = '" + boundries + '\'' + 
			",client_name = '" + clientName + '\'' + 
			",company_rate = '" + companyRate + '\'' + 
			",interfaces = '" + interfaces + '\'' + 
			",rate_amount_usage = '" + rateAmountUsage + '\'' + 
			",distance_to_city_center = '" + distanceToCityCenter + '\'' + 
			",attributed = '" + attributed + '\'' + 
			",services = '" + services + '\'' + 
			",location_txt = '" + locationTxt + '\'' + 
			",rate_preview_date = '" + ratePreviewDate + '\'' + 
			",land_lines = '" + landLines + '\'' + 
			",attributes = '" + attributes + '\'' + 
			",rate_final_file = '" + rateFinalFile + '\'' + 
			"}";
		}
}