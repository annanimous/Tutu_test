package com.example.aristarkhov_vm.tutu_test.application;

import android.app.Application;

import com.example.aristarkhov_vm.tutu_test.model.CitiesFrom;
import com.example.aristarkhov_vm.tutu_test.model.CitiesTo;
import com.example.aristarkhov_vm.tutu_test.model.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noyr on 15-Oct-16.
 */

public class TutuApplication extends Application {

    /*public CitiesFrom citiesFrom;
    public CitiesTo citiesTo;*/
    List<Station> stationFromFullList = new ArrayList<>();
    List<Station> stationToFullList = new ArrayList<>();
    private static TutuApplication instance;

    public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    public static TutuApplication getAppInstance() {
        return instance;
    }

    public List<Station> getStationFromFullList() {
        return stationFromFullList;
    }
    public void setStationFromFullList(List<Station> stationFromFullList) {
        this.stationFromFullList = stationFromFullList;
    }
    public List<Station> getStationToFullList() {
        return stationToFullList;
    }
    public void setStationToFullList(List<Station> stationToFullList) {
        this.stationToFullList = stationToFullList;
    }
}