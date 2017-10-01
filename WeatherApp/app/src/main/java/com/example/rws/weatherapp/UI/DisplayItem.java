package com.example.rws.weatherapp.UI;

/**
 * Created by Marx on 10/1/2017.
 */

public interface DisplayItem {

    public static final int DISPLAY_TYPE_CURRENT = 1;
    public static final int DISPLAY_TYPE_DAY_HEADER = 2;
    public static final int DISPLAY_TYPE_PARTIAL_DAY = 3;

    int displayType();
}
