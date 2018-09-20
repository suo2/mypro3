package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;

/**
 * Created by P00708 on 2018/7/10.
 */

public class WeatherDisplayView extends LinearLayout{

    private TextView cityName;
    private TextView todayWeather;
    private TextView todayWind;
    private TextView todayTemperature;
    private TextView todayTemperatureHighLow;
    private TextView todayDate;
    private TextView tomorrowWeather;
    private TextView tomorrowTemperatureHighLow;
    private TextView theDayAfterTomorrowWeather;
    private TextView theDayAfterTomorrowHighLow;
    public WeatherDisplayView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.weather_display_view_layout,this);
        initView();
    }

    public WeatherDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.weather_display_view_layout,this);
        initView();
    }
    private void initView(){

        cityName = (TextView) findViewById(R.id.city_name);
        todayWeather = (TextView) findViewById(R.id.today_weather);
        todayWind = (TextView) findViewById(R.id.today_wind);
        todayTemperature = (TextView) findViewById(R.id.today_temperature);
        todayTemperatureHighLow = (TextView) findViewById(R.id.today_temperature_high_low);
        todayDate = (TextView) findViewById(R.id.today_date);
        tomorrowWeather = (TextView) findViewById(R.id.tomorrow_weather);
        tomorrowTemperatureHighLow = (TextView) findViewById(R.id.tomorrow_temperature_high_low);
        theDayAfterTomorrowWeather = (TextView) findViewById(R.id.the_day_after_tomorrow_weather);
        theDayAfterTomorrowHighLow = (TextView) findViewById(R.id.the_day_after_tomorrow_temperature_high_low);
    }

    public void setCityName(String cityName) {
        if(cityName != null){
            this.cityName.setText(cityName);
        }

    }

    public void setTodayWeather(String todayWeather) {
        if(todayWeather != null){
            this.todayWeather.setText(todayWeather);
        }
    }

    public void setTodayWind(String todayWind) {
        if(todayWind != null){
            this.todayWind.setText(todayWind);
        }
    }

    public void setTodayTemperature(String todayTemperature) {
        if(todayTemperature != null){
            this.todayTemperature.setText(todayTemperature);
        }
    }

    public void setTodayTemperatureHighLow(String todayTemperatureHighLow) {
        if(todayTemperatureHighLow != null){
            this.todayTemperatureHighLow.setText(todayTemperatureHighLow);
        }

    }

    public void setTodayDate(String todayDate) {
        if(todayDate != null){
            this.todayDate.setText(todayDate);
        }
    }

    public void setTomorrowWeather(String tomorrowWeather) {
        if(tomorrowWeather != null){
            this.tomorrowWeather.setText(tomorrowWeather);
        }
    }

    public void setTomorrowTemperatureHighLow(String tomorrowTemperatureHighLow) {
        if(tomorrowTemperatureHighLow != null){
            this.tomorrowTemperatureHighLow.setText(tomorrowTemperatureHighLow);
        }
    }

    public void setTheDayAfterTomorrowWeather(String theDayAfterTomorrowWeather) {
        if(theDayAfterTomorrowWeather != null){
            this.theDayAfterTomorrowWeather.setText(theDayAfterTomorrowWeather);
        }

    }

    public void setTheDayAfterTomorrowHighLow(String theDayAfterTomorrowHighLow) {
        if(theDayAfterTomorrowHighLow != null){
            this.theDayAfterTomorrowHighLow.setText(theDayAfterTomorrowHighLow);
        }
    }
}
