package com.example.rws.weatherapp.Models.Forecast;

import com.example.rws.weatherapp.Models.Clouds;
import com.example.rws.weatherapp.Models.WeatherConditions;
import com.example.rws.weatherapp.Models.WeatherMain;
import com.example.rws.weatherapp.Models.Wind;

import java.util.List;

/**
 * Created by Marx on 9/27/2017.
 * 3 hour period
 */

public class PartialDayForecast {

    public WeatherMain main;
    public List<WeatherConditions> weather;
    public Clouds clouds;
    public Wind wind;


    /**
     * epoch of this part
     */
    public long dt;

    /**
     * Text representation of the time in GMT. Probably useless
     */
    public String dt_txt;

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
}

