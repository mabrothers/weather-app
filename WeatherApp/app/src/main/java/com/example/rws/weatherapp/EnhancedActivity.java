package com.example.rws.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.rws.weatherapp.Models.Current.CurrentWeather;
import com.example.rws.weatherapp.Models.Forecast.Forecast;
import com.example.rws.weatherapp.Models.Google.PlaceDetails;
import com.example.rws.weatherapp.Models.Google.Prediction;
import com.example.rws.weatherapp.Network.BaseServerCall;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.Network.QueryParameter;
import com.example.rws.weatherapp.Network.ServerCall;
import com.example.rws.weatherapp.UI.LocationAutoCompleteAdapter;
import com.example.rws.weatherapp.Utils.LocationUtil;
import com.example.rws.weatherapp.Utils.NetworkUtil;
import com.example.rws.weatherapp.Utils.StringUtil;
import com.example.rws.weatherapp.Utils.WeatherAdapter;
import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicBoolean;

public class EnhancedActivity extends AppCompatActivity {

    protected final int UNITS_F = 0;
    protected final int UNITS_C = 1;

    protected int units = UNITS_F;

    protected FrameLayout flGPS;
    protected AutoCompleteTextView input;
    protected Switch switchUnits;
    protected RecyclerView recycler;
    protected ProgressBar progress;

    protected WeatherAdapter adapter;
    protected LocationAutoCompleteAdapter locationAdapter;

    //Nightmares ensue when you have multiple searches running.
    protected final AtomicBoolean isLoading = new AtomicBoolean(false);

    protected CurrentWeather current;
    protected Forecast forecast;

    protected String lastLat;
    protected String lastLon;
    protected Prediction lastPrediction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enhanced);
        initLayout();

    }

    protected void initLayout(){
        flGPS = (FrameLayout)findViewById(R.id.fl_gps);
        input = (AutoCompleteTextView)findViewById(R.id.input);
        switchUnits = (Switch)findViewById(R.id.switch_units);
        recycler = (RecyclerView)findViewById(R.id.recycler);
        progress = (ProgressBar)findViewById(R.id.progress);
        adapter = new WeatherAdapter(getApplicationContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        locationAdapter = new LocationAutoCompleteAdapter(this);
        input.setAdapter(locationAdapter);
        switchUnits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                units = isChecked ? UNITS_C : UNITS_F;
                adapter.setUnits(getUnitsString());
                if(current != null && isLoading.compareAndSet(false, true)){
                    search();
                }
            }
        });
        input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(isLoading.compareAndSet(false, true)){
                    lastPrediction = locationAdapter.getItem(position);
                    adapter.clearData();
                    progress.setIndeterminate(true);
                    switchUnits.setClickable(false);
                    input.setText("");
                    getDetails();
                }

            }
        });
    }

    protected void getDetails(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //get place details
                final PlaceDetails placeDetails = LocationUtil.getPlace(getApplicationContext(), lastPrediction.place_id);
                lastLat = placeDetails.getLat();
                lastLon = placeDetails.getLon();
                search();
            }
        }).start();
    }

    protected void search(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //We update current first then get the forecast for a better user experience
                current = getCurrent(lastLat, lastLon);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setCurrentWeather(current);
                        //now get the forecast
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                forecast = getForecast(lastLat, lastLon);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.setForecast(forecast);
                                        progress.setIndeterminate(false);
                                        switchUnits.setClickable(true);
                                        isLoading.set(false);
                                    }
                                });
                            }
                        }).start();
                    }
                });
            }
        }).start();
    }

    /**
     * Run in a background Thread. get the forecast based on search criteria
     */
    protected Forecast getForecast(final String lat, final String lon){
        final ServerCall serverCall = new ServerCall(getApplicationContext());
        serverCall.endpoint = NetworkConstants.FORECAST_ENDPOINT;
        addSearchParams(serverCall, lat, lon);
        try{
            final String response = NetworkUtil.get(serverCall);
            if(!StringUtil.isGoodString(response)){
                return null;
            }

            return new Gson().fromJson(response, Forecast.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Run in a background Thread. get the current weather based on search criteria
     */
    protected CurrentWeather getCurrent(final String lat, final String lon){
        final ServerCall serverCall = new ServerCall(getApplicationContext());
        serverCall.endpoint = NetworkConstants.CURRENT_ENDPOINT;
        addSearchParams(serverCall, lat, lon);
        try{
            final String response = NetworkUtil.get(serverCall);
            if(!StringUtil.isGoodString(response)){
                return null;
            }
            return new Gson().fromJson(response, CurrentWeather.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The search parameters are the same between both calls
     */
    protected void addSearchParams(final BaseServerCall serverCall, final String lat, final String lon){
        serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_UNITS, getUnitsString()));
        serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_LATITUDE, lat));
        serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_LONGITUDE, lon));
    }

    /**
     * Helper method to convert the unit parameter into a string the server can understand
     */
    protected String getUnitsString(){
        switch (units){
            case UNITS_C:
                return NetworkConstants.VALUE_METRIC;
            case UNITS_F:
            default:
                return NetworkConstants.VALUE_IMPERIAL;
        }
    }
}
