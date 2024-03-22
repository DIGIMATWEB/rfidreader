package com.vanch.vhxdemo.requestNewlandapps.presenter;

import android.content.Context;

import com.vanch.vhxdemo.requestNewlandapps.View.viewRFIDImpl;
import com.vanch.vhxdemo.requestNewlandapps.interactor.interactorcheckRFID;
import com.vanch.vhxdemo.requestNewlandapps.interactor.interactorcheckRFIDImpl;
import com.vanch.vhxdemo.requestNewlandapps.model.dataRfidResponse;

import java.util.List;

public class presenterNewlandsImpl implements presenterNewlands{
    private  Context context;
    private viewRFIDImpl view;
    private interactorcheckRFID interactor;
    public  presenterNewlandsImpl(viewRFIDImpl view , Context context){
        this.context=context;
        this.view=view;
        interactor= new interactorcheckRFIDImpl(this,context);
    }
    @Override
    public void reqRfid(String code, Double locationLat, Double locationLong) {
        if(view!=null){
            interactor.requestRfid(code,locationLat,locationLong);
        }
    }

    @Override
    public void setResponse(Boolean responeResult, List<dataRfidResponse> data, String code) {
        if(view!=null){
            view.setResponse(responeResult,data,code);
        }
    }
}
