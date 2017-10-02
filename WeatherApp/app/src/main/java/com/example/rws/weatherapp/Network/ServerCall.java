package com.example.rws.weatherapp.Network;

import android.content.Context;

import com.example.rws.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marx on 9/27/2017.
 */

public class ServerCall extends BaseServerCall{


    public ServerCall(final Context context){

        //This will be needed in all calls
        parameters.add(new QueryParameter(NetworkConstants.KEY_APP_ID, context.getResources().getString(R.string.owm_api_key)));
    }


}
