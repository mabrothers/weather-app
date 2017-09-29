package com.example.rws.weatherapp.Models.Forecast;

import com.example.rws.weatherapp.Models.Coordinates;

/**
 * Created by Marx on 9/29/2017.
 */

public class City {

    public String name;
    public Coordinates coord;

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", coord=" + coord +
                '}';
    }
}
