package com.example.minikai;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.room.Room;

import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiDatabase;
import com.example.minikai.room.WifiInfos.WifiInfos;
import com.example.minikai.room.WifiInfosMapper;
import com.proto.wifiProto.WifiInformationsRequest;
import com.proto.wifiProto.WifiInformationsResponse;
import com.proto.wifiProto.WifiServiceGrpc;

import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class WifiFunctions {
    public void sendWifiDataToServer(WifiInfos wifiInfos, Context context){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8080)
                .usePlaintext().build();
        WifiServiceGrpc.WifiServiceBlockingStub wifiStub = WifiServiceGrpc.newBlockingStub(channel);
        WifiInformationsRequest wifiInformationsRequest = WifiInformationsRequest.newBuilder()
                .setStatus(wifiInfos.status).setSSID(wifiInfos.SSID)
                .setMACAdress(wifiInfos.wifiMacAddress).setFrequency(wifiInfos.wifiFrequency)
                .setCurrentTime(wifiInfos.wifiCurrentTime).build();
        WifiInformationsResponse response= wifiStub.wifiInformations(wifiInformationsRequest);

        sendDataToRoom(wifiInfos,context);
    }
    public void sendDataToRoom(WifiInfos wifiInfos, Context context) {
        WifiDatabase db = Room.databaseBuilder(context,
                WifiDatabase.class,"wifi-database").allowMainThreadQueries().build();

        db.wifiDAO().insertAll(WifiInfosMapper.convertWifiInfoToWifiEntity(WifiInfosMapper
                .setUpWifiInfo("Conectado",wifiInfos.SSID,wifiInfos.wifiFrequency
                        ,wifiInfos.wifiMacAddress,wifiInfos.wifiCurrentTime)));

        List<WifiInfoEntity> wifiList = db.wifiDAO().getAllWifi();

        String wifis="";

        for (WifiInfoEntity wifiItem : wifiList){
            wifis+= wifiItem.wifiID+ " - " + wifiItem.status + " - " + wifiItem.SSID + " - "
                    + wifiItem.wifiFrequency + " - " +  wifiItem.wifiMacAddress+ " - "
                    + wifiItem.wifiCurrentTime;
        }

        Log.d("BancoWifis",wifis);
    }
}
