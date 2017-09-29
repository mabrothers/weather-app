package com.example.rws.weatherapp.Models;

/**
 * Created by Marx on 9/27/2017.
 */

/**
 * Parse all fields as Strings for formatting.
 */
public class WeatherMain {

    public String temp;
    public String pressure;
    public String humidity;

    @Override
    public String toString() {
        return "WeatherMain{" +
                "temp='" + temp + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
