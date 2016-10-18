package com.jassdev.jackson.tututestapp.Utils.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TutuModel implements Parcelable
{
    private ArrayList<CitiesFromTo> citiesFrom;

    private ArrayList<CitiesFromTo> citiesTo;

    protected TutuModel(Parcel in) {
        citiesFrom = in.createTypedArrayList(CitiesFromTo.CREATOR);
        citiesTo = in.createTypedArrayList(CitiesFromTo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(citiesFrom);
        dest.writeTypedList(citiesTo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TutuModel> CREATOR = new Creator<TutuModel>() {
        @Override
        public TutuModel createFromParcel(Parcel in) {
            return new TutuModel(in);
        }

        @Override
        public TutuModel[] newArray(int size) {
            return new TutuModel[size];
        }
    };

    public ArrayList<CitiesFromTo> getCitiesFrom() {
        return citiesFrom;
    }

    public ArrayList<CitiesFromTo> getCitiesTo() {
        return citiesTo;
    }
}
