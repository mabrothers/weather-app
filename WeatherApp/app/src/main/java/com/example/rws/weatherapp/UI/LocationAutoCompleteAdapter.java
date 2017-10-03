package com.example.rws.weatherapp.UI;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.rws.weatherapp.Models.Google.Prediction;
import com.example.rws.weatherapp.Models.Google.Predictions;
import com.example.rws.weatherapp.Utils.LocationUtil;
import com.example.rws.weatherapp.Utils.StringUtil;

import java.util.List;

/**
 * Created by Marx on 10/2/2017.
 */

public class LocationAutoCompleteAdapter extends ArrayAdapter<Prediction> implements Filterable {


    protected static final int MIN_SEARCH_LENGTH = 3;
    protected Predictions predictions;
    protected Context appContext;

    protected final Handler handler;

    public LocationAutoCompleteAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        appContext = context.getApplicationContext();
        handler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public int getCount() {
        return predictions == null || predictions.predictions == null ? 0 : predictions.predictions.size();
    }

    @Nullable
    @Override
    public Prediction getItem(int position) {
        return predictions == null || predictions.predictions == null ? null : predictions.predictions.get(position);
    }

    protected final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            if(constraint == null || constraint.length() < MIN_SEARCH_LENGTH){
                return null;
            }
            //If we went into production we'd want to delay sending this call off to lower our costs
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Predictions filteredPredictions = LocationUtil.getSuggestions(appContext, constraint.toString());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            predictions = filteredPredictions;
                            notifyDataSetChanged();
                        }
                    });
                }
            }).start();
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

}
