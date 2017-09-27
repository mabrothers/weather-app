package com.example.rws.weatherapp.Network;

/**
 * Created by Marx on 9/27/2017.
 */

public class QueryParameter {

    public final String name;
    public final String value;

    public QueryParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public QueryParameter(String name, int value) {
        this.name = name;
        this.value = String.valueOf(value);
    }


    public QueryParameter(String name, long value) {
        this.name = name;
        this.value = String.valueOf(value);
    }

    public QueryParameter(String name, boolean value) {
        this.name = name;
        this.value = String.valueOf(value);
    }


}
