package com.example.minikai;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiDatabase;
import com.example.minikai.room.WifiInfosMapper;
import com.proto.wifiProto.WifiInformationsRequest;
import com.proto.wifiProto.WifiInformationsResponse;
import com.proto.wifiProto.WifiServiceGrpc;
import java.security.Provider;
import java.util.List;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ServicesWifi extends Service  {

    private static final String TAG = "MyService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        


        return START_STICKY;
    }
}
