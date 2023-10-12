package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;

import com.example.mhealth.appointment_diary.R;

public class Settings extends AppCompatActivity {


    String statusBarColour;
    String toolBarColour;

    Toolbar tb;

    int sbColour, tbColour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tb = (Toolbar) findViewById(R.id.toolbar3);
        tb.setTitleTextColor(Color.parseColor("#f2f2f2"));
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getFragmentManager().beginTransaction().replace(R.id.myfrm, new SettingsFragment()).commit();
        getDefaultSettings();
    }

    private void setCustomColour() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        statusBarColour = sp.getString("status_bar_colours", "#000066");
        toolBarColour = sp.getString("tool_bar_colours", "#3333ff");


        sbColour = Color.parseColor(statusBarColour);
        tbColour = Color.parseColor(toolBarColour);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(sbColour);

        }

        tb.setBackgroundColor(tbColour);
    }


    private void getDefaultSettings() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean ds = sp.getBoolean("default_colour", false);
        if (ds) {
            tb.setBackgroundColor(Color.parseColor("#3333ff"));
            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().setStatusBarColor(Color.parseColor("#000066"));
            }

        } else {
            setCustomColour();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

}