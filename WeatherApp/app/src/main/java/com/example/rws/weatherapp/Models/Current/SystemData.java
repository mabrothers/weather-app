package com.example.rws.weatherapp.Models.Current;

/**
 * Created by Marx on 9/27/2017.
 */

public class SystemData {

    /**
     * 2 digit country code. I could probably do some translation on this for UI
     */
    public String country;

    /**
     * epoch time. Can display somewhere in UI. Also for determining day/night icons
     */
    public long sunrise;
    public long sunset;


}
