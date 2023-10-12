package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;

import java.util.Date;
import java.util.List;

public class MlabOptions extends AppCompatActivity {

    TextView ver;

    int sbColour,tbColour,tlColour,bgColour;
    Toolbar tb;
    NotificationCompat.Builder noti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlab_options);

        getSupportActionBar().setTitle("M-LAB Module");


    }

    public void HTSResults(View v) {

        try {

//            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();


            Intent myint = new Intent(getApplicationContext(), HtsOptions.class);
            startActivity(myint);

        } catch (Exception e) {


        }
    }

    public void TB(View v) {

        try {

            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {


        }
    }




    public void goToClients(View v) {

        try {


//            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();
            Intent myint = new Intent(getApplicationContext(), eidvlOptions.class);
            startActivity(myint);


        } catch (Exception e) {
            Toast.makeText(this, "unable to load Register", Toast.LENGTH_SHORT).show();


        }
    }

    public void historicalResults(View v) {

        try {

            Intent myint = new Intent(getApplicationContext(), eidvlOptions.class);
            startActivity(myint);


        } catch (Exception e) {


        }
    }

    public void setToolBar() {

        try {


        } catch (Exception e) {


        }
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
          //  setCustomColour();
        }


    }


    public void goToResults(View v) {

        try {
//            new LongOperation().execute();

            Intent inty = new Intent(MlabOptions.this, eidvlOptions.class);
            startActivity(inty);

        } catch (Exception e) {
            Toast.makeText(this, "error loading results, try again", Toast.LENGTH_SHORT).show();


        }
    }




    }