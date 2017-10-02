package com.example.rws.weatherapp.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.rws.weatherapp.R;
import com.example.rws.weatherapp.Utils.FormatUtil;

import java.util.Date;

/**
 * Created by Marx on 10/1/2017.
 */

public class ForecastDayViewHolder extends AbsViewHolder {

    protected final TextView lblDate;

    public ForecastDayViewHolder(final Context context) {
        super(((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_mvp_forecast_day, null));
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        lblDate = (TextView)itemView.findViewById(R.id.lbl_date);
    }

    public void setData(final Date date){
        try{
            lblDate.setText(FormatUtil.FULL_DATE_FORMAT.format(date));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
