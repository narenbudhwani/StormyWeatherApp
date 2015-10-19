package com.adstoreapp.stormy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adstoreapp.stormy.R;
import com.adstoreapp.stormy.weather.Current;
import com.adstoreapp.stormy.weather.Day;
import com.adstoreapp.stormy.weather.Forecast;
import com.adstoreapp.stormy.weather.Hour;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.OnClick;


public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY FORECAST";
    private Forecast mForecast;
    private TextView mTemperatureLabel;
    private TextView mTimeLabel;
    private TextView mSummaryLabel;
    private TextView mHumidityValue;
    private TextView mPrecipValue;
    private ImageView mIconView;
    private ImageView mRefreshImageView;
    private Button mDailyButton;
    private Button mHourlyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTemperatureLabel = (TextView) findViewById(R.id.temperatureLabel);
        mTimeLabel = (TextView) findViewById(R.id.timeLabel);
        mSummaryLabel = (TextView) findViewById(R.id.summaryText);
        mHumidityValue = (TextView) findViewById(R.id.humidityValue);
        mPrecipValue = (TextView) findViewById(R.id.precipValue);
        mIconView = (ImageView) findViewById(R.id.iconImageView);
        mRefreshImageView = (ImageView) findViewById(R.id.refreshImageView);
        mDailyButton = (Button) findViewById(R.id.DailyButton);
        mHourlyButton = (Button) findViewById(R.id.Hourly);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshWeather();
            }
        });


        refreshWeather();
        Log.d(TAG, "Running MainActivity UI code");

            mDailyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, DailyActivity.class);
                    intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
                    startActivity(intent);
                }
            });

        mHourlyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HourlyForecastActivity.class);
                intent.putExtra(HOURLY_FORECAST, mForecast.getHourlyForecast());
                startActivity(intent);
            }
        });

    }
    private void refreshWeather() {



        String apiKey = "e22e9dff1871f50e4f3f26f740f13f04";
        double latitude = 37.8267;
        double longitude = -122.423;
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey + "/" + latitude + "," + longitude ;

        if(isNetwrokAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData =  response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getUpdatedWeather();
                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception Caught", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is not available", Toast.LENGTH_LONG).show();
        }
    }
    private void getUpdatedWeather() {
                Current current = mForecast.getCurrent();

                mTemperatureLabel.setText(current.getTemperature() + "");
                mTimeLabel.setText("At " + current.getFormattedTime() + "the temperature will be");
                mSummaryLabel.setText(current.getSummary() + "");
                mHumidityValue.setText(current.getHumidity() + "");
                mPrecipValue.setText(current.getPercipChance() + "%");

                Drawable drawable = getResources().getDrawable(current.getIconId());
                mIconView.setImageDrawable(drawable);

    }
    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));

        return forecast;
    }
    private Hour[] getHourlyForecast(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for(int i = 0; i<data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTimezone(timezone);

            hours[i] = hour;
        }

        return  hours;
    }
    private Day[] getDailyForecast(String jsonData) throws  JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i = 0; i<data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setTimezone(timezone);
            day.setTime(jsonDay.getLong("time"));
            day.setIcon(jsonDay.getString("icon"));
            day.setSummary(jsonDay.getString("summary"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));

            days[i] = day;

        }
            return days;

    }
    private Current getCurrentDetails(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG, "FROM JSON:" + timezone);
        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPercipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        Log.d(TAG, current.getFormattedTime());

        return current;
    }
    private boolean isNetwrokAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if(networkInfo != null && networkInfo.isConnected() ) {
            isAvailable = true;
        }

        return  isAvailable;
    }
    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "get error Message");
    }
}
