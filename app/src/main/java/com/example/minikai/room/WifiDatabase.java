package com.example.minikai.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Wifi.class},version = 1)
 public abstract class WifiDatabase extends RoomDatabase {

    public abstract WifiDAO wifiDAO();
}
