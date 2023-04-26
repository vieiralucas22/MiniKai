package com.example.minikai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class wifiReceiver extends BroadcastReceiver {

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