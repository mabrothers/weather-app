package com.example.rws.weatherapp.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.rws.weatherapp.R;

/**
 * Created by Marx on 10/1/2017.
 */

public class ForecastHeaderViewHolder extends AbsViewHolder {

    public ForecastHeaderViewHolder(final Context context) {
        super(((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_mvp_forecast_header, null));
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
    }
}
