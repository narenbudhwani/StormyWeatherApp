package com.adstoreapp.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunita on 10/16/2015.
 */
public class Hour implements Parcelable {

    private double mTemperature;
    private String mSummary;
    private String mIcon;
    private long mTime;
    private String mTimezone;

    public int getTemperature() {
        return (int) Math.round( mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId() {
        return Forecast.getIconId(mIcon);
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public String getHour() {
        SimpleDateFormat formatter = new SimpleDateFormat("h a");
        Date date = new Date(mTime * 1000);
        return formatter.format(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSummary);
        parcel.writeString(mTimezone);
        parcel.writeString(mIcon);
        parcel.writeDouble(mTemperature);
        parcel.writeLong(mTime);
    }

    public Hour (Parcel in) {
        mSummary = in.readString();
        mTimezone = in.readString();
        mIcon = in.readString();
        mTemperature = in.readDouble();
        mTime = in.readLong();
    }

    public Hour() {}

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel parcel) {
            return new Hour(parcel);
        }

        @Override
        public Hour[] newArray(int i) {
            return new Hour[i];
        }
    };



}
