package com.example.rws.weatherapp.UI;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rws.weatherapp.Models.Current.CurrentWeather;
import com.example.rws.weatherapp.Models.WeatherConditions;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.R;
import com.example.rws.weatherapp.Utils.FormatUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Marx on 10/1/2017.
 */

public class CurrentViewHolder extends AbsViewHolder {

    protected final TextView lblCurrent;
    protected final TextView lblTemp;
    protected final ImageView imgCondition;
    protected final LinearLayout llConditions;
    protected final TextView lblHumidity;
    protected final TextView lblWind;
    protected final TextView lblPressure;
    protected final TextView lblClouds;

    public CurrentViewHolder(Context context) {
        super(((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_mvp_current, null));
        lblCurrent = (TextView)itemView.findViewById(R.id.lbl_current);
        lblTemp = (TextView)itemView.findViewById(R.id.lbl_temp);
        imgCondition = (ImageView)itemView.findViewById(R.id.img_condition);
        llConditions = (LinearLayout)itemView.findViewById(R.id.ll_conditions);
        lblHumidity = (TextView)itemView.findViewById(R.id.lbl_humidity_value);
        lblWind = (TextView)itemView.findViewById(R.id.lbl_wind_value);
        lblPressure = (TextView)itemView.findViewById(R.id.lbl_pressure_value);
        lblClouds = (TextView)itemView.findViewById(R.id.lbl_clouds_value);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
    }

    public void setData(final CurrentWeather data, final Context context, final String units){
        if(data == null){
            return;
        }
        lblCurrent.setText(context.getResources().getString(R.string.mvp_adapter_current_header, data.name));
        lblTemp.setText(context.getResources().getString(R.string.mvp_adapter_value_temp, FormatUtil.formatTemp(data.main.temp)));
        lblHumidity.setText(data.main.humidity.concat("%"));
        lblPressure.setText(context.getResources().getString(R.string.mvp_adapter_value_pressure, data.main.pressure));
        lblClouds.setText(data.clouds.all.concat("%"));
        String speedUnits;
        switch (units){
            case NetworkConstants.VALUE_IMPERIAL:
                speedUnits = context.getResources().getString(R.string.mvp_speed_imperial);
                break;
            case NetworkConstants.VALUE_METRIC:
                speedUnits = context.getResources().getString(R.string.mvp_speed_metric);
                break;
            default:
                speedUnits = "";
                break;
        }
        lblWind.setText(new StringBuilder()
                .append(FormatUtil.getDirection(context, data.wind.deg))
                .append(" ")
                .append(data.wind.speed)
                .append(" ")
                .append(speedUnits));
        try{
            Picasso.with(context).load(String.format(NetworkConstants.ICON_ENDPOINT, data.weather.get(0).icon)).into(imgCondition);
        } catch (Exception e){
            e.printStackTrace();
        }
        llConditions.removeAllViews();
        for(WeatherConditions condition : data.weather){
            final TextView lblCondition = new TextView(context);
            lblCondition.setTextColor(Color.WHITE);
            lblCondition.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            lblCondition.setText(condition.description);
            llConditions.addView(lblCondition);
        }
    }

}
