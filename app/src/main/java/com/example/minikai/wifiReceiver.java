package com.example.minikai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.minikai.room.WifiDatabase;
import com.proto.wifiProto.WifiInformationsResponse;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class wifiReceiver extends BroadcastReceiver {
    ServicesWifi servicesWifi =new ServicesWifi();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String currentTime = new SimpleDateFormat("HH:mm:ss").format(timestamp.getTime());
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null) {
                if (networkInfo.isConnected()) {
                    // O dispositivo se conectou a uma nova rede Wi-Fi
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if (wifiInfo != null) {
                        String ssid = wifiInfo.getSSID();
//                        String macAddress = wifiInfo.getBSSID();
//                        String Frequency = Integer.toString(wifiInfo.getFrequency());
//                        Log.d("Receiver",ssid+" "+macAddress+" "+Frequency+" "+currentTime);
//                       servicesWifi.sendWifiDataToServer("Conectado",ssid,macAddress,Frequency,currentTime, context.getApplicationContext(),"onReceive");
                        Toast.makeText(context, "Conectado a rede Wi-Fi " + ssid , Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // O dispositivo desconectou da rede Wi-Fi
                    Toast.makeText(context, "Desconectado da rede Wi-Fi", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}