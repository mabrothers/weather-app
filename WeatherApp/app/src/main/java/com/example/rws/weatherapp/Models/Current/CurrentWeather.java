package com.example.rws.weatherapp.Models.Current;

import com.example.rws.weatherapp.Models.Clouds;
import com.example.rws.weatherapp.Models.Coordinates;
import com.example.rws.weatherapp.Models.WeatherConditions;
import com.example.rws.weatherapp.Models.WeatherMain;
import com.example.rws.weatherapp.Models.Wind;

import java.util.List;

/**
 * Created by Marx on 9/27/2017.
 */

public class CurrentWeather {

    public WeatherMain main;
    public Coordinates coord;
    public List<WeatherConditions> weather;
    public Wind wind;
    public Clouds clouds;
    public SystemData sys;

    /**
     * No documentation and no indication of what the units are so probably won't display
     */
    public long visibility;

    /**
     * epoch time of last update.
     */
    public long dt;

    /**
     * City ID
     */
    public String id;

    /**
     * City name
     */
    public String name;

}
