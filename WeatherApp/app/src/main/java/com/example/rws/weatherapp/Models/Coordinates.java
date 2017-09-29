package com.example.rws.weatherapp.Models;

/**
 * Created by Marx on 9/27/2017.
 */

public class Coordinates {

    public String lat; //parse as Strings for safety

    @Override
    public String toString() {
        return "Coordinates{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

    public String lon;

}
