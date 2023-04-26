package com.example.minikai.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WifiDAO {

    @Insert
    void insertAll (Wifi... wifis);

    @Query("SELECT * FROM Wifi")
    List<Wifi> getAllWifi();
}