package com.example.rws.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.Network.QueryParameter;
import com.example.rws.weatherapp.Network.ServerCall;
import com.example.rws.weatherapp.Utils.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final ServerCall serverCall = new ServerCall(getApplicationContext());
                serverCall.endpoint = NetworkConstants.CURRENT_ENDPOINT;
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_CITY_ID, "4247770"));
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_UNITS, NetworkConstants.VALUE_IMPERIAL));
                try{
                    final String response = NetworkUtil.get(serverCall);
                    Log.v(NetworkConstants.LOG_TAG, response);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
