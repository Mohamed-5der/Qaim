package com.qaim.qaim.Classes;

public class PaymentParams {
    int order_id ;
    String transaction_id ;

    public PaymentParams(int order_id, String transaction_id) {
        this.order_id = order_id;
        this.transaction_id = transaction_id;
    }
}
