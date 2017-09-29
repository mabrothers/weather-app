package com.example.rws.weatherapp.Utils;

/**
 * Created by Marx on 9/29/2017.
 */

public class StringUtil {

    /**
     *
     * @param toCheck a list of Strings to evaluate
     * @return true if all Strings evaluate as good per isGoodString
     */
    public static boolean areGoodStrings(String... toCheck){
        if(toCheck == null){
            return false;
        }
        for(String s : toCheck){
            if(!isGoodString(s)){
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param toCheck The String to evaluate
     * @return true if toCheck is not null and does not equal empty String ""
     */
    public static boolean isGoodString(String toCheck){
        return toCheck != null && !toCheck.equals("");
    }

}
