package com.example.minikai.ui;

import  android.content.Context;
import android.net.wifi.WifiInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.minikai.Manifest;
import com.example.minikai.R;
import com.example.minikai.ServicesWifi;
import com.example.minikai.WifiFunctions;
import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiInfos.WifiInfos;
import com.example.minikai.room.WifiDatabase;
import com.example.minikai.room.WifiInfosMapper;
import com.proto.wifiProto.WifiInformationsRequest;
import com.proto.wifiProto.WifiInformationsResponse;
import com.proto.wifiProto.WifiServiceGrpc;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WifiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static  final int Permissions_resuquest_code=100;
    private static int WifiIntervalChange =  20*60*2000;
    private Handler handler = new Handler();
    private String lastWifi = "";
    String SSID,macAddress,Frequency;

    public WifiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiFragment newInstance(String param1, String param2) {
        WifiFragment fragment = new WifiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /////////////////Roda a função que envia o código para o servidor a cada 20 minutos
        Timer timer = new Timer();
        WifiFunctions wifiFunctions = new WifiFunctions();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(timestamp.getTime());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {


                        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        String isConnected = Boolean.toString(wifiManager.isWifiEnabled());
                        //Podemos pegar as informações do wifi que nosso device está conectado
                        WifiInfo wifi = wifiManager.getConnectionInfo();
                        SSID = wifi.getSSID();
                        macAddress = wifi.getBSSID();
                        Frequency = Integer.toString(wifi.getFrequency());

                        ///Validando os dados antes de manda-los para o servidor

                        if(!wifi.getSSID().equals("<unknown ssid>") && !wifi.getBSSID().equals("02:00:00:00:00:00") && !Integer.toString(wifi.getFrequency()).equals("-1") && wifiManager.isWifiEnabled()){

                            wifiFunctions.sendWifiDataToServer(isConnected,SSID,macAddress,Frequency,currentTime,getActivity().getApplicationContext(),"WifiFragment");
                            printWifiDataOnScreen(view,SSID,Frequency,macAddress,isConnected);

                        }else {
                            printWifiDataOnScreen(view,"","","",isConnected);
                        }
                    }
                });
            }
        },0,WifiIntervalChange);
    }


    public void printWifiDataOnScreen(View view, String SSID, String Frequency, String macAddress, String isConnected){
        TextView StatusTextview =(TextView) view.findViewById(R.id.wifiStatus);
        TextView SSIDTextview =(TextView) view.findViewById(R.id.SSID);
        TextView frequencyTextview =(TextView) view.findViewById(R.id.wifiFrequency);
        TextView macAdressTextview =(TextView) view.findViewById(R.id.wifiMACAdress);
        TextView disconnectedWifi =(TextView) view.findViewById(R.id.DisconnectedWifi);


        if (isConnected.equals("true")){
            StatusTextview.setText(" Conectado");
            SSIDTextview.setText(" "+SSID);
            frequencyTextview.setText(" "+Frequency);
            macAdressTextview.setText(" "+ macAddress);
        }else{
            disconnectedWifi.setText("Wifi desativado");
            StatusTextview.setText(" ");
            SSIDTextview.setText(" ");
            frequencyTextview.setText(" ");
            macAdressTextview.setText(" ");
        }

    }
}