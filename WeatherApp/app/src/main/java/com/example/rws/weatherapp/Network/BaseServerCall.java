package com.example.rws.weatherapp.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marx on 10/2/2017.
 */

public abstract class BaseServerCall {

    public String endpoint;
    public final List<QueryParameter> parameters = new ArrayList<>();

}
