package com.vanch.vhxdemo.requestNewlandapps.presenter;

import com.vanch.vhxdemo.requestNewlandapps.model.dataRfidResponse;

import java.util.List;

public interface presenterNewlands {
    void reqRfid(String code, Double locationLat, Double locationLong);
    void setResponse(Boolean responeResult, List<dataRfidResponse> data, String code);

}
