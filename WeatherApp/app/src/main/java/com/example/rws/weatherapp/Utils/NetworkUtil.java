package com.example.rws.weatherapp.Utils;

import android.net.Uri;
import android.util.Log;

import com.example.rws.weatherapp.Network.NetworkConstants;
import com.example.rws.weatherapp.Network.QueryParameter;
import com.example.rws.weatherapp.Network.ServerCall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marx on 9/27/2017.
 */

public class NetworkUtil {




    public static String get(final ServerCall call) throws IOException{
        final URL url;
        if(!call.parameters.isEmpty()){
            Uri.Builder builder = new Uri.Builder();
            for(QueryParameter parameter : call.parameters){
                builder.appendQueryParameter(parameter.name, parameter.value);
            }
            url = new URL(call.endpoint + "?" + builder.build().getEncodedQuery());
        } else {
            url = new URL(call.endpoint);
        }
        Log.v(NetworkConstants.LOG_TAG, "calling " + url.toString());
        final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.connect();
        final InputStream data = urlConnection.getInputStream();

        final Writer writer = new StringWriter();
        final char[] buffer = new char[1024];
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(data, "UTF-8"), 1024);
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            if (reader != null){
                reader.close();
            }
            data.close();
        }
        urlConnection.disconnect();
        return writer.toString();
    }


}
