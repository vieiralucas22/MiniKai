package com.example.minikai.room.WifiInfos;

public class WifiInfos {

public int wifiID;
public String status;
public String wifiMacAddress;

public String SSID;
public String wifiFrequency;
public String wifiCurrentTime;

    public WifiInfos() {}


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWifiMacAddress() {
        return wifiMacAddress;
    }

    public void setWifiMacAddress(String wifiMacAddress) {
        this.wifiMacAddress = wifiMacAddress;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getWifiFrequency() {
        return wifiFrequency;
    }

    public void setWifiFrequency(String wifiFrequency) {
        this.wifiFrequency = wifiFrequency;
    }

    public String getWifiCurrentTime() {
        return wifiCurrentTime;
    }

    public void setWifiCurrentTime(String wifiCurrentTime) {
        this.wifiCurrentTime = wifiCurrentTime;
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
