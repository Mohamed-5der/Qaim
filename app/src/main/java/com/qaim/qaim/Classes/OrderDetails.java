package com.qaim.qaim.Classes;

public class OrderDetails {
    String nameOfOrder , descriptionOfOrder ;

    public OrderDetails(String nameOfOrder, String descriptionOfOrder) {
        this.nameOfOrder = nameOfOrder;
        this.descriptionOfOrder = descriptionOfOrder;
    }

    public String getNameOfOrder() {
        return nameOfOrder;
    }

    public void setNameOfOrder(String nameOfOrder) {
        this.nameOfOrder = nameOfOrder;
    }

    public String getDescriptionOfOrder() {
        return descriptionOfOrder;
    }

    public void setDescriptionOfOrder(String descriptionOfOrder) {
        this.descriptionOfOrder = descriptionOfOrder;
    }
}
