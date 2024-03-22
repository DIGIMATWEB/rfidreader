package com.vanch.vhxdemo.requestNewlandapps.model;

import com.google.gson.annotations.SerializedName;

public class requestRfidLoc {
    @SerializedName("tag")
    private String tag;
    @SerializedName("altitude")
    private String altitude;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public requestRfidLoc(String tag, String altitude, String latitude, String longitude) {
        super();
        this.tag = tag;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
