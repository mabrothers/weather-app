package com.example.rws.weatherapp.Models.Google;

import java.util.List;

/**
 * Created by Marx on 10/2/2017.
 */

public class Predictions {

    public List<Prediction> predictions;


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Predictions{");

        if(predictions != null){
            for(int i = 0; i < predictions.size(); i++){
                if(i > 0){
                    builder.append(", ");
                }
                builder.append(predictions.get(i).toString());
            }
        }

        builder.append('}');
        return builder.toString();
    }
}
