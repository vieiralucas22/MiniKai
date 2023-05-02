package com.example.minikai.Dagger;

import com.example.minikai.room.WifiInfos.WifiInfos;

import dagger.Module;
import dagger.Provides;

@Module
public class ModuleWifi {
    @Provides
    public WifiInfos provideWifiInfo(){return new WifiInfos();}
}
