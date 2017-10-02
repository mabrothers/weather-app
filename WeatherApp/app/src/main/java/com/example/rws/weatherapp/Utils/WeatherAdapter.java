package com.example.rws.weatherapp.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;

import com.example.rws.weatherapp.Models.Current.CurrentWeather;
import com.example.rws.weatherapp.Models.Forecast.Forecast;
import com.example.rws.weatherapp.Models.Forecast.PartialDayForecast;
import com.example.rws.weatherapp.Models.ForecastDay;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.UI.AbsViewHolder;
import com.example.rws.weatherapp.UI.CurrentViewHolder;
import com.example.rws.weatherapp.UI.DisplayItem;
import com.example.rws.weatherapp.UI.ForecastDayViewHolder;
import com.example.rws.weatherapp.UI.ForecastHeader;
import com.example.rws.weatherapp.UI.ForecastHeaderViewHolder;
import com.example.rws.weatherapp.UI.ForecastPartViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marx on 10/1/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<AbsViewHolder> {

    protected final Context context;
    protected String units = NetworkConstants.VALUE_IMPERIAL;

    protected CurrentWeather currentWeather;
    protected Forecast forecast;
    protected final List<DisplayItem> displayItems = new ArrayList<>();


    public WeatherAdapter(final Context context) {
        this.context = context;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
        populate();
    }

    public void setForecast(final Forecast forecast){
        this.forecast = forecast;
        populate();
    }

    public void clearData(){
        currentWeather = null;
        forecast = null;
        populate();
    }

    public void setUnits(String units) {
        this.units = units;
        populate();
    }

    protected void populate(){
        displayItems.clear();
        if(currentWeather != null){
            displayItems.add(currentWeather);
        }
        if(forecast != null && forecast.list != null){

            displayItems.add(new ForecastHeader());

            //now lets sort the parts by day for a better user experience

            final ArrayMap<Long, ForecastDay> parts = new ArrayMap<>();

            for(PartialDayForecast part : forecast.list){
                final Date date = part.getDay();
                final long time = date.getTime();
                ForecastDay forecastDay = parts.get(time);
                if(forecastDay == null){
                    forecastDay = new ForecastDay(time);
                    parts.put(time, forecastDay);
                }
                forecastDay.parts.add(part);
            }

            final List<ForecastDay> sortedDays = new ArrayList<>(parts.values());
            Collections.sort(sortedDays);
            for(ForecastDay forecastDay : sortedDays){
                if(!forecastDay.parts.isEmpty()){
                    displayItems.add(forecastDay);
                }
                for(PartialDayForecast part : forecastDay.parts){
                    displayItems.add(part);
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case DisplayItem.DISPLAY_TYPE_CURRENT:
                return new CurrentViewHolder(context);
            case DisplayItem.DISPLAY_TYPE_PARTIAL_DAY:
                return new ForecastPartViewHolder(context);
            case DisplayItem.DISPLAY_TYPE_FORECAST_HEADER:
                return new ForecastHeaderViewHolder(context);
            case DisplayItem.DISPLAY_TYPE_DAY_HEADER:
                return new ForecastDayViewHolder(context);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case DisplayItem.DISPLAY_TYPE_CURRENT:
                ((CurrentViewHolder)holder).setData((CurrentWeather)displayItems.get(position), context, units);
                break;
            case DisplayItem.DISPLAY_TYPE_PARTIAL_DAY:
                ((ForecastPartViewHolder)holder).setData((PartialDayForecast) displayItems.get(position), context, units);
                break;
            case DisplayItem.DISPLAY_TYPE_DAY_HEADER:
                ((ForecastDayViewHolder)holder).setData(((ForecastDay)displayItems.get(position)).getDate());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return displayItems.get(position).displayType();
    }

    @Override
    public int getItemCount() {
        return displayItems.size();
    }
}
