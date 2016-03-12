package com.amro.weatherforecast;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by amro on 3/12/16.
 */
public class PlaceholderFragment extends Fragment
{

    ArrayAdapter<String> adapter;

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
}
