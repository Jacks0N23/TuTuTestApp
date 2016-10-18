package com.jassdev.jackson.tututestapp.Utils.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CitiesFromTo implements Parcelable
{
    private Point point;

    private String regionTitle;

    private ArrayList<Stations> stations;

    private String cityId;

    private String countryTitle;

    private String districtTitle;

    private String cityTitle;

    protected CitiesFromTo(Parcel in) {
        point = in.readParcelable(Point.class.getClassLoader());
        regionTitle = in.readString();
        stations = in.createTypedArrayList(Stations.CREATOR);
        cityId = in.readString();
        countryTitle = in.readString();
        districtTitle = in.readString();
        cityTitle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(point, flags);
        dest.writeString(regionTitle);
        dest.writeTypedList(stations);
        dest.writeString(cityId);
        dest.writeString(countryTitle);
        dest.writeString(districtTitle);
        dest.writeString(cityTitle);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CitiesFromTo> CREATOR = new Creator<CitiesFromTo>() {
        @Override
        public CitiesFromTo createFromParcel(Parcel in) {
            return new CitiesFromTo(in);
        }

        @Override
        public CitiesFromTo[] newArray(int size) {
            return new CitiesFromTo[size];
        }
    };

    public Point getPoint ()
    {
        return point;
    }

    public void setPoint (Point point)
    {
        this.point = point;
    }

    public String getRegionTitle ()
    {
        return regionTitle;
    }

    public void setRegionTitle (String regionTitle)
    {
        this.regionTitle = regionTitle;
    }

    public ArrayList<Stations> getStations ()
    {
        return stations;
    }


    public String getCityId ()
    {
        return cityId;
    }

    public void setCityId (String cityId)
    {
        this.cityId = cityId;
    }

    public String getCountryTitle ()
    {
        return countryTitle;
    }

    public void setCountryTitle (String countryTitle)
    {
        this.countryTitle = countryTitle;
    }

    public String getDistrictTitle ()
    {
        return districtTitle;
    }

    public void setDistrictTitle (String districtTitle)
    {
        this.districtTitle = districtTitle;
    }

    public String getCityTitle ()
    {
        return cityTitle;
    }

    public void setCityTitle (String cityTitle)
    {
        this.cityTitle = cityTitle;
    }
}