package com.example.mhealth.appointment_diary.appointment_diary;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mhealth.appointment_diary.DCMActivity;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.adapter.RequestsAdapter;
import com.example.mhealth.appointment_diary.models.RescheduledRequestsModel;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.Smsretrieverapi.SmsReceiver;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.models.Appointments;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.utilitymodules.Appointment;
import com.example.mhealth.appointment_diary.utilitymodules.Broadcast;
import com.example.mhealth.appointment_diary.utilitymodules.ClientTransfer;
import com.example.mhealth.appointment_diary.utilitymodules.ClinicMovement;
import com.example.mhealth.appointment_diary.utilitymodules.Consent;
import com.example.mhealth.appointment_diary.utilitymodules.Registration;
import com.example.mhealth.appointment_diary.utilitymodules.TransitClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.internal.Intrinsics;

import static com.example.mhealth.appointment_diary.StringSplitter.SplitString.splittedString;

/**
 * Created by abdullahi on 11/12/2017.
 */

public class TodaysAppointment extends AppCompatActivity {

    //messages
    ListView listView;
    ArrayAdapter arrayAdapter;
    EditText input;
    int appointmentCounter;

    RequestsAdapter requestsAdapter;

    CheckInternet chkInternet;
    AccessServer acs;

    String z, dates, phone;
    private AppointmentAdapter myadapt;
    private List<RescheduledRequestsModel> mymesslist = new ArrayList<>();
    private List<RescheduledRequestsModel> requestlist= new ArrayList<>();


    //messages

    Progress progress;

    Button btnRegister, btnReport, bookedappointments, broadcast, transfer, consent,transitCl,moveClinic, todayapp;
    CardView card_register, card_reschedule, card_book, card_consent, card_dsd, card_transfer, card_transit, card_clinic, card_today, card_calender, client_encounter;
    Button missed,honored;
    String passedUname,passedPassword;

