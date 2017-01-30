package com.example.vikash.carpooltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class RideSetupActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView listView;
    ArrayList<Location> locations;
    CustomLocationListViewAdapter customLocationListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_ride_share);
        listView = (ListView)findViewById(R.id.locationListView);
        locations = getLocations();
        customLocationListViewAdapter = new CustomLocationListViewAdapter(getApplicationContext(),R.layout.custom_listview_item,locations);
        listView.setAdapter(customLocationListViewAdapter);

    }

    private ArrayList<Location> getLocations(){
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Karachi Bakery"));
        locations.add(new Location("Cyber Towers"));
        locations.add(new Location("Golconda Ford"));
        locations.add(new Location("Cream Stone"));
        locations.add(new Location("Snow World"));
        locations.add(new Location("Punjabi Rasoi"));
        locations.add(new Location("Kolkata House"));
        return locations;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Location location = (Location)adapterView.getItemAtPosition(i);
        location.setStatusChecked(true);
        updateLocationList(location);
    }
    private void updateLocationList(Location location){
        for(Location lc : locations){
            if(location.getLocationName().matches(lc.getLocationName())){
                locations.remove(lc);
                break;
            }
        }
        locations.add(location);
        customLocationListViewAdapter.notifyDataSetChanged();
    }
}
