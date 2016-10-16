
package com.example.aristarkhov_vm.tutu_test.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Country {

    @SerializedName("cityId")
    private Long mCityId;
    @SerializedName("cityTitle")
    private String mCityTitle;
    @SerializedName("countryTitle")
    private String mCountryTitle;
    @SerializedName("districtTitle")
    private String mDistrictTitle;
    @SerializedName("point")
    private Point mPoint;
    @SerializedName("regionTitle")
    private String mRegionTitle;
    @SerializedName("stations")
    private List<Station> mStations;

    public Long getCityId() {
        return mCityId;
    }

    public void setCityId(Long cityId) {
        mCityId = cityId;
    }

    public String getCityTitle() {
        return mCityTitle;
    }

    public void setCityTitle(String cityTitle) {
        mCityTitle = cityTitle;
    }

    public String getCountryTitle() {
        return mCountryTitle;
    }

    public void setCountryTitle(String countryTitle) {
        mCountryTitle = countryTitle;
    }

    public String getDistrictTitle() {
        return mDistrictTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        mDistrictTitle = districtTitle;
    }

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(Point point) {
        mPoint = point;
    }

    public String getRegionTitle() {
        return mRegionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        mRegionTitle = regionTitle;
    }

    public List<Station> getStations() {
        return mStations;
    }

    public void setStations(List<Station> stations) {
        mStations = stations;
    }

}
