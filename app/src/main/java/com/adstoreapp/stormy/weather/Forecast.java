package com.adstoreapp.stormy.weather;

import com.adstoreapp.stormy.R;

/**
 * Created by sunita on 10/16/2015.
 */
public class Forecast {

    private Current mCurrent;
    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public static int getIconId(String iconStirng) {

        int iconId = R.drawable.clear_day;

        if(iconStirng.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (iconStirng.equals("clear-night")){
            iconId = R.drawable.clear_night;
        }
        else if(iconStirng.equals("rain")) {
            iconId = R.drawable.rain;

        }
        else if (iconStirng.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (iconStirng.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (iconStirng.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (iconStirng.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (iconStirng.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (iconStirng.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (iconStirng.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return  iconId;
    }
}
