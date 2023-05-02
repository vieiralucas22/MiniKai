package com.example.minikai.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.minikai.room.Entity.WifiInfoEntity;

import com.example.minikai.room.Entity.WifiInfoEntity;

import java.util.List;

@Dao
public interface WifiDAO {

    @Insert
    void insertAll (WifiInfoEntity... wifis);

    @Query("SELECT MAX(wifiID) FROM WifiInfoEntity")
    int getMaxId();

    @Query("SELECT SSID FROM WifiInfoEntity WHERE wifiID=:currentID")
    String currentSSID(int currentID);
    @Query("SELECT wifiMacAddress FROM WifiInfoEntity WHERE wifiID=:currentID")
    String currentMacAdress(int currentID);
    @Query("SELECT wifiFrequency FROM WifiInfoEntity WHERE wifiID=:currentID")
    String currentFrequency(int currentID);
    @Query("SELECT status FROM WifiInfoEntity WHERE wifiID=:currentID")
    String currentStatus(int currentID);



    @Query("SELECT * FROM WifiInfoEntity")
    List<WifiInfoEntity> getAllWifi();
}