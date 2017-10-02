package com.example.rws.weatherapp.Models.Google;

/**
 * Created by Marx on 10/2/2017.
 */

public class Prediction {

    public String place_id;
    public String description;

    @Override
    public String toString() {
        return description;
    }
}
