package com.vanch.vhxdemo.requestNewlandapps.View;

import com.vanch.vhxdemo.requestNewlandapps.model.dataRfidResponse;

import java.util.List;

public interface viewRFIDImpl {
    void setResponse(Boolean responeResult, List<dataRfidResponse> data, String code);
}
