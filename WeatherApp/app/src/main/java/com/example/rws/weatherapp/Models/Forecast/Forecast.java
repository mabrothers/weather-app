package com.example.rws.weatherapp.Models.Forecast;

import java.util.List;

/**
 * Created by Marx on 9/29/2017.
 */

public class Forecast {

    public List<PartialDayForecast> list;

    public String country;
    public City city;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Forecast{");
        if(list != null){
            builder.append("list=[");
            for(PartialDayForecast forecast : list){
                builder.append(forecast.toString()).append(",");
            }
            builder.append("],");
        }
        builder.append(" country='").append(country).append(", city=").append(city).append('}');

        return builder.toString();
    }
}
