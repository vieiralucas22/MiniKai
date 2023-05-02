package com.example.minikai.room.WifiInfos;

import javax.inject.Inject;

public class WifiInfos {

public int wifiID;
public String status;
public String wifiMacAddress;

public String SSID;
public String wifiFrequency;
public String wifiCurrentTime;

@Inject
    public WifiInfos (String status,String SSID, String Frequency, String macAddress,String wifiCurrentTime) {
        this.status =status;
        this.SSID =SSID;
        this.wifiFrequency =Frequency;
        this.wifiMacAddress = macAddress;
        this.wifiCurrentTime = wifiCurrentTime;
    }

    public void setWifiID(int wifiID) {
        this.wifiID = wifiID;
    }

    public void setWifiMacAddress(String wifiMacAddress) {
        this.wifiMacAddress = wifiMacAddress;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public void setWifiFrequency(String wifiFrequency) {
        this.wifiFrequency = wifiFrequency;
    }

    public void setWifiCurrentTime(String wifiCurrentTime) {
        this.wifiCurrentTime = wifiCurrentTime;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getWifiMacAddress() {
        return wifiMacAddress;
    }
    public String getSSID() {
        return SSID;
    }
    public String getWifiFrequency() {
        return wifiFrequency;
    }
    public String getWifiCurrentTime() {
        return wifiCurrentTime;
    }

    @Override
    public String toString() {
        return "WifiInfos{" +
                "wifiID=" + wifiID +
                ", status='" + status + '\'' +
                ", wifiMacAddress='" + wifiMacAddress + '\'' +
                ", SSID='" + SSID + '\'' +
                ", wifiFrequency='" + wifiFrequency + '\'' +
                ", wifiCurrentTime='" + wifiCurrentTime + '\'' +
                '}';
    }
}
