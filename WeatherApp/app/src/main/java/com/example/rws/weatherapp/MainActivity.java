package com.example.rws.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rws.weatherapp.Models.Current.CurrentWeather;
import com.example.rws.weatherapp.Models.Forecast.Forecast;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.Network.QueryParameter;
import com.example.rws.weatherapp.Network.ServerCall;
import com.example.rws.weatherapp.Utils.NetworkUtil;
import com.example.rws.weatherapp.Utils.StringUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    protected final String LOG_TAG_POC = "POC";

    protected final int TYPE_CURRENT = 0;
    protected final int TYPE_FORECAST = 1;

    protected final int MODE_CITY_ID = 0;
    protected final int MODE_ZIP = 1;
    protected final int MODE_CITY_NAME = 2;
    protected final int MODE_CITY_AND_COUNTRY = 3;
    protected final int MODE_LAT_LONG = 4;

    protected EditText txtID;
    protected EditText txtZip;
    protected EditText txtCity;
    protected EditText txtCountry;
    protected EditText txtLat;
    protected EditText txtLon;
    protected Button btnCurrent;
    protected Button btnForecast;
    protected TextView lblResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poc);
        initLayout();

    }

    /**
     * Assign all the widgets
     */
    protected void initLayout(){
        txtID = (EditText)findViewById(R.id.txt_city_id);
        txtZip = (EditText)findViewById(R.id.txt_zip);
        txtCity = (EditText)findViewById(R.id.txt_city_name);
        txtCountry = (EditText)findViewById(R.id.txt_country);
        txtLat = (EditText)findViewById(R.id.txt_lat);
        txtLon = (EditText)findViewById(R.id.txt_long);
        btnCurrent = (Button)findViewById(R.id.btn_current);
        btnForecast = (Button)findViewById(R.id.btn_forecast);
        lblResult = (TextView)findViewById(R.id.lbl_result);
        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput(TYPE_CURRENT);
            }
        });
        btnForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput(TYPE_FORECAST);
            }
        });
    }

    /**
     * Reset all input fields
     */
    protected void clearInput(){
        txtID.setText("");
        txtZip.setText("");
        txtCity.setText("");
        txtCountry.setText("");
        txtLat.setText("");
        txtLon.setText("");
    }

    /**
     * Make sure the user entered in something we can use
     * @param type current or forecast
     */
    protected void validateInput(final int type){
        lblResult.setText("");
        final String cityID = txtID.getText().toString();
        if(StringUtil.isGoodString(cityID)){
            getWeather(type, MODE_CITY_ID, cityID);
            return;
        }
        final String zip = txtZip.getText().toString();
        if(StringUtil.isGoodString(zip)){
            getWeather(type, MODE_ZIP, zip);
            return;
        }
        final String city = txtCity.getText().toString();
        if(StringUtil.isGoodString(city)){
            final String country = txtCountry.getText().toString();
            if(StringUtil.isGoodString(country)){
                getWeather(type, MODE_CITY_AND_COUNTRY, city, country);
            } else {
                getWeather(type, MODE_CITY_NAME, city);
            }
            return;
        }
        final String lat = txtLat.getText().toString();
        final String lon = txtLon.getText().toString();
        if(StringUtil.areGoodStrings(lat, lon)){
            getWeather(type, MODE_LAT_LONG, lat, lon);
            return;
        }
        Toast.makeText(getApplicationContext(), R.string.poc_error_input, Toast.LENGTH_SHORT).show();
    }

    /**
     * Set all search parameter, make a network call and display results
     *
     * @param type current or forecast
     * @param mode what type of search we're doing
     * @param parameters 1 or more parameters to search against
     */
    protected void getWeather(final int type, final int mode, final String... parameters){
        final ServerCall serverCall = new ServerCall(getApplicationContext());
        switch (type){
            case TYPE_CURRENT:
                serverCall.endpoint = NetworkConstants.CURRENT_ENDPOINT;
                break;
            case TYPE_FORECAST:
                serverCall.endpoint = NetworkConstants.FORECAST_ENDPOINT;
                break;
        }
        //always imperial for this test
        serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_UNITS, NetworkConstants.VALUE_IMPERIAL));
        switch (mode){
            case MODE_CITY_ID:
                if(!checkParameterCount(1, parameters)){
                    clearInput();
                    return;
                }
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_CITY_ID, parameters[0]));
                break;
            case MODE_ZIP:
                if(!checkParameterCount(1, parameters)){
                    clearInput();
                    return;
                }
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_ZIP_CODE, parameters[0]));
                break;
            case MODE_CITY_NAME:
                if(!checkParameterCount(1, parameters)){
                    clearInput();
                    return;
                }
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_CITY_NAME, parameters[0]));
                break;
            case MODE_CITY_AND_COUNTRY:
                if(!checkParameterCount(2, parameters)){
                    clearInput();
                    return;
                }
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_CITY_NAME, parameters[0].concat(",").concat(parameters[1])));
                break;
            case MODE_LAT_LONG:
                if(!checkParameterCount(2, parameters)){
                    clearInput();
                    return;
                }
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_LATITUDE, parameters[0]));
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_LONGITUDE, parameters[1]));
                break;
            default:
                //
                clearInput();
                return;
        }
        //TODO some form of spinner
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String response = NetworkUtil.get(serverCall);
                    Log.v(NetworkConstants.LOG_TAG, response);
                    final CurrentWeather current;
                    final Forecast forecast;
                    if(type == TYPE_CURRENT){
                        forecast = null;
                        current = new Gson().fromJson(response, CurrentWeather.class);
                    } else if(type == TYPE_FORECAST){
                        current = null;
                        forecast = new Gson().fromJson(response, Forecast.class);
                    } else {
                        current = null;
                        forecast = null;
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearInput();

                            // make sure the response parses into the models correctly
                            if(current != null){
                                lblResult.setText(current.toString());
                            } else if(forecast != null){
                                lblResult.setText(forecast.toString());
                            }

//                            try{
//                                //try formatting the response
//                                lblResult.setText(new JSONObject(response).toString(2));
//                            } catch (Exception e){
//                                e.printStackTrace();
//                                //just show it unformatted
//                                lblResult.setText(response);
//                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     *
     * @param expectedParamCount number of parameters that should be present
     * @param parameters list of parameters to validate
     * @return true if there are the correct number of parameters
     */
    protected boolean checkParameterCount(final int expectedParamCount, final String... parameters){
        final boolean invalid = parameters == null || parameters.length < expectedParamCount;
        if(invalid){
            Log.v(LOG_TAG_POC, "Wrong number of parameters");
        }
        return !invalid;
    }


}
