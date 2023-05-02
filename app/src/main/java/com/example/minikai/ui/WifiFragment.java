package com.example.minikai.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.minikai.Manifest;
import com.example.minikai.R;
import com.example.minikai.room.Entity.WifiInfoEntity;
import com.example.minikai.room.WifiDatabase;

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
    //private static  final int Permissions_resuquest_code=100;


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

        printWifiDataOnScreen(view);


    }

    public  void printWifiDataOnScreen(View view){


        TextView StatusTextview =(TextView) view.findViewById(R.id.wifiStatus);
        TextView SSIDTextview =(TextView) view.findViewById(R.id.SSID);
        TextView frequencyTextview =(TextView) view.findViewById(R.id.wifiFrequency);
        TextView macAdressTextview =(TextView) view.findViewById(R.id.wifiMACAdress);
        TextView disconnectedWifi =(TextView) view.findViewById(R.id.DisconnectedWifi);

        WifiDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                WifiDatabase.class,"wifi-database").allowMainThreadQueries().build();


                int currentWifiID = db.wifiDAO().getMaxId();
                Log.d("CUrrentWifiID",Integer.toString(currentWifiID));
                if (db.wifiDAO().currentStatus(currentWifiID).equals("Conectado")){
                    StatusTextview.setText(" Conectado");
                    SSIDTextview.setText(" "+db.wifiDAO().currentSSID(currentWifiID));
                    frequencyTextview.setText(" "+db.wifiDAO().currentFrequency(currentWifiID));
                    macAdressTextview.setText(" "+ db.wifiDAO().currentMacAdress(currentWifiID));
                }else{
                    disconnectedWifi.setText("Wifi desativado");
                    StatusTextview.setText(" ");
                    SSIDTextview.setText(" ");
                    frequencyTextview.setText(" ");
                    macAdressTextview.setText(" ");
                }

    }
}