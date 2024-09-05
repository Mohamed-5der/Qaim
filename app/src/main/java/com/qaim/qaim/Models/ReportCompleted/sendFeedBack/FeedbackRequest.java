package com.qaim.qaim.Models.ReportCompleted.sendFeedBack;

public class FeedbackRequest {
    String content;
    String subject;
    String companyId;

    public FeedbackRequest(String content, String subject, String companyId) {
        this.content = content;
        this.subject = subject;
        this.companyId = companyId;
    }
}
