package com.example.rws.weatherapp.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rws.weatherapp.Models.Forecast.PartialDayForecast;
import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.R;
import com.example.rws.weatherapp.Utils.FormatUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Marx on 10/1/2017.
 */

public class ForecastPartViewHolder extends AbsViewHolder {

    protected final TextView lblTime;
    protected final ImageView imgCondition;
    protected final TextView lblTemp;
    protected final TextView lblHumidity;
    protected final TextView lblWind;

    public ForecastPartViewHolder(Context context) {
        super(((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.adapter_mvp_forecast_part, null));
        lblTime = (TextView)itemView.findViewById(R.id.lbl_time);
        imgCondition = (ImageView)itemView.findViewById(R.id.img_condition);
        lblTemp = (TextView)itemView.findViewById(R.id.lbl_temp);
        lblHumidity = (TextView)itemView.findViewById(R.id.lbl_humidity);
        lblWind = (TextView)itemView.findViewById(R.id.lbl_wind);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));


    }

    public void setData(final PartialDayForecast data, Context context, final String units){

        try{
            Picasso.with(context).load(String.format(NetworkConstants.ICON_ENDPOINT, data.weather.get(0).icon)).into(imgCondition);
        } catch (Exception e){
            e.printStackTrace();
        }
        lblTemp.setText(context.getResources().getString(R.string.mvp_adapter_value_temp, FormatUtil.formatTemp(data.main.temp)));
        String speedUnits;
        switch (units){
            case NetworkConstants.VALUE_IMPERIAL:
                speedUnits = context.getResources().getString(R.string.mvp_speed_imperial);
                break;
            case NetworkConstants.VALUE_METRIC:
                speedUnits = context.getResources().getString(R.string.mvp_speed_metric_short);
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

        lblHumidity.setText(data.main.humidity.concat(context.getResources().getString(R.string.mvp_adapter_humid_short)));
        lblTime.setText(FormatUtil.HOURLY_FORMAT.format(data.getDate()));

    }


}
