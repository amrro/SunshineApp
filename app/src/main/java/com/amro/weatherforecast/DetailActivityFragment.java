package com.amro.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment
{

    TextView sample;
    public DetailActivityFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        sample = (TextView) rootView.findViewById(R.id.sample);
        String oneDayForecast = getArguments().getString("dayDetails");
        sample.setText(oneDayForecast);
        return rootView;
    }
}
