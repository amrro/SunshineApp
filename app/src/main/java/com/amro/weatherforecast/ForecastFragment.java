package com.amro.weatherforecast;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by amro on 3/12/16.
 */
public class ForecastFragment extends Fragment
{
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_referesh)
        {
            FetchWeatherTask fetchTask  =new FetchWeatherTask();
            fetchTask.execute("94043");
            return true;
        }
        /// Why??
        return super.onOptionsItemSelected(item);
    }
    public ForecastFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ArrayList<String> fakeData = new ArrayList<>();
        fakeData.add("Today - Sunny - 35/30");
        fakeData.add("Tomorrow - Rainy - 30/20");
        fakeData.add("Sat   - Cloudy - 20/15");
        fakeData.add("Sun- Sunny - 35/30");
        fakeData.add("Mon - Rainy - 30/20");
        fakeData.add("Thru   - Cloudy - 20/15");

        adapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                fakeData
        );



        ListView list = (ListView) view.findViewById(R.id.listview_forecast);
        list.setAdapter(adapter);

        return view;
    }


    class FetchWeatherTask extends AsyncTask<String, Void, Void>
    {

        final private String TAG = FetchWeatherTask.class.getSimpleName();
        @Override
        protected Void doInBackground(String... params)
        {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                // http://api.openweathermap.org/data/2.5/forecast/daily?
                final String QUERY_PARAM = "q";         // q=94043 postal code&
                final String QUERY_MODE = "mode";       // mode=json&
                final String QUERY_UNIT = "units";      // units=metric&
                final String QUERY_DAYS = "cnt";        // cnt=7
                final String API_KEY    = "APPID";

                String mode = "json";
                String units = "metric";
                String myKey = "eea052d407132bde75a5ae66e06cfd3b";


                Uri builderURI = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])           // adds the postal code.
                        .appendQueryParameter(QUERY_MODE, mode)                 // changes the mode to json.
                        .appendQueryParameter(QUERY_UNIT, units)                // specifies the metric OR imperial.
                        .appendQueryParameter(QUERY_DAYS, Integer.toString(7))  // specifies number of days.
                        .appendQueryParameter(API_KEY, myKey)
                        .build();

                URL url = new URL(builderURI.toString());

                Log.v(TAG, "Built URI " + builderURI.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }
}
