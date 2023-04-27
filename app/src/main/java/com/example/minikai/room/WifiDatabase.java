package com.example.minikai.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.minikai.room.Entity.WifiInfoEntity;

@Database(entities = {WifiInfoEntity.class},version = 1)
 public abstract class WifiDatabase extends RoomDatabase {

    public abstract WifiDAO wifiDAO();
}
