package com.example.rws.weatherapp.Models.Google;

/**
 * Created by Marx on 10/2/2017.
 */

public class PlaceDetails {


    public Result result;


    public String getLat(){
        return result != null && result.geometry != null && result.geometry.location != null ? result.geometry.location.lat : null;
    }
    public String getLon(){
        return result != null && result.geometry != null && result.geometry.location != null ? result.geometry.location.lng : null;
    }

    public static class Result {
        public Geometry geometry;
    }

    public static class Geometry {
        public Location location;
    }

    public static class Location {
        public String lat;
        public String lng;
    }
}
