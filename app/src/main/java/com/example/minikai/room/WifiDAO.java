package com.example.minikai.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.minikai.room.Entity.WifiInfoEntity;

import com.example.minikai.room.Entity.WifiInfoEntity;

import java.util.List;

@Dao
public interface WifiDAO {

    @Insert
    void insertAll (WifiInfoEntity... wifis);

    @Query("SELECT * FROM WifiInfoEntity")
    List<WifiInfoEntity> getAllWifi();
}