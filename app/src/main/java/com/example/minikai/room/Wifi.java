package com.example.minikai.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wifi {

@PrimaryKey(autoGenerate = true)
public int wifiID;

@ColumnInfo(name = "status")
public String status;
@ColumnInfo(name = "wifiMacAdress")
public String wifiMacAdress;

@ColumnInfo(name = "SSID")
public String SSID;

@ColumnInfo(name = "wifiFrequency")
public String wifiFrequency;

    public Wifi(String status,String wifiMacAdress, String SSID, String wifiFrequency) {
        this.status = status;
        this.wifiMacAdress = wifiMacAdress;
        this.SSID = SSID;
        this.wifiFrequency =wifiFrequency;
    }

}
