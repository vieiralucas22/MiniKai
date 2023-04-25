package com.example.minikai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] permissions ={"android.permission.ACCESS_FINE_LOCATION"};
    private wifiReceiver wifiReceiver;
//    Intent intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(permissions,80);

//
//        wifiReceiver = new wifiReceiver();
//
//        registerReceiver();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==80){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this,"Permissão aceita",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this,"Permissão negada",Toast.LENGTH_SHORT).show();

            }
        }
    }
}