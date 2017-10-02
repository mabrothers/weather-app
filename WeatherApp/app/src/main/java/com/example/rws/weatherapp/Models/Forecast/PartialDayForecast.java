package com.example.rws.weatherapp.Models.Forecast;

import com.example.rws.weatherapp.Models.Clouds;
import com.example.rws.weatherapp.Models.WeatherConditions;
import com.example.rws.weatherapp.Models.WeatherMain;
import com.example.rws.weatherapp.Models.Wind;
import com.example.rws.weatherapp.UI.DisplayItem;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Marx on 9/27/2017.
 * 3 hour period
 */

public class PartialDayForecast implements DisplayItem {

    public WeatherMain main;
    public List<WeatherConditions> weather;
    public Clouds clouds;
    public Wind wind;

    protected transient Date date;
    protected transient Date day;

    /**
     * epoch of this part
     */
    public long dt;

    /**
     * Text representation of the time in GMT. Probably useless
     */
    public String dt_txt;

    public Date getDate() {
        if(date == null){
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dt * 1000);
            date = calendar.getTime();
        }
        return date;
    }

    public Date getDay() {
        if(day == null){
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dt * 1000);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            day = calendar.getTime();
        }
        return day;
    }

    @Override
    public String toString() {
        return "PartialDayForecast{" +
                "main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", dt=" + dt +
                ", dt_txt='" + dt_txt + '\'' +
                '}';
    }

    @Override
    public int displayType() {
        return DISPLAY_TYPE_PARTIAL_DAY;
    }
}

