package com.qaim.qaim.Models.ShowPrevReportEmp;

import com.google.gson.annotations.SerializedName;
import com.qaim.qaim.Models.ShowCompanyPrevReport.ImagesItem;

import java.util.List;

public class Row{

	@SerializedName("nerest_commerial_street")
	private String nerestCommerialStreet;

	@SerializedName("client_city")
	private ClientCity clientCity;

	@SerializedName("rate_report_date")
	private String rateReportDate;

	@SerializedName("notes")
	private String notes;

	@SerializedName("color")
	private String color;

	@SerializedName("city")
	private City city;

	@SerializedName("rate_report_type")
	private String rateReportType;

	@SerializedName("rate_terms")
	private String rateTerms;

	@SerializedName("document")
	private String document;

	@SerializedName("client_reasons")
	private String clientReasons;

	@SerializedName("client_type")
	private Object clientType;

	@SerializedName("street_name")
	private String streetName;

	@SerializedName("images")
	private List<ImagesItem> images;

	public List<ImagesItem> getImages() {
		return images;
	}

	@SerializedName("rate_day_date")
	private String rateDayDate;

	@SerializedName("rate_type")
	private String rateType;

	@SerializedName("rate_ref_no")
	private String rateRefNo;

	@SerializedName("previewer_rate")
	private Object previewerRate;

	@SerializedName("document_name")
	private String documentName;

	@SerializedName("client_username")
	private String clientUsername;

	@SerializedName("rate_type_done")
	private String rateTypeDone;

	@SerializedName("company")
	private Company company;

	@SerializedName("boundries")
	private String boundries;

	@SerializedName("nerest_commerial_street_distance")
	private String nerestCommerialStreetDistance;

	@SerializedName("id")
	private int id;

	@SerializedName("client_name")
	private String clientName;

	@SerializedName("company_rate")
	private Object companyRate;

	@SerializedName("info")
	private Info info;

	@SerializedName("rate_independant_txt")
	private String rateIndependantTxt;

	@SerializedName("rate_final_file_name")
	private String rateFinalFileName;

	@SerializedName("client_owner_type")
	private String clientOwnerType;

	@SerializedName("interfaces")
	private String interfaces;

	@SerializedName("rate_amount_usage")
	private String rateAmountUsage;

	@SerializedName("is_accepted")
	private String isAccepted;

	@SerializedName("distance_to_city_center")
	private String distanceToCityCenter;

	@SerializedName("attributed")
	private String attributed;

	@SerializedName("is_accepted_text")
	private String isAcceptedText;

	@SerializedName("previewer")
	private Previewer previewer;

	@SerializedName("services")
	private String services;

	@SerializedName("location_txt")
	private String locationTxt;

	@SerializedName("rate_steps")
	private String rateSteps;

	@SerializedName("rate_preview_date")
	private String ratePreviewDate;

	@SerializedName("land_lines")
	private String landLines;

	@SerializedName("rate_amount")
	private String rateAmount;

	@SerializedName("attributes")
	private List<AttributesItem> attributes;

	@SerializedName("status_text")
	private String statusText;

	@SerializedName("region")
	private Region region;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("rate_final_file")
	private String rateFinalFile;

	@SerializedName("status")
	private String status;

	public String getNerestCommerialStreet(){
		return nerestCommerialStreet;
	}

	public ClientCity getClientCity(){
		return clientCity;
	}

	public String getRateReportDate(){
		return rateReportDate;
	}

	public String getNotes(){
		return notes;
	}

	public String getColor(){
		return color;
	}

	public City getCity(){
		return city;
	}

	public String getRateReportType(){
		return rateReportType;
	}

	public String getRateTerms(){
		return rateTerms;
	}

	public String getDocument(){
		return document;
	}

	public String getClientReasons(){
		return clientReasons;
	}

	public Object getClientType(){
		return clientType;
	}

	public String getStreetName(){
		return streetName;
	}

	public String getRateDayDate(){
		return rateDayDate;
	}

	public String getRateType(){
		return rateType;
	}

	public String getRateRefNo(){
		return rateRefNo;
	}

	public Object getPreviewerRate(){
		return previewerRate;
	}

	public String getDocumentName(){
		return documentName;
	}

	public String getClientUsername(){
		return clientUsername;
	}

	public String getRateTypeDone(){
		return rateTypeDone;
	}

	public Company getCompany(){
		return company;
	}

	public String getBoundries(){
		return boundries;
	}

	public String getNerestCommerialStreetDistance(){
		return nerestCommerialStreetDistance;
	}

	public int getId(){
		return id;
	}

	public String getClientName(){
		return clientName;
	}

	public Object getCompanyRate(){
		return companyRate;
	}

	public Info getInfo(){
		return info;
	}

	public String getRateIndependantTxt(){
		return rateIndependantTxt;
	}

	public String getRateFinalFileName(){
		return rateFinalFileName;
	}

	public String getClientOwnerType(){
		return clientOwnerType;
	}

	public String getInterfaces(){
		return interfaces;
	}

	public String getRateAmountUsage(){
		return rateAmountUsage;
	}

	public String getIsAccepted(){
		return isAccepted;
	}

	public String getDistanceToCityCenter(){
		return distanceToCityCenter;
	}

	public String getAttributed(){
		return attributed;
	}

	public String getIsAcceptedText(){
		return isAcceptedText;
	}

	public Previewer getPreviewer(){
		return previewer;
	}

	public String getServices(){
		return services;
	}

	public String getLocationTxt(){
		return locationTxt;
	}

	public String getRateSteps(){
		return rateSteps;
	}

	public String getRatePreviewDate(){
		return ratePreviewDate;
	}

	public String getLandLines(){
		return landLines;
	}

	public String getRateAmount(){
		return rateAmount;
	}

	public List<AttributesItem> getAttributes(){
		return attributes;
	}

	public String getStatusText(){
		return statusText;
	}

	public Region getRegion(){
		return region;
	}

	public int getOrderId(){
		return orderId;
	}

	public String getRateFinalFile(){
		return rateFinalFile;
	}

	public String getStatus(){
		return status;
	}
}