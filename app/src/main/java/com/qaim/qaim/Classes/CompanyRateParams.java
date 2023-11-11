package com.qaim.qaim.Classes;

public class CompanyRateParams {
    int order_id , company_id ; float  rate   ;

    public CompanyRateParams(int order_id, int company_id, float rate) {
        this.order_id = order_id;
        this.company_id = company_id;
        this.rate = rate;
    }
}
