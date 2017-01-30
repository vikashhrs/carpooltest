package com.example.vikash.carpooltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikash on 31-Jan-17.
 */
public class CustomLocationListViewAdapter extends ArrayAdapter {
    private ArrayList<Location> locations;

    public CustomLocationListViewAdapter(Context context, int resource, ArrayList<Location> locations) {
        super(context, resource, locations);
        this.locations = locations;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.custom_listview_item, parent,false);
        Location location = (Location)getItem(position);
        TextView textView = (TextView)view.findViewById(R.id.locationName);
        textView.setText(location.getLocationName());
        ImageView imageView = (ImageView)view.findViewById(R.id.statusChecked);
        if(location.isStatusChecked()){
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.INVISIBLE);
        }

        return view;

    }
}
