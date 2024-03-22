package com.vanch.vhxdemo.requestNewlandapps.model;

import com.google.gson.annotations.SerializedName;

public class dataRfidResponse {
    @SerializedName("vehicle_name")
    private String vehicleName = "";
    @SerializedName("company_name")
    private String companyName = "";
    @SerializedName("message_error")
    private String messageError = "";
    public dataRfidResponse(String vehicleName, String companyName,String messageError) {
        super();
        this.vehicleName = vehicleName;
        this.companyName = companyName;
        this.messageError = messageError;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
}
