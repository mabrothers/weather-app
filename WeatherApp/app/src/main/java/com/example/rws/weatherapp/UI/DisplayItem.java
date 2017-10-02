package com.example.rws.weatherapp.UI;

/**
 * Created by Marx on 10/1/2017.
 */

public interface DisplayItem {

    int DISPLAY_TYPE_CURRENT = 1;
    int DISPLAY_TYPE_FORECAST_HEADER = 2;
    int DISPLAY_TYPE_DAY_HEADER = 3;
    int DISPLAY_TYPE_PARTIAL_DAY = 4;

    int displayType();
}
