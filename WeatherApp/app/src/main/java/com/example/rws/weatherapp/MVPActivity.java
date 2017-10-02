package com.example.rws.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rws.weatherapp.Models.Current.CurrentWeather;
import com.example.rws.weatherapp.Models.Forecast.Forecast;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.Network.QueryParameter;
import com.example.rws.weatherapp.Network.ServerCall;
import com.example.rws.weatherapp.Utils.NetworkUtil;
import com.example.rws.weatherapp.Utils.StringUtil;
import com.example.rws.weatherapp.Utils.WeatherAdapter;
import com.google.gson.Gson;

import java.util.concurrent.atomic.AtomicBoolean;

public class MVPActivity extends AppCompatActivity {

    protected final int UNITS_F = 0;
    protected final int UNITS_C = 1;

    protected int units = UNITS_F;


    protected EditText txtCityName;
    protected EditText txtZip;
    protected EditText txtCountry;
    protected Switch switchUnits;
    protected FrameLayout flSearch;
    protected RecyclerView recycler;
    protected ProgressBar progress;

    protected WeatherAdapter adapter;

    //Nightmares ensue when you have multiple searches running.
    protected final AtomicBoolean isLoading = new AtomicBoolean(false);

    protected String lastSearchZip;
    protected String lastSearchCity;
    protected String lastSearchCountry;
    protected CurrentWeather current;
    protected Forecast forecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initLayout();
    }

    /**
     * Assign all the widgets and set adapters and listeners
     */
    protected void initLayout(){
        txtCityName = (EditText)findViewById(R.id.txt_city_name);
        txtZip = (EditText)findViewById(R.id.txt_zip);
        txtCountry = (EditText)findViewById(R.id.txt_country);
        switchUnits = (Switch)findViewById(R.id.switch_units);
        flSearch = (FrameLayout)findViewById(R.id.fl_search);
        recycler = (RecyclerView)findViewById(R.id.recycler);
        progress = (ProgressBar)findViewById(R.id.progress);
        flSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoading.compareAndSet(false, true)){
                    search(false);
                }
            }
        });
        switchUnits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                units = isChecked ? UNITS_C : UNITS_F;
                adapter.setUnits(getUnitsString());
                if(current != null && isLoading.compareAndSet(false, true)){
                    search(true);
                }
            }
        });
        adapter = new WeatherAdapter(getApplicationContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        txtCountry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                flSearch.performClick();
                return false;
            }
        });
    }

    /**
     * Find weather based on user input
     * @param useHistory if true use the last search, else parse the input fields
     */
    protected void search(final boolean useHistory){
        //lower the keyboard on search
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(flSearch.getWindowToken(), 0);
        final String city = useHistory ? lastSearchCity : txtCityName.getText().toString();
        final String zip = useHistory ? lastSearchZip : txtZip.getText().toString();
        final String country = useHistory ? lastSearchCountry : txtCountry.getText().toString();

        if(StringUtil.isGoodString(city) || StringUtil.isGoodString(zip)){
            //we can proceed with the search
            adapter.clearData();
            switchUnits.setClickable(false);
            progress.setIndeterminate(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //We update current first then get the forecast for a better user experience
                    lastSearchZip = zip;
                    lastSearchCity = city;
                    lastSearchCountry = country;
                    current = getCurrent(zip, city, country);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setCurrentWeather(current);
                            //now get the forecast
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    forecast = getForecast(zip, city, country);
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
        } else {
            Toast.makeText(getApplicationContext(), R.string.mvp_error_input, Toast.LENGTH_SHORT).show();
            isLoading.set(false);
            switchUnits.setClickable(true);
        }
    }

    /**
     * Run in a background Thread. get the forecast based on search criteria
     */
    protected Forecast getForecast(final String zip, final String city, final String country){
        final ServerCall serverCall = new ServerCall(getApplicationContext());
        serverCall.endpoint = NetworkConstants.FORECAST_ENDPOINT;
        addSearchParams(serverCall, zip, city, country);
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
    protected CurrentWeather getCurrent(final String zip, final String city, final String country){
        //prioritize zip over city
        final ServerCall serverCall = new ServerCall(getApplicationContext());
        serverCall.endpoint = NetworkConstants.CURRENT_ENDPOINT;
        addSearchParams(serverCall, zip, city, country);
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
    protected void addSearchParams(final ServerCall serverCall, final String zip, final String city, final String country){
        serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_UNITS, getUnitsString()));
        if(StringUtil.isGoodString(zip)){
            if(StringUtil.isGoodString(country)){
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_ZIP_CODE, zip.concat(",").concat(country)));
            } else {
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_ZIP_CODE, zip));
            }
        } else if(StringUtil.isGoodString(city)){
            if(StringUtil.isGoodString(country)){
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_CITY_NAME, city.concat(",").concat(country)));
            } else {
                serverCall.parameters.add(new QueryParameter(NetworkConstants.KEY_CITY_NAME, city));
            }
        }
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
