package com.example.aristarkhov_vm.tutu_test.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by noyr on 15-Oct-16.
 */

public class CitiesFrom {
    @SerializedName("citiesFrom")
    private Country[] mCountry;

    public Country[] getmCountry() {
        return mCountry;
    }

    public void setmCountry(Country[] mCountry) {
        this.mCountry = mCountry;
    }
}
