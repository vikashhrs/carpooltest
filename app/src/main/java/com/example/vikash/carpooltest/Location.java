package com.example.vikash.carpooltest;

/**
 * Created by vikash on 31-Jan-17.
 */
public class Location {
    private String locationName;
    private boolean statusChecked;

    public Location(String locationName) {
        this.locationName = locationName;
        this.statusChecked = false;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public boolean isStatusChecked() {
        return statusChecked;
    }

    public void setStatusChecked(boolean statusChecked) {
        this.statusChecked = statusChecked;
    }
}
