package com.example.rws.weatherapp.Utils;

import android.content.Context;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.rws.weatherapp.R;

/**
 * Created by Marx on 10/1/2017.
 */

public class FormatUtil {

    protected static final DecimalFormat TEMP_FORMAT = new DecimalFormat("0");

    public final static SimpleDateFormat HOURLY_FORMAT = new SimpleDateFormat("h a", Locale.US);
    public final static SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("EE, MMM d yyyy", Locale.getDefault());

    public static String formatTemp(final String temp){
        return TEMP_FORMAT.format(Float.parseFloat(temp));
    }


    /**
     *
     * @param context Application context
     * @param deg value from the server
     * @return Localized String representation of one of 8 compass points, or null if no point could be parsed
     */
    public static String getDirection(final Context context, final String deg){

        // 22.5 each way
        //0 N           337-359,0-22
        //45 NE         23-45,46-67
        //90 E          68-90,91-112
        //135 SE        113-135,136-157
        //180 S         158-180,181-202
        //225 SW        203-225,226-247
        //270 W         248-270,271-292
        //315 NW        293-315,315-336

        int resource = -1;
        try{
            final float degrees = Float.valueOf(deg) % 360;
            if((degrees >=0 && degrees <= 22) || (degrees >= 337 && degrees <= 359)){
                resource = R.string.mvp_direction_n;
            } else if(degrees >= 23 && degrees <= 67){
                resource = R.string.mvp_direction_ne;
            } else if(degrees >= 68 && degrees <= 112){
                resource = R.string.mvp_direction_e;
            } else if(degrees >= 113 && degrees <= 157){
                resource = R.string.mvp_direction_se;
            } else if(degrees >= 158 && degrees <= 202){
                resource = R.string.mvp_direction_s;
            } else if(degrees >= 203 && degrees <= 247){
                resource = R.string.mvp_direction_se;
            } else if(degrees >= 248 && degrees <= 292){
                resource = R.string.mvp_direction_w;
            } else if(degrees >= 393 && degrees <= 336){
                resource = R.string.mvp_direction_nw;
            }
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(resource != -1){
            return context.getResources().getString(resource);
        }

        return null;
    }

}
