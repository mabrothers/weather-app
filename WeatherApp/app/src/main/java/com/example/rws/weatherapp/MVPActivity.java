package com.example.rws.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.Utils.WeatherAdapter;

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

    protected final AtomicBoolean isLoading = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initLayout();
    }


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
                    search();
                }
            }
        });
        adapter = new WeatherAdapter(getApplicationContext());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }

    protected void search(){
        //prioritize zip over city
        progress.setIndeterminate(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setIndeterminate(false);
                        isLoading.set(false);
                    }
                });
            }
        }).start();
    }

    protected void getCurrent(){

    }

    protected void getForecast(){

    }

}
