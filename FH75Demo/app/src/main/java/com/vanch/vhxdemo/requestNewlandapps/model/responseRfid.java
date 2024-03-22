package com.vanch.vhxdemo.requestNewlandapps.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class responseRfid {
    @SerializedName("result")
    private Boolean result;
    @SerializedName("data")
    private List<dataRfidResponse> data;

    public responseRfid(Boolean result, List<dataRfidResponse> data) {
        super();
        this.result = result;
        this.data = data;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public List<dataRfidResponse> getData() {
        return data;
    }

    public void setData(List<dataRfidResponse> data) {
        this.data = data;
    }
}
