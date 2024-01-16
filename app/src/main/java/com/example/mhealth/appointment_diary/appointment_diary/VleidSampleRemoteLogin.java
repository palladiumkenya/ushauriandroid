package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class VleidSampleRemoteLogin extends AppCompatActivity {

    MaterialBetterSpinner SpinnerLabs;
    private ArrayAdapter<String> arrayAdapterLabs;
    String selectedLab;
    LinearLayout llvlbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vleid_sample_remote_login);
        // HttpsTrustManager.allowAllSSL();
        setToolBar();
        changeStatusBarColor("#3F51B5");
        initialise();
        setSpinnerAdapters();
        setSpinnerLabsListener();
    }



    private void displayOtherButtons(){

        try{
            if(selectedLab.isEmpty()){

                llvlbtn.setVisibility(View.GONE);

            }
            else {
                llvlbtn.setVisibility(View.VISIBLE);

            }


        }
        catch(Exception e){


        }
    }
    private void setSpinnerLabsListener(){

        try{


            SpinnerLabs.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    selectedLab=SpinnerLabs.getText().toString();
                    displayOtherButtons();
//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }

    private void initialise(){

        try{
            llvlbtn=(LinearLayout) findViewById(R.id.vleid_vlbuttons);
            selectedLab="";
            SpinnerLabs=(MaterialBetterSpinner) findViewById(R.id.vleid_testinglab);
            arrayAdapterLabs = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTLABS);
        }
        catch(Exception e){


        }
    }

    public void setSpinnerAdapters(){

        try{
            SpinnerLabs.setAdapter(arrayAdapterLabs);






        }
        catch(Exception e){


        }
    }

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    public void setToolBar(){

        try{
            getSupportActionBar().setTitle("EID/VL Sample Remote Login");

           /* Toolbar toolbar = (Toolbar) findViewById(R.id.eidvlremotelogintoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EID/VL Sample remote login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        }
        catch(Exception e){


        }
    }



    //button function here

    public void goToEidSamples(View v){
        try{

//            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();

            Intent myint=new Intent(getApplicationContext(), EidSamples.class);
            myint.putExtra("selectedLab", selectedLab);
            startActivity(myint);
        }
        catch(Exception e){}
    }

    public void goToViralLoadSamples(View v){
        try{

            Intent myint=new Intent(getApplicationContext(), ViralLoadSamples.class);
            myint.putExtra("selectedLab", selectedLab);
            startActivity(myint);

//            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){}
    }

    public void goToSampleTransportation(View v){
        try{

//            Snackbar.make(v, "Feature Coming Soon", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();

           /* Intent myint=new Intent(getApplicationContext(), SampleTransportation.class);
            myint.putExtra("selectedLab", selectedLab);
            startActivity(myint);*/

        }
        catch(Exception e){}
    }
    public void goToSampleTransportationStatus(View v){
        try{

//            Snackbar.make(v, "Feature Coming Soon", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
           /* Intent myint=new Intent(getApplicationContext(), SampleTransportationStatus.class);
            myint.putExtra("selectedLab", selectedLab);
            startActivity(myint);*/

        }
        catch(Exception e){}
    }

    public void goToCheckRejectedSamples(View v){
        try{

//            Snackbar.make(v, "Feature Coming Soon", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            Intent myint=new Intent(getApplicationContext(), CheckRejectedSamples.class);
            myint.putExtra("selectedLab", selectedLab);
            startActivity(myint);

        }
        catch(Exception e){}
    }

    public void goToBarcodeReader(View v){
        try{

            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){}
    }

}