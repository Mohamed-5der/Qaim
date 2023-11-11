package com.qaim.qaim.Classes;

public class PreviewerRateParams {
    int  order_id , previewer_id ; float rate ;

    public PreviewerRateParams(int order_id, int previewer_id, float rate) {
        this.order_id = order_id;
        this.previewer_id = previewer_id;
        this.rate = rate;
    }
}
