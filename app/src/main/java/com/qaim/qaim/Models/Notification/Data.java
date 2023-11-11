package com.qaim.qaim.Models.Notification;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("notifications")
	private List<NotificationsItem> notifications;

	public void setNotifications(List<NotificationsItem> notifications){
		this.notifications = notifications;
	}

	public List<NotificationsItem> getNotifications(){
		return notifications;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"notifications = '" + notifications + '\'' + 
			"}";
		}
}