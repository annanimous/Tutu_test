
package com.example.aristarkhov_vm.tutu_test.model;

import com.google.gson.annotations.SerializedName;

public class Station {

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
    @SerializedName("stationId")
    private Long mStationId;
    @SerializedName("stationTitle")
    private String mStationTitle;

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

    public Long getStationId() {
        return mStationId;
    }

    public void setStationId(Long stationId) {
        mStationId = stationId;
    }

    public String getStationTitle() {
        return mStationTitle;
    }

    public void setStationTitle(String stationTitle) {
        mStationTitle = stationTitle;
    }

}
