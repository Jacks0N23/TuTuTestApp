package com.jassdev.jackson.tututestapp.Utils.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Stations implements Parcelable {
    private Point point;

    private String stationTitle;

    private String regionTitle;

    private String cityId;

    private String stationId;

    private String countryTitle;

    private String districtTitle;

    private String cityTitle;

    protected Stations(Parcel in) {
        point = in.readParcelable(Point.class.getClassLoader());
        stationTitle = in.readString();
        regionTitle = in.readString();
        cityId = in.readString();
        stationId = in.readString();
        countryTitle = in.readString();
        districtTitle = in.readString();
        cityTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(point, flags);
        dest.writeString(stationTitle);
        dest.writeString(regionTitle);
        dest.writeString(cityId);
        dest.writeString(stationId);
        dest.writeString(countryTitle);
        dest.writeString(districtTitle);
        dest.writeString(cityTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Stations> CREATOR = new Creator<Stations>() {
        @Override
        public Stations createFromParcel(Parcel in) {
            return new Stations(in);
        }

        @Override
        public Stations[] newArray(int size) {
            return new Stations[size];
        }
    };

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getStationTitle() {
        return stationTitle;
    }

    public void setStationTitle(String stationTitle) {
        this.stationTitle = stationTitle;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public void setRegionTitle(String regionTitle) {
        this.regionTitle = regionTitle;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getCountryTitle() {
        return countryTitle;
    }

    public void setCountryTitle(String countryTitle) {
        this.countryTitle = countryTitle;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public void setDistrictTitle(String districtTitle) {
        this.districtTitle = districtTitle;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public void setCityTitle(String cityTitle) {
        this.cityTitle = cityTitle;
    }
}

