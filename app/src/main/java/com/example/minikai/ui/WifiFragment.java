package com.example.minikai.ui;

import  android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.util.StringUtil;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//import com.example.minikai.Manifest;
import com.example.minikai.R;
import com.example.minikai.room.Wifi;
import com.example.minikai.room.WifiDatabase;
import com.proto.wifiProto.WifiInformationsRequest;
import com.proto.wifiProto.WifiInformationsResponse;
import com.proto.wifiProto.WifiServiceGrpc;
import com.example.minikai.wifiReceiver;


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

                            Log.d("batata",isConnected+SSID+macAddress+" "+Frequency);

                            sendWifiDataToServer(view,isConnected,SSID,macAddress,Frequency);
                            printWifiDataOnScreen(view,SSID,Frequency,macAddress,isConnected);
                        }else {
                            printWifiDataOnScreen(view,"","","",isConnected);
                        }
                    }
                });
            }
        },0,WifiIntervalChange);
    }
    public void sendWifiDataToServer(View view,String isConnected,String SSID,String macAddress,String Frequency){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost",8080).usePlaintext().build();
        WifiServiceGrpc.WifiServiceBlockingStub wifiStub = WifiServiceGrpc.newBlockingStub(channel);
        WifiInformationsRequest wifiInformationsRequest = WifiInformationsRequest.newBuilder()
                .setStatus(isConnected).setSSID(SSID)
                .setMACAdress(macAddress).setFrequency(Frequency)
                .build();
        WifiInformationsResponse response= wifiStub.wifiInformations(wifiInformationsRequest);

        if(response.getMessage().equals("Dados recebidos com sucesso")){
            Log.d("sendWifiServer",SSID+macAddress+" "+Frequency);

            sendDataToRoom(SSID,Frequency,macAddress);

        }else{
            Log.d("BancoWifis","wifis");///////////////////////
        }
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
public void sendDataToRoom(String SSID, String Frequency, String macAddress){
    WifiDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
            WifiDatabase.class,"wifi-database").allowMainThreadQueries().build();

    Wifi currentWifi = new Wifi("Conectado",macAddress,SSID,Frequency);
    Log.d("sendWifiRoom",SSID+macAddress+" "+Frequency);
    db.wifiDAO().insertAll(currentWifi);
    Log.d("batata","pos");
    List<Wifi> wifiList = db.wifiDAO().getAllWifi();
    String wifis="";

    for (Wifi wifiItem : wifiList){
        wifis+= wifiItem.status + " - " + wifiItem.SSID + " - " + wifiItem.wifiFrequency + " - " +  wifiItem.wifiMacAddress;
    }

    Log.d("BancoWifis",wifis);
}

}