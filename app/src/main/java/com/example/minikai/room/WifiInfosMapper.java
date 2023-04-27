package com.example.minikai.room;

import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiInfos.WifiInfos;

public class WifiInfosMapper {

    public WifiInfoEntity wifiEntity;

   public WifiInfosMapper(WifiInfoEntity wifiInfoEntity){
       this.wifiEntity = wifiInfoEntity;
   }

    public  void convertWifiInfoToWifiEntity(WifiInfos wifiInfo){
       wifiEntity.status = wifiInfo.getStatus();
       wifiEntity.wifiMacAddress = wifiInfo.getWifiMacAddress();
       wifiEntity.SSID = wifiInfo.getSSID();
       wifiEntity.wifiFrequency = wifiInfo.getWifiFrequency();
       wifiEntity.wifiCurrentTime = wifiInfo.getWifiCurrentTime();

    //   return wifiEntity;
    }
    public  void setUpWifiInfo(String status,String SSID, String Frequency, String macAddress,String currentTime){
        WifiInfos wifiInfo= new WifiInfos();
        wifiInfo.setStatus(status);
        wifiInfo.setSSID(SSID);
        wifiInfo.setWifiFrequency(Frequency);
        wifiInfo.setWifiMacAddress(macAddress);
        wifiInfo.setWifiCurrentTime(currentTime);
        convertWifiInfoToWifiEntity(wifiInfo);
    }

}
