package com.example.rws.weatherapp.Network;

import android.content.Context;

import com.example.rws.weatherapp.R;

/**
 * Created by Marx on 10/2/2017.
 */

public class GoogleServerCall extends BaseServerCall {


    public GoogleServerCall(final Context context){

        //This will be needed in all calls
        parameters.add(new QueryParameter(NetworkConstants.KEY_GOOGLE_API, context.getResources().getString(R.string.google_places_api_key)));
    }
}
