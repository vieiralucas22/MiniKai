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
@ColumnInfo(name = "wifiMacAddress")
public String wifiMacAddress;

@ColumnInfo(name = "SSID")
public String SSID;

@ColumnInfo(name = "wifiFrequency")
public String wifiFrequency;

    public Wifi(String status, String wifiMacAddress, String SSID, String wifiFrequency) {
        this.status = status;
        this.wifiMacAddress = wifiMacAddress;
        this.SSID = SSID;
        this.wifiFrequency = wifiFrequency;
    }

}
