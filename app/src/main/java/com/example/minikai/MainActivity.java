package com.example.minikai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] permissions ={"android.permission.ACCESS_FINE_LOCATION"};
    private wifiReceiver wifiReceiver;
    public IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Permissios pop-up que aparece na tela
        requestPermissions(permissions,80);
        //Para realizar uma ação na troca de wifi
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        wifiReceiver = new wifiReceiver();

        registerReceiver(wifiReceiver,intentFilter);
////Para rodar a notificação e o background service
        Intent serviceIntent = new Intent(this, ServicesWifi.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
            foregroundServiceRunning();
        }

    }

////Essa função serve para Fazer
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
///Serve para rodarmos a aplicação em background
    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(ServicesWifi.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }

        return false;
    }
}
