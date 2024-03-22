package com.vanch.vhxdemo.requestNewlandapps.util;

import com.vanch.vhxdemo.requestNewlandapps.model.requestRfidLoc;
import com.vanch.vhxdemo.requestNewlandapps.model.responseRfid;
import com.vanch.vhxdemo.retrofit.RetrofitEndPoints;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface serviceRFID {
    @POST(RetrofitEndPoints.GET_RFID)
    Call<responseRfid> getRFID(@Header("Authorization") String token, @Body requestRfidLoc request);
}
