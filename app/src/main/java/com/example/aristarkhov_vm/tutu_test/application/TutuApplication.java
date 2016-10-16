package com.example.aristarkhov_vm.tutu_test.application;

import android.app.Application;

import com.example.aristarkhov_vm.tutu_test.model.CitiesFrom;
import com.example.aristarkhov_vm.tutu_test.model.CitiesTo;

/**
 * Created by noyr on 15-Oct-16.
 */

public class TutuApplication extends Application {

    public CitiesFrom citiesFrom;
    public CitiesTo citiesTo;

    public CitiesFrom getCitiesFrom() {
        return citiesFrom;
    }

    public void setCitiesFrom(CitiesFrom citiesFrom) {
        this.citiesFrom = citiesFrom;
    }

    public CitiesTo getCitiesTo() {
        return citiesTo;
    }

    public void setCitiesTo(CitiesTo citiesTo) {
        this.citiesTo = citiesTo;
    }
}