package com.qaim.qaim.Classes;

public class BusinessInProgressDetails {
    String realStateName , realStateDescription , companyName ;

    public BusinessInProgressDetails(String realStateName, String realStateDescription, String companyName) {
        this.realStateName = realStateName;
        this.realStateDescription = realStateDescription;
        this.companyName = companyName;
    }

    public String getRealStateName() {
        return realStateName;
    }

    public void setRealStateName(String realStateName) {
        this.realStateName = realStateName;
    }

    public String getRealStateDescription() {
        return realStateDescription;
    }

    public void setRealStateDescription(String realStateDescription) {
        this.realStateDescription = realStateDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
