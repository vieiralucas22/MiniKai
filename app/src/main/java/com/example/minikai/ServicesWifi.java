package com.example.minikai;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiDatabase;
import com.example.minikai.room.WifiInfos.WifiInfos;
import com.example.minikai.room.WifiInfosMapper;
import com.proto.wifiProto.WifiInformationsRequest;
import com.proto.wifiProto.WifiInformationsResponse;
import com.proto.wifiProto.WifiServiceGrpc;
import java.security.Provider;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ServicesWifi extends Service  {

    final String CHANNEL_ID = "Foreground Service";
    private static int WifiIntervalChange =  10*1000;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
///O que acontece quando o serviço está rodando em background

        Timer timer = new Timer();
        WifiFunctions wifiFunctions = new WifiFunctions();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        String currentTime = new SimpleDateFormat("HH:mm:ss").format(timestamp.getTime());

                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        String isConnected = Boolean.toString(wifiManager.isWifiEnabled());
                        //Podemos pegar as informações do wifi que nosso device está conectado
                        WifiInfo wifi = wifiManager.getConnectionInfo();
                        WifiInfos wifiInfos = new WifiInfos(isConnected,wifi.getSSID()
                                ,Integer.toString(wifi.getFrequency()),wifi.getBSSID(),currentTime);

                        ///Validando os dados antes de manda-los para o servidor

                        if(!wifi.getSSID().equals("<unknown ssid>") && !wifi.getBSSID()
                                .equals("02:00:00:00:00:00") && !Integer.toString(wifi.getFrequency())
                                .equals("-1") && wifiManager.isWifiEnabled()){

                            wifiFunctions.sendWifiDataToServer(wifiInfos,getApplicationContext());

                        }else {
                            Toast.makeText(getApplicationContext(), "Dados inválidos para enviar ao servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        },0,WifiIntervalChange);

        //Cria a notificação que aparece quando o aplicativo está rodando em background
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this,CHANNEL_ID)
                    .setContentText("Foreground Service Running").setContentTitle("This is Titulo");
            startForeground(1001,notification.build());

        }
        return START_STICKY;
    }
}
