package com.example.rws.weatherapp.Network;

/**
 * Created by Marx on 9/27/2017.
 */

public class NetworkConstants {

    public static final String BASE_SERVER = "api.openweathermap.org/data/2.5/";
    public static final String CURRENT_ENDPOINT = BASE_SERVER + "weather";
    public static final String FORECAST_ENDPOINT = BASE_SERVER + "forecast";


    public static final String KEY_APP_ID = "APPID";

    /**
     * City and zip can accept a country code. Comma separated
     */
    public static final String KEY_CITY_NAME = "q";
    public static final String KEY_ZIP_CODE = "zip";
    public static final String KEY_CITY_ID = "id";
    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LONGITUDE = "lon";

    public static final String KEY_UNITS = "units";

    public static final String VALUE_IMPERIAL = "imperial";
    public static final String VALUE_METRIC = "metric";


    //TODO language (lang) may be added at a later point
}
