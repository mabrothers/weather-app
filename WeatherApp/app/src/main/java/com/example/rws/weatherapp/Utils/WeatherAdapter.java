package com.example.rws.weatherapp.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.rws.weatherapp.Models.Current.CurrentWeather;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.UI.AbsViewHolder;
import com.example.rws.weatherapp.UI.CurrentViewHolder;
import com.example.rws.weatherapp.UI.DisplayItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marx on 10/1/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<AbsViewHolder> {

    protected final Context context;
    protected String units = NetworkConstants.VALUE_IMPERIAL;

    protected CurrentWeather currentWeather;
    protected final List<DisplayItem> displayItems = new ArrayList<>();


    public WeatherAdapter(final Context context) {
        this.context = context;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
        populate();
    }

    public void clearData(){
        currentWeather = null;
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
        notifyDataSetChanged();
    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case DisplayItem.DISPLAY_TYPE_CURRENT:
                return new CurrentViewHolder(context);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case DisplayItem.DISPLAY_TYPE_CURRENT:
                ((CurrentViewHolder)holder).setData((CurrentWeather)displayItems.get(position), context, units);
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
