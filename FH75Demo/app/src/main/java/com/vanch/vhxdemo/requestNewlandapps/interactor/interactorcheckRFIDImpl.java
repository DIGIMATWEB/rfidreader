package com.vanch.vhxdemo.requestNewlandapps.interactor;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.vanch.vhxdemo.requestNewlandapps.model.requestRfidLoc;
import com.vanch.vhxdemo.requestNewlandapps.model.responseRfid;
import com.vanch.vhxdemo.requestNewlandapps.presenter.presenterNewlands;
import com.vanch.vhxdemo.requestNewlandapps.util.serviceRFID;
import com.vanch.vhxdemo.retrofit.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class interactorcheckRFIDImpl implements  interactorcheckRFID {
    private Context context;
    private serviceRFID service;
    private presenterNewlands presenter;
    //
    private Retrofit retrofitClient;
    public  interactorcheckRFIDImpl (presenterNewlands presenter ,Context context){
        this.presenter = presenter;
        this.context = context;
        retrofitClient = RetrofitClient.getRetrofitInstance();
        service = retrofitClient.create(serviceRFID.class);
    }
    @Override
    public void requestRfid(String code, Double locationLat, Double locationLong) {
        if(code!=null){
            requestMRFID(code,"0",String.valueOf(locationLat),String.valueOf(locationLong));
        }
    }

    private void requestMRFID(String code, String altitude, String locationLat, String locationLong) {
        requestRfidLoc request= new requestRfidLoc(code,altitude,locationLat,locationLong);
        Call<responseRfid> call =service.getRFID("ab2a0249426da773c2a6a781a63fbf86",request);
        Log.e("request","Code: "+code+" Lat: "+altitude+" Long: "+locationLat+" Long: "+locationLong);
        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        Log.e("request", jsonRequest);
        call.enqueue(new Callback<responseRfid>() {
            @Override
            public void onResponse(Call<responseRfid> call, Response<responseRfid> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Boolean responseResult = response.body().getResult();
                        if (responseResult != null && responseResult) {
                            presenter.setResponse(true, response.body().getData(),code);
                        } else {
                            presenter.setResponse(false, response.body().getData(),code);
                        }
                    } else {
                        Log.e("request", "Response body is null");
                    }
                } else {
                    Log.e("request", "Failed response: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<responseRfid> call, Throwable t) {
                Log.e("request", "onFailure: " + t.getMessage());
                if (t instanceof HttpException) {
                    ResponseBody errorBody = ((HttpException) t).response().errorBody();
                    if (errorBody != null) {
                        try {
                            String errorBodyString = errorBody.string();
                            Log.e("request", "Raw Error Body: " + errorBodyString);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
