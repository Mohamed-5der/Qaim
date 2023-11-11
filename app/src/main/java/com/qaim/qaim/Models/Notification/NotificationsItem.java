package com.qaim.qaim.Models.Notification;

import com.google.gson.annotations.SerializedName;

public class NotificationsItem{

	@SerializedName("sender")
	private Sender sender;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("type")
	private String type;

	@SerializedName("order_id")
	private int orderId;

	@SerializedName("content")
	private String content;

	@SerializedName("order")
	private Order order;

	public void setSender(Sender sender){
		this.sender = sender;
	}

	public Sender getSender(){
		return sender;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setOrder(Order order){
		this.order = order;
	}

	public Order getOrder(){
		return order;
	}

	@Override
 	public String toString(){
		return 
			"NotificationsItem{" + 
			"sender = '" + sender + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",type = '" + type + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",content = '" + content + '\'' + 
			",order = '" + order + '\'' + 
			"}";
		}
}