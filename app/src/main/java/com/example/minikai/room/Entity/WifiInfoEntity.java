package com.example.minikai.room.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WifiInfoEntity {

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
    @ColumnInfo(name = "wifiCurrentTime")
    public String wifiCurrentTime;

}
