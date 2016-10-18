package com.jassdev.jackson.tututestapp.Utils.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Point implements Parcelable
{
    private String longitude;

    private String latitude;

    protected Point(Parcel in) {
        longitude = in.readString();
        latitude = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(longitude);
        dest.writeString(latitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [longitude = "+longitude+", latitude = "+latitude+"]";
    }
}