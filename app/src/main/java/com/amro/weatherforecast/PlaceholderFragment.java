package com.amro.weatherforecast;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by amro on 3/12/16.
 */
public class PlaceholderFragment extends Fragment
{

    public PlaceholderFragment()
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

        return view;
    }
}
