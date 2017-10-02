package com.example.rws.weatherapp.Models;

import android.support.annotation.NonNull;

import com.example.rws.weatherapp.Models.Forecast.PartialDayForecast;
import com.example.rws.weatherapp.UI.DisplayItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Marx on 10/1/2017.
 */

public class ForecastDay implements Comparable<ForecastDay>, DisplayItem{

    protected long day;
    public final ArrayList<PartialDayForecast> parts = new ArrayList<>();

    protected Date date;

    public ForecastDay(long day) {
        this.day = day;
    }

    public Date getDate(){
        if(date == null){
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(day);
            date = calendar.getTime();
        }
         return date;
    }


    @Override
    public int compareTo(@NonNull ForecastDay o) {
        return (int)((day /1000) - (o.day / 1000));
    }

    @Override
    public int displayType() {
        return DISPLAY_TYPE_DAY_HEADER;
    }
}
