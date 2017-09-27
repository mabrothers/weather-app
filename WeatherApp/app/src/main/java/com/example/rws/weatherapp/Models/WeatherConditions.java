package com.example.rws.weatherapp.Models;

/**
 * Created by Marx on 9/27/2017.
 */

public class WeatherConditions {

    public String id; //comes back as an int, parse as String for future proofing

    /**
     * i.e. "Clouds"
     */
    public String main;

    /**
     * i.e. "overcast clouds"
     */
    public String description;

    /**
     * parse for either local or remote image
     */
    public String icon;
}
