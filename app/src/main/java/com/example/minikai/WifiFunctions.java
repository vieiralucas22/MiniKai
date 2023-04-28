package com.example.minikai;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiDatabase;
import com.example.minikai.room.WifiInfosMapper;
import com.proto.wifiProto.WifiInformationsRequest;
import com.proto.wifiProto.WifiInformationsResponse;
import com.proto.wifiProto.WifiServiceGrpc;

import java.util.List;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class WifiFunctions {
    public void sendWifiDataToServer(String isConnected, String SSID, String macAddress, String Frequency, String currentTime, Context context, String x){

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8080).usePlaintext().build();
        WifiServiceGrpc.WifiServiceBlockingStub wifiStub = WifiServiceGrpc.newBlockingStub(channel);
        WifiInformationsRequest wifiInformationsRequest = WifiInformationsRequest.newBuilder()
                .setStatus(isConnected).setSSID(SSID)
                .setMACAdress(macAddress).setFrequency(Frequency)
                .setCurrentTime(currentTime).build();
        WifiInformationsResponse response= wifiStub.wifiInformations(wifiInformationsRequest);

        sendDataToRoom(response.getSSID(),response.getFrequency(),response.getMACAdress(),response.getCurrentTime(),context);
    }
    public void sendDataToRoom(String SSID, String Frequency, String macAddress, String currentTime, Context context) {
        WifiDatabase db = Room.databaseBuilder(context,
                WifiDatabase.class,"wifi-database").allowMainThreadQueries().build();

        WifiInfoEntity entity = new WifiInfoEntity();

        WifiInfosMapper currentWifi = new WifiInfosMapper(entity);

        currentWifi.setUpWifiInfo("Conectado",SSID,Frequency,macAddress,currentTime);
        db.wifiDAO().insertAll(currentWifi.wifiEntity);

        List<WifiInfoEntity> wifiList = db.wifiDAO().getAllWifi();

        String wifis="";

        for (WifiInfoEntity wifiItem : wifiList){
            wifis+= wifiItem.wifiID+ " - " + wifiItem.status + " - " + wifiItem.SSID + " - " + wifiItem.wifiFrequency + " - " +  wifiItem.wifiMacAddress+ " - " + wifiItem.wifiCurrentTime;
        }

        Log.d("BancoWifis",wifis);
    }
}
