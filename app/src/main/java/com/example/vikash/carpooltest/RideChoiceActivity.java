package com.example.vikash.carpooltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class RideChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    Credentials credentials = null;
    private Button getRide,
                   shareRide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ride_choice_activity);
        credentials = (Credentials) getIntent().getBundleExtra("data").getSerializable("credentials");
        getRide = (Button)findViewById(R.id.getRide);
        getRide.setOnClickListener(this);
        shareRide =(Button)findViewById(R.id.shareRide);
        shareRide.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.getRide){
            
        }
        if(id == R.id.shareRide){

        }
    }
}