    TextView messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaysappointment);

        //messages

        try{

          //  appCounterTxtV=(TextView) findViewById(R.id.appointmentcount);
            chkInternet=new CheckInternet(TodaysAppointment.this);
            appointmentCounter=0;
            acs=new AccessServer(TodaysAppointment.this);
           // fab=(FloatingActionButton) findViewById(R.id.fabtodays);
            passedUname="";
            passedPassword="";
            listView = (ListView)findViewById(R.id.messages);
            //messages = (ListView) findViewById(R.id.messages);
           // input = (EditText) findViewById(R.id.input);

        }
        catch(Exception e){

        }

        postapi();



        //messages
        progress =new Progress(TodaysAppointment.this);

        setToolbar();
        initialise();

        //SSLTrust.nuke();
        setButtonListeners();
        //setCardListeners();

        card_register =(CardView) findViewById(R.id.card_reg);
        card_book= (CardView)   findViewById(R.id.card_bk);
        card_consent= (CardView)  findViewById(R.id.card_consen);
        card_dsd= (CardView)  findViewById(R.id.card_ds);
        card_transfer = (CardView) findViewById(R.id.card_transfe);
        card_transit= (CardView) findViewById(R.id.card_transi);
        card_clinic= (CardView)  findViewById(R.id.card_clin);
        card_today= (CardView) findViewById(R.id.card_todey);
        card_calender= (CardView) findViewById(R.id.card_calender);
        card_reschedule= (CardView) findViewById(R.id.client_encounter);
        messages = (TextView) findViewById(R.id.msg_reschedule);

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RescheduledRequests.class);
                startActivity(intent);

            }
        });

        card_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                intent.putExtra("username",passedUname);
                intent.putExtra("password",passedPassword);
                startActivity(intent);

            }
        });

        card_reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ClientEncounter.class);
                startActivity(intent);


            }
        });


        card_transit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransitClient.class);
                startActivity(intent);

            }
        });
        card_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppCal.class);
                startActivity(intent);

            }
        });


        card_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClinicMovement.class);
                startActivity(intent);
            }
        });


        card_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                Runnable r =new Runnable() {
                    @Override
                    public void run() {
                        progress.showProgress("loading..");
                        Intent intent = new Intent(getApplicationContext(),Appointment.class);
                        startActivity(intent);

                        progress.dissmissProgress();
                    }
                };
                handler.post(r);

                /*Intent intent = new Intent(getApplicationContext(),Appointment.class);
                startActivity(intent);*/
            }
        });

        card_dsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DCMActivity.class);
                startActivity(intent);
            }
        });


        card_consent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Consent.class);
                startActivity(intent);
            }
        });


        card_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientTransfer.class);
                startActivity(intent);
            }
        });

        card_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FetchAppointment.class);
                startActivity(intent);
            }
        });



    }

    public void getPassedData(){

        passedUname=getIntent().getStringExtra("username");
        passedPassword=getIntent().getStringExtra("password");
    }
    public void setButtonListeners(){

        try{

            btnRegister.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Registration.class);
                    intent.putExtra("username",passedUname);
                    intent.putExtra("password",passedPassword);
                    startActivity(intent);
                }
            });

            transitCl.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), TransitClient.class);
                    startActivity(intent);
                }
            });

            moveClinic.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), ClinicMovement.class);
                    startActivity(intent);
                }
            });

            bookedappointments.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(),Appointment.class);
                                startActivity(intent);
                            }
                        }; handler.post(runnable);


                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    /*Intent intent = new Intent(getApplicationContext(),Appointment.class);
                    startActivity(intent);*/
                }
            });

            broadcast.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DCMActivity.class);
                    startActivity(intent);
                }
            });

            consent.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Consent.class);
                    startActivity(intent);
                }
            });

            transfer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ClientTransfer.class);
                    startActivity(intent);
                }
            });

            todayapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Handler handler = new Handler();
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(),FetchAppointment.class);
                                startActivity(intent);
                            }
                        }; handler.post(runnable);


                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }


                   /* Intent intent = new Intent(getApplicationContext(), FetchAppointment.class);
                    startActivity(intent);*/
                }
            });

        }
        catch(Exception e){


        }
    }
    //card listeners

    //end card listeners


    public void setToolbar(){
        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Appointment diary");

        }
        catch(Exception e){


        }
    }

    public void initialise(){

        try{

            //appCounterTxtV=(TextView) findViewById(R.id.appointmentcount);

            //fab=(FloatingActionButton) findViewById(R.id.fabtodays);


            passedUname="";
            passedPassword="";

            btnRegister = (Button)findViewById(R.id.btnRegister);
            bookedappointments = (Button)findViewById(R.id.bookedappointments);
            transitCl = (Button)findViewById(R.id.transit1);
            moveClinic = (Button)findViewById(R.id.moveclinic);
            broadcast = (Button)findViewById(R.id.broadcast);
            consent = (Button)findViewById(R.id.consent);
            transfer = findViewById(R.id.transfer);
            todayapp = findViewById(R.id.appointments1);



        }
        catch(Exception e){

        }
    }

    //messages
    public  void postapi() {

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        try {
            List<Activelogin> al=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
            for(int x=0;x<al.size();x++) {
                String myuname = al.get(x).getUname();
                List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
                for (int y = 0; y < myl.size(); y++) {

                    phone = myl.get(y).getPhone();

                }
            }

            jsonObject.put("phone_no", phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonArray.put(jsonObject);
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        String urls ="?telephone="+phone;

        AndroidNetworking.get(z+Config.Reschedule_LIST+urls)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Connection","keep-alive")
                .addHeaders("Accept", "application/json")

                .setPriority(Priority.MEDIUM)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("SERVER RESPONSE", response.toString());
                        Log.d("total number", String.valueOf(response.length()));



                        if (response.length()>0){
                            messages.setVisibility(View.VISIBLE);
                        }
                        messages.setText(String.valueOf(response.length()));

                        String test =response.toString();

                        if (test==null){
                            Toast.makeText(TodaysAppointment.this, "success", Toast.LENGTH_SHORT).show();

                        }

                        //Toast.makeText(UPIErrorList.this, "success"+response, Toast.LENGTH_SHORT).show();
                        requestlist= new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {





                            try {
                                JSONObject item = (JSONObject) response.get(i);


                                // JSONObject jsonObject = jsonArray1.getJSONObject(i);

                                String  client_name1 = item.getString("client_name");
                                String clinic_no1 =item.getString("clinic_no");
                                String client_phone_no1 = item.getString("client_phone_no");
                                String appntmnt_date1= item.getString("appntmnt_date");
                                String reschedule_date1 = item.getString("reschedule_date");
                                String reason1= item.getString("reason");
                                int clinic_id1= item.getInt("clinic_id");
                                String appointment_type= item.getString("appointment_type");

                                int appointment_id= item.getInt("appointment_id");
                                int reschedule_id= item.getInt("reschedule_id");




                                requestsAdapter =new RequestsAdapter(TodaysAppointment.this, requestlist);



                                RescheduledRequestsModel rescheduledRequestsModel= new RescheduledRequestsModel(clinic_id1, clinic_no1, client_name1, client_phone_no1, appntmnt_date1, reschedule_date1, reason1, appointment_type, appointment_id, reschedule_id);
                                //upilist=new ArrayList<>();
                                requestlist.add(rescheduledRequestsModel);

                                listView.setAdapter(requestsAdapter);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }}



                    }




                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(TodaysAppointment.this, anError.getMessage(), Toast.LENGTH_LONG).show();

                        Log.d("", anError.getMessage());

                    }
                });








    }



}
