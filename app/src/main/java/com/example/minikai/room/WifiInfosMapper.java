package com.example.minikai.room;

import androidx.annotation.NonNull;

import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiInfos.WifiInfos;

public class WifiInfosMapper {


    public static WifiInfoEntity  convertWifiInfoToWifiEntity(WifiInfos wifiInfo){

       WifiInfoEntity wifiInf = new WifiInfoEntity();
       if(wifiInfo==null)   return wifiInf;

        wifiInf.status = wifiInfo.getStatus();
        wifiInf.wifiMacAddress = wifiInfo.getWifiMacAddress();
        wifiInf.SSID = wifiInfo.getSSID();
        wifiInf.wifiFrequency = wifiInfo.getWifiFrequency();
        wifiInf.wifiCurrentTime = wifiInfo.getWifiCurrentTime();

        return wifiInf;
    }
    public static   WifiInfos setUpWifiInfo(String status,String SSID, String Frequency, String macAddress,String currentTime){
        return new WifiInfos(status,SSID,Frequency,macAddress,currentTime);
    }

}
