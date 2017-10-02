package com.example.rws.weatherapp.Utils;

import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;

import com.example.rws.weatherapp.Models.Google.PlaceDetails;
import com.example.rws.weatherapp.Models.Google.Predictions;
import com.example.rws.weatherapp.Network.GoogleServerCall;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.Network.QueryParameter;
import com.google.gson.Gson;

/**
 * Created by Marx on 10/2/2017.
 */

public class LocationUtil {



    public static PlaceDetails getPlace(final Context context, final String placeID){
        final GoogleServerCall serverCall = new GoogleServerCall(context);
        serverCall.endpoint = NetworkConstants.GOOGLE_PLACE_DETAILS_ENDPOINT;
        serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_GOOGLE_PLACE_ID, placeID));
        try{
            final String response = NetworkUtil.get(serverCall);
//            Log.v("test", response);
            return new Gson().fromJson(response, PlaceDetails.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static Predictions getSuggestions(final Context context, final String query){
        final GoogleServerCall serverCall = new GoogleServerCall(context);
        serverCall.endpoint = NetworkConstants.GOOGLE_AUTO_COMPLETE_ENDPOINT;
        serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_GOOGLE_QUERY, query));
        try{
            final String response = NetworkUtil.get(serverCall);
//            Log.v("test", response);
            return new Gson().fromJson(response, Predictions.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
