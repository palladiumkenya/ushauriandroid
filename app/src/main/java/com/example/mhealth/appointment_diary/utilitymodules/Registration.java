package com.example.mhealth.appointment_diary.utilitymodules;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.AppendFunction.AppendFunction;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Mydates.MyDates;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.models.RegisterCounter;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Clientartdate;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.tables.Myaffiliation;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.facebook.stetho.Stetho;
import com.google.android.material.snackbar.Snackbar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by DELL on 12/11/2015.
 */
public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RequestQueue rq;
    ArrayList<String> servicesList;
    ArrayList<Service> services;

    ArrayList<String> RankList;
    ArrayList<Ranks> ranks;

    /*ArrayList<String> serviceUnitsList;
    ArrayList<ServicesUnit> serviceUnits;*/

    ArrayList<String> serviceUnitsList;
    ArrayList<Unit> serviceUnits;
public static final String TAG ="Registration";

    private int serviceID = 0;
    private int serviceUnitID = 0;

    private int ranksID = 0;
    String ranksValue= "";

    String serviceValue= "";
    String unitValue="";

    //int serviceID;
    //int serviceUnitID;



    SearchableSpinner serviceSpin, rankSpin,unitSpin;

    ArrayList<String>servicesArray;
    ArrayAdapter<String> AA;

    ArrayList<String> ArmyList, AirwaysList, NavyList;
    ArrayAdapter<String> AAList;




    SearchableSpinner ServiceSpinner, serviceUnitSpinner;

    LinearLayout smslayoutL, idnoL, orphanL, altphoneL, disableL, groupingL;

    EditText cccE, upnE,  f_nameE, s_nameE, o_nameE, dobE, enrollment_dateE, art_dateE, phoneE, email_address, idnoE, altphoneE, ageinyearsE, ServiceNo;
    //fileserialE
    Spinner genderS, maritalS, conditionS, enrollmentS, languageS, smsS, wklymotivation, messageTime, SelectstatusS, patientStatus, GroupingS, orphanS, schoolS, newGroupingS;

    String gender_code, marital_code, condition_code, grouping_code, new_grouping_code, category_code, language_code, sms_code, Selectstatus_code, wklyMotivation_code, messageTime_code, patientStatus_code, school_code, orphan_code, idnoS;


    DatePickerDialog datePickerDialog;
    CheckInternet chkinternet;
    AccessServer acs;
    SendMessage sm;

    String[] genders = {"Please Select Gender", "Female", "Male"};
    //String[] service = {"Please Select Service", "Kenya Army", "Kenya Air Force", "Kenya Navy"};

    String[] newgroupings = {"Please Select Grouping", "Adolescent", "PMTCT", "TB", "Adults", "Peads", "TB-HIV", "HEI"};
    String[] gendersUcsf = {"Please Select Sex", "Female", "Male"};
    String[] maritals = {"Please Select Marital Status", "Single", "Married Monogomaus", "Married Polygamous", "Divorced", "Widowed", "Cohabiting", "Not Applicable"};
    String[] maritalsInfants = {"Please Select Marital Status", "Single", "Not Applicable"};
    String[] conditions = {"Please Select Condition", "ART", "Pre-Art"};
    String[] groups = {"Please Select Grouping", "peads", "adolescents", "PMTCT", "ART", "High VL (Suppressed & non suppressant)"};
    String[] languages = {"Please Select Language", "Swahili", "English"};
    String[] smss = {"Enable Sms", "Yes", "No"};
    String[] orphanOp = {"Are you an Orphan", "Yes", "No"};
    String[] schoolOp = {"Are you in school", "Yes", "No"};
    String[] weeklymotivation = {"Enable weekly motivation alerts", "Yes", "No"};
    String[] msgtime = {"Please select preffered messaging time", "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "22:00", "23:00"};
    String[] pntstatus = {"Please select transaction type", "New", "Update", "Transfer Client In (This module allows transfer in of a client from a facility without ushauri system)"};
    String[] statuss = {"Please Select Status", "Active", "Disabled", "Deceased", "Transfer Out"};
    String[] statussnew = {"Please Select Status", "Active"};


    String counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        rq = Volley.newRequestQueue(Registration.this);
        //serviceSpin = findViewById(R.id.services);
        //unitSpin = findViewById(R.id.unit);
        //service and unit array
        //getServicesUnits();




        ServiceSpinner = findViewById(R.id.ServiceSpinner);
        serviceUnitSpinner = findViewById(R.id.serviceUnit);
        rankSpin = findViewById(R.id.RankSpinner);
        rankSpin.setTitle("Select Rank");
        rankSpin.setPositiveButton("OK");


       ServiceSpinner.setTitle("Select Service");
        ServiceSpinner.setPositiveButton("OK");
        serviceUnitSpinner.setTitle("Select Unit");
        serviceUnitSpinner.setPositiveButton("OK");


        // components from activity_registration.xml

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Registration");


        initialise();
        populateSerialNumber();
        //SSLTrust.nuke();
        displayAlternativeNumber();

//        sendEncrypted();

//        Toast.makeText(this, ""+sha256("Reg*12345*ken*sit*nav*15/6/1991*1*3*1*13/6/2017*0713559850*1*2*2"), Toast.LENGTH_LONG).show();

        DobdateListener();
        ArtdateListener();
        EnrollmentdateListener();

//        populateCategory();
        populateCondition();
        populateGender();
        populateLanguage();

        populateNewGrouping();
        populateSms();
//        populateStatus();
        populateweekly();
        populatemsg();
        populatepntstatus();
        populategrouping();

        setSpinnerListeners();
        setPrompts();
        getFacilities();
        getRanks();



        final Context gratitude = this;
        final Button btnRSubmit = (Button) findViewById(R.id.btnRSubmit);
        btnRSubmit.setEnabled(true);

        Stetho.initializeWithDefaults(this);


    }



    private void populateMflCode() {

        try {

            List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");
            String mflcode = "";

            for (int x = 0; x < myl.size(); x++) {

                mflcode = myl.get(x).getMfl();

            }
            cccE.setText(mflcode);

        } catch (Exception e) {

            Toast.makeText(this, "error occured populating mflcode", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateSerialNumber() {

        try {

            upnE.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                   // fileserialE.setText(upnE.getText().toString());

                }
            });
        } catch (Exception e) {

            Toast.makeText(this, "error setting serial number", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayAlternativeNumber() {
        try {

            if (isUcsf()) {

                altphoneL.setVisibility(View.VISIBLE);
            } else {

                altphoneL.setVisibility(View.GONE);
            }
        } catch (Exception e) {


        }
    }


    public boolean isUcsf() {

        boolean result = false;
        String myaff = "";
        List<Myaffiliation> mya = Myaffiliation.findWithQuery(Myaffiliation.class, "select * from Myaffiliation limit 1");
        for (int x = 0; x < mya.size(); x++) {
            myaff = mya.get(x).getAff();
        }
        try {
            if (myaff.contains("UCSF")) {
                result = true;
            } else {
                result = false;
            }
            return result;
        } catch (Exception e) {
            result = false;
            return result;
        }
    }

    public void setPrompts() {

        try {
            SelectstatusS.setPrompt("select item");
        } catch (Exception e) {


        }
    }

//    public void sendEncrypted(){
//
//
//        SmsManager sm = SmsManager.getDefault();
//        sm.sendTextMessage("40146", null, sha256("Reg*12345*ken*sit*nav*15/6/1991*1*3*1*13/6/2017*0713559850*1*2*2"), null, null);
//    }


    public void setSpinnerListeners() {

        try {


            SelectstatusS.setOnItemSelectedListener(this);
            genderS.setOnItemSelectedListener(this);
            maritalS.setOnItemSelectedListener(this);
            conditionS.setOnItemSelectedListener(this);
//            categoryS.setOnItemSelectedListener(this);
            languageS.setOnItemSelectedListener(this);
            smsS.setOnItemSelectedListener(this);
            wklymotivation.setOnItemSelectedListener(this);
            messageTime.setOnItemSelectedListener(this);
            patientStatus.setOnItemSelectedListener(this);
//            GroupingS.setOnItemSelectedListener(this);
            newGroupingS.setOnItemSelectedListener(this);

        } catch (Exception e) {

        }
    }

    public void setOrphanSpinners() {

        try {
            orphanS.setOnItemSelectedListener(this);
            schoolS.setOnItemSelectedListener(this);
        } catch (Exception e) {


        }
    }


    public void initialise() {

        try {
            ageinyearsE = (EditText) findViewById(R.id.ageinyears);
            sm = new SendMessage(Registration.this);
            acs = new AccessServer(Registration.this);
            chkinternet = new CheckInternet(Registration.this);
            //idnoE = (EditText) findViewById(R.id.idno);
            ServiceNo = (EditText) findViewById(R.id.idno);
            idnoS = "";


            email_address = (EditText) findViewById(R.id.Email);
            altphoneE = (EditText) findViewById(R.id.altphone);
            disableL = (LinearLayout) findViewById(R.id.disablell);

            altphoneL = (LinearLayout) findViewById(R.id.altphonell);
            orphanL = (LinearLayout) findViewById(R.id.orphanll);
            idnoL = (LinearLayout) findViewById(R.id.idnoll);
            // fileserialE = (EditText) findViewById(R.id.cfile);
            cccE = (EditText) findViewById(R.id.ccc);
            upnE = (EditText) findViewById(R.id.upn);
            smslayoutL = (LinearLayout) findViewById(R.id.smslayout);
            groupingL = (LinearLayout) findViewById(R.id.groupingll);
            f_nameE = (EditText) findViewById(R.id.f_name);
            s_nameE = (EditText) findViewById(R.id.s_name);
            o_nameE = (EditText) findViewById(R.id.o_name);
            dobE = (EditText) findViewById(R.id.dob);
            enrollment_dateE = (EditText) findViewById(R.id.enrollment_date);
            art_dateE = (EditText) findViewById(R.id.art_date);
            phoneE = (EditText) findViewById(R.id.phone);

            //ServiceNo = findViewById()


            genderS = (Spinner) findViewById(R.id.gender_spinner);
            schoolS = (Spinner) findViewById(R.id.school_spinner);
            orphanS = (Spinner) findViewById(R.id.orphan_spinner);
            newGroupingS = (Spinner) findViewById(R.id.grouping_spinner);

            maritalS = (Spinner) findViewById(R.id.marital_spinner);
            conditionS = (Spinner) findViewById(R.id.condition_spinner);
//           categoryS=(Spinner) findViewById(R.id.category_spinner);
            languageS = (Spinner) findViewById(R.id.language_spinner);
            smsS = (Spinner) findViewById(R.id.sms_spinner);
            SelectstatusS = (Spinner) findViewById(R.id.status_spinner);
            wklymotivation = (Spinner) findViewById(R.id.weekly_spinner);
            messageTime = (Spinner) findViewById(R.id.time_spinner);
            patientStatus = (Spinner) findViewById(R.id.Patientstatus_spinner);



            //serviceS =(Spinner) findViewById(R.id.Service);
            //unitS =(Spinner) findViewById(R.id.serviceUnit);


//           GroupingS=(Spinner) findViewById(R.id.grouping_spinner);


            gender_code = "";
            grouping_code = "";
            new_grouping_code = "";
            marital_code = "";
            condition_code = "";
            category_code = "";
            language_code = "";
            sms_code = "";
            Selectstatus_code = "";
            wklyMotivation_code = "";
            messageTime_code = "";
            patientStatus_code = "";
            orphan_code = "";
            school_code = "";

           /* locatorcountyS = "-1";
            locatorsubcountyS = "-1";
            locatorlocationS = "-1";
            locatorwardS = "-1";
            locatorvillageS = "-1";*/
//           grouping_code="";
        } catch (Exception e) {

            Toast.makeText(this, "error initialising variables", Toast.LENGTH_SHORT).show();


        }
    }

    public void clearFields() {

        try {

            cccE.setText("");
            upnE.setText("");
            f_nameE.setText("");
            s_nameE.setText("");
            o_nameE.setText("");
            dobE.setText("");
            enrollment_dateE.setText("");
            art_dateE.setText("");
            phoneE.setText("");
            idnoE.setText("");
            ServiceNo.setText("");

           // email_address.setText("");
            if (altphoneE.isShown()) {
                altphoneE.setText("");
            }

            //email_address.setText("");
            //fileserialE.setText("");


            populateCondition();
            populateGender();
            populateLanguage();

            populateNewGrouping();
            populateSms();
//           populateStatus();
            populateweekly();
            populatemsg();
            populatepntstatus();
            populategrouping();
            //getFacilities();

            gender_code = "";
            marital_code = "";
            condition_code = "";
            category_code = "";
            language_code = "";
            sms_code = "";
            Selectstatus_code = "";
            wklyMotivation_code = "";
            messageTime_code = "";
            patientStatus_code = "";
            orphan_code = "";
            school_code = "";


        } catch (Exception e) {


        }
    }


    public void EnrollmentdateListener() {

        try {

            enrollment_dateE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Registration.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    enrollment_dateE.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
           });
        } catch (Exception e) {


        }
    }

    public void ArtdateListener() {

        try {

            art_dateE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Registration.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    art_dateE.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();                }
            });
        } catch (Exception e) {


        }
    }


    public void DobdateListener() {

        try {

            dobE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // calender class's instance and get current date , month and year from calender
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR); // current year
                    int mMonth = c.get(Calendar.MONTH); // current month
                    int mDay = c.get(Calendar.DAY_OF_MONTH); // current day


                    // date picker dialog
                    datePickerDialog = new DatePickerDialog(Registration.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                   dobE.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                    int mycurrentYear = Integer.parseInt(MyDates.getCurrentYear());
                                    int difference = mycurrentYear - year;

                                   ageinyearsE.setText(difference + " Years");

                                    /*if (difference >= 18) {
                                        idnoL.setVisibility(View.VISIBLE);
                                        if (isUcsf()) {
                                            orphanL.setVisibility(View.GONE);
                                        }


                                    }
                                    else {

                                        idnoL.setVisibility(View.GONE);
                                        if (isUcsf()) {

                                            orphanL.setVisibility(View.VISIBLE);
                                            setOrphanSpinners();
                                            populateOrphan();
                                            populateSchool();

                                        }


                                    }*/

                                    if (difference <= 12) {


                                        populateMaritalInfant();

                                    } else {

                                        populateMarital();

                                    }

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin = (Spinner) parent;

        if (spin.getId() == R.id.gender_spinner) {


            gender_code = Integer.toString(position);

        }
        if (spin.getId() == R.id.marital_spinner) {

            marital_code = Integer.toString(position);

        }
        if (spin.getId() == R.id.condition_spinner) {

            condition_code = Integer.toString(position);

            if (position == 2) {

                art_dateE.setVisibility(View.GONE);

            } else {

                art_dateE.setVisibility(View.VISIBLE);
            }


        }
//        if (spin.getId()==R.id.category_spinner){
//
//            category_code=Integer.toString(position+1);
//
//
//        }

        if (spin.getId() == R.id.language_spinner) {

            language_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.grouping_spinner) {

            new_grouping_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.sms_spinner) {

            sms_code = Integer.toString(position);
//            Toast.makeText(this, ""+sms_code, Toast.LENGTH_SHORT).show();


            if (sms_code.equalsIgnoreCase("1")) {
                smslayoutL.setVisibility(View.VISIBLE);

            } else if (sms_code.equalsIgnoreCase("2")) {

                smslayoutL.setVisibility(View.GONE);

            }


        }

        if (spin.getId() == R.id.status_spinner) {

            Selectstatus_code = Integer.toString(position);
            if (Selectstatus_code.contentEquals("2")) {

                disableL.setVisibility(View.VISIBLE);

            } else {

                disableL.setVisibility(View.GONE);
            }


        }
        if (spin.getId() == R.id.school_spinner) {

            school_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.orphan_spinner) {

            orphan_code = Integer.toString(position);


        }
        if (spin.getId() == R.id.weekly_spinner) {

            wklyMotivation_code = Integer.toString(position);


        }
        if (spin.getId() == R.id.time_spinner) {

            messageTime_code = Integer.toString(position);


        }

        if (spin.getId() == R.id.Patientstatus_spinner) {

            patientStatus_code = Integer.toString(position);

            if (patientStatus_code.contentEquals("1")) {
                populateStatusNew();
                cccE.setEnabled(false);
                //upnE.setEnabled(false);
                populateMflCode();


            } else {
                cccE.setEnabled(true);
                //upnE.setEnabled(true);
                cccE.setText("");
                populateStatus();

            }


        }

        if (spin.getId() == R.id.orphan_spinner) {

            orphan_code = Integer.toString(position);


        }


        if (spin.getId() == R.id.school_spinner) {

            school_code = Integer.toString(position);


        }


//        if (spin.getId()==R.id.grouping_spinner){
//
//            grouping_code=Integer.toString(position);
//
//
//        }


    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void actOnSelected() {

//        Toast.makeText(this, "you selected "+selected_item+"the behind scene value is "+myselected, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "you selected "+selected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }

    public void populategrouping() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), groups);

            GroupingS.setAdapter(customAdapter);


        } catch (Exception ignored) {


        }
    }


    public void populateGender() {


        try {

            if (isUcsf()) {
                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), gendersUcsf);

                genderS.setAdapter(customAdapter);

            } else {

                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), genders);

                genderS.setAdapter(customAdapter);

            }


        } catch (Exception e) {


        }
    }

    private boolean displayGrouping() {

        boolean result = false;

        if (isUcsf()) {
            groupingL.setVisibility(View.GONE);
            result = false;
        } else {

            groupingL.setVisibility(View.VISIBLE);
            result = true;
        }
        return result;
    }


    public void populateNewGrouping() {


        try {

            if (displayGrouping()) {

                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), newgroupings);


                newGroupingS.setAdapter(customAdapter);


            } else {


            }


        } catch (Exception e) {


        }
    }


    public void populateMarital() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), maritals);


            maritalS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateMaritalInfant() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), maritalsInfants);


            maritalS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void populateCondition() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), conditions);

            conditionS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateSchool() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), schoolOp);

            schoolS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateOrphan() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), orphanOp);

            orphanS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


//    public void populateCategory(){
//        List<String> mydata=new ArrayList<>();
//        mydata.add("Adults");
//        mydata.add("Adolescents");
//
//        try{
//
//            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mydata);
//
//            // Drop down layout style - list view with radio button
//            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            categoryS.setAdapter(dataAdapter);
//
//
//        }
//
//        catch(Exception e){
//
//
//        }
//    }


    public void populateLanguage() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), languages);


            languageS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateSms() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), smss);


            smsS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void populateStatus() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), statuss);


            SelectstatusS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateStatusNew() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), statussnew);


            SelectstatusS.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populateweekly() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), weeklymotivation);


            wklymotivation.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    public void populatemsg() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), msgtime);


            messageTime.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }


    public void populatepntstatus() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), pntstatus);


            patientStatus.setAdapter(customAdapter);


        } catch (Exception e) {


        }

    }


    public void submitClicked(View v) {

        try {

            String cccS = cccE.getText().toString();
            //String fileserialS = fileserialE.getText().toString();
            String upnS = upnE.getText().toString();
            String f_nameS = f_nameE.getText().toString();
            String s_nameS = s_nameE.getText().toString();
            String o_nameS = o_nameE.getText().toString();
            String dobS = dobE.getText().toString();
            String enrollmentS = enrollment_dateE.getText().toString();
            String art_dateS = art_dateE.getText().toString();
            String phoneS = phoneE.getText().toString();
            String emailS = email_address.getText().toString();
            String altphoneNumber = "-1";
            String buddyphoneNumber = "-1";
            String idNumber = "-1";
           // String Service_No = idnoE.getText().toString();
            String Service_No = ServiceNo.getText().toString();

            String[] dobArray = new String[]{};
            String[] enrollmentArray = new String[]{};
            String[] artArray = new String[]{};

            String dobYear = "";
            String dobMnth = "";
            String dobDay = "";

            int dobYearv = -1;
            int dobMnthv = -1;
            int dobDayv = -1;

            String artYear = "";
            String artMnth = "";
            String artDay = "";

            int artYearv = -1;
            int artMnthv = -1;
            int artDayv = -1;

            String enrollYear = "";
            String enrollMnth = "";
            String enrollDay = "";

            int enrollYearv = -1;
            int enrollMnthv = -1;
            int enrollDayv = -1;


            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String currentArray[] = timeStamp.split("\\.");
            String currentDate = currentArray[2];
            String currentMonth = currentArray[1];
            String currentYear = currentArray[0];

            int cdate = Integer.parseInt(currentDate);
            int cmnth = Integer.parseInt(currentMonth);
            int cyear = Integer.parseInt(currentYear);

            if (!dobS.isEmpty()) {
                dobArray = dobS.split("/");
                dobYear = dobArray[2];
                dobMnth = dobArray[1];
                dobDay = dobArray[0];

                dobYearv = Integer.parseInt(dobYear);
                dobMnthv = Integer.parseInt(dobMnth);
                dobDayv = Integer.parseInt(dobDay);

            }
            if (!enrollmentS.isEmpty()) {
                enrollmentArray = enrollmentS.split("/");

                enrollYear = enrollmentArray[2];
                enrollMnth = enrollmentArray[1];
                enrollDay = enrollmentArray[0];

                enrollYearv = Integer.parseInt(enrollYear);
                enrollMnthv = Integer.parseInt(enrollMnth);
                enrollDayv = Integer.parseInt(enrollDay);

            }
            if (!art_dateS.isEmpty()) {

                artArray = art_dateS.split("/");

                artYear = artArray[2];
                artMnth = artArray[1];
                artDay = artArray[0];

                artYearv = Integer.parseInt(artYear);
                artMnthv = Integer.parseInt(artMnth);
                artDayv = Integer.parseInt(artDay);
            }


//            get the dob years


//            get the art years


//            get the art years


            if (patientStatus_code.contentEquals("0")) {

                Toast.makeText(this, "Please Select transaction type", Toast.LENGTH_SHORT).show();


            } else if (cccS.trim().isEmpty()) {

                cccE.setError("mfl code is required");

            }


            /*else if (upnS.trim().isEmpty()) {

                upnE.setError("KDOD No. required");

            }*/
//           else if(fileserialS.trim().isEmpty() && !patientStatus_code.contentEquals("2")){
//
//                fileserialE.setError("file serial number is required");
//           }

            else if (f_nameS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {

                f_nameE.setError("First name required");

            } else if (s_nameS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                s_nameE.setError("Second name required");
            } else if (dobS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                dobE.setError("Date of Birth required");
            }
//            else if(idnoL.isShown() && idnoE.getText().toString().trim().isEmpty()){
//                idnoE.setError("ID Number is required");
//           }
            else if (enrollmentS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                enrollment_dateE.setError("Enrollment date required");
            } else if (art_dateE.isShown() && art_dateS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                art_dateE.setError("ART date required");
            } else if (phoneS.trim().isEmpty() && !patientStatus_code.contentEquals("2")) {
                phoneE.setError("Phone required");
            } else if ((!dobS.trim().isEmpty()) && dobYearv > cyear) {

                dobE.setError("Date of Birth should be less than today");
                Toast.makeText(this, "Date of Birth should be less than today", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && dobYearv == cyear && dobMnthv > cmnth) {

                dobE.setError("Date of Birth should be less than today");
                Toast.makeText(this, "Date of Birth should be less than today", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && dobYearv == cyear && dobMnthv == cmnth && dobDayv >= cdate) {

                dobE.setError("Date of Birth should be less than today");
                Toast.makeText(this, "Date of Birth should be less than today", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && (!enrollmentS.trim().isEmpty()) && dobYearv > enrollYearv) {

                dobE.setError("Date of Birth should be less than enroll date");
                Toast.makeText(this, "Date of Birth should be less than enroll date", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && (!enrollmentS.trim().isEmpty()) && dobYearv == enrollYearv && dobMnthv > enrollMnthv) {

                dobE.setError("Date of Birth should be less than enroll date");
                Toast.makeText(this, "Date of Birth should be less than enroll date", Toast.LENGTH_SHORT).show();

            } else if ((!dobS.trim().isEmpty()) && (!enrollmentS.trim().isEmpty()) && dobYearv == enrollYearv && dobMnthv == enrollMnthv && dobDayv >= enrollDayv) {

                dobE.setError("Date of Birth should be less than enroll date");
                Toast.makeText(this, "Date of Birth should be less than enroll date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!dobS.trim().isEmpty()) && (!art_dateS.trim().isEmpty()) && dobYearv > artYearv) {

                dobE.setError("Date of Birth should be less than ART date");
                Toast.makeText(this, "Date of Birth should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!dobS.trim().isEmpty()) && (!art_dateS.trim().isEmpty()) && dobYearv == artYearv && dobMnthv > artMnthv) {

                dobE.setError("Date of Birth should be less than ART date");
                Toast.makeText(this, "Date of Birth should be less than  ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!dobS.trim().isEmpty()) && (!art_dateS.trim().isEmpty()) && dobYearv == artYearv && dobMnthv == artMnthv && dobDayv >= artDayv) {

                dobE.setError("Date of Birth should be less than ART date");
                Toast.makeText(this, "Date of Birth should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!art_dateS.trim().isEmpty()) && artYearv > cyear) {

                art_dateE.setError("ART date should not be greater than today");
                Toast.makeText(this, "ART date should not be greater than today", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!art_dateS.trim().isEmpty()) && artYearv == cyear && artMnthv > cmnth) {
                art_dateE.setError("ART date should not be greater than today");
                Toast.makeText(this, "ART date should not be greater than today", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!art_dateS.trim().isEmpty()) && artYearv == cyear && artMnthv == cmnth && artDayv > cdate) {

                art_dateE.setError("ART date should not be greater than today");
                Toast.makeText(this, "ART date should not be greater than today", Toast.LENGTH_SHORT).show();

            }

//            else if(enrollYearv>cyear){
//
//                enrollment_dateE.setError("Enrollment date should be less than today");
//                Toast.makeText(this, "Enrollment date should be less than today", Toast.LENGTH_SHORT).show();
//
//            }
//            else if(enrollYearv==cyear&&enrollMnthv>cmnth){
//                enrollment_dateE.setError("Enrollment date should be less than today");
//                Toast.makeText(this, "Enrollment date should be less than today", Toast.LENGTH_SHORT).show();
//
//            }
//
//            else if(enrollYearv==cyear&&enrollMnthv==cmnth && enrollDayv>=cdate){
//
//                enrollment_dateE.setError("Enrollment date should be less than today");
//                Toast.makeText(this, "Enrollment date should be less than today", Toast.LENGTH_SHORT).show();
//
//            }

            else if (art_dateE.isShown() && (!enrollmentS.trim().isEmpty()) && enrollYearv > artYearv) {

                enrollment_dateE.setError("Enrollment date should be less than ART date");
                Toast.makeText(this, "Enrollment date should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!enrollmentS.trim().isEmpty()) && enrollYearv == artYearv && enrollMnthv > artMnthv) {
                enrollment_dateE.setError("Enrollment date should be less than ART date");
                Toast.makeText(this, "Enrollment date should be less than ART date", Toast.LENGTH_SHORT).show();

            } else if (art_dateE.isShown() && (!enrollmentS.trim().isEmpty()) && enrollYearv == artYearv && enrollMnthv == artMnthv && enrollDayv > artDayv) {

                enrollment_dateE.setError("Enrollment date should be less than or equal to ART date");
                Toast.makeText(this, "Enrollment date should be less than or equal to ART date", Toast.LENGTH_SHORT).show();

            } else if (gender_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();


            } else if (marital_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Marital Status", Toast.LENGTH_SHORT).show();


            } else if (condition_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {

                Toast.makeText(this, "Please Select Condition", Toast.LENGTH_SHORT).show();


            }
//            else if(grouping_code.contentEquals("0")){
//                Toast.makeText(this, "Please Select Grouping", Toast.LENGTH_SHORT).show();
//
//
//            }
            else if (smslayoutL.isShown() && language_code.contentEquals("0")) {

                Toast.makeText(this, "Please Select Language", Toast.LENGTH_SHORT).show();


            } else if (sms_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Sms Option", Toast.LENGTH_SHORT).show();


            } else if (sms_code.contentEquals("1") && wklyMotivation_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select motivational code", Toast.LENGTH_SHORT).show();


            } else if (Selectstatus_code.contentEquals("0") && !patientStatus_code.contentEquals("2")) {
                Toast.makeText(this, "Please Select Status", Toast.LENGTH_SHORT).show();


            } else {

                if (sms_code.contentEquals("0") || sms_code.contentEquals("2")) {

                    sms_code = "-1";
                    wklyMotivation_code = "-1";
                }
               /* if (idnoE.isShown() && !idnoE.getText().toString().trim().isEmpty()) {
                    idnoS = idnoE.getText().toString();
                } else {

                    idnoS = "-1";
                }*/

                //*******check empty values and set them to -1***

                /* Encrypt */
                if (cccS.trim().isEmpty()) {
                    cccS = "-1";
                }
                if (f_nameS.trim().isEmpty()) {
                    f_nameS = "-1";
                }
                if (s_nameS.trim().isEmpty()) {

                    s_nameS = "-1";
                }
                if (o_nameS.trim().isEmpty()) {

                    o_nameS = "-1";
                }
                if (dobS.trim().isEmpty()) {

                    dobS = "-1";
                }
                if (gender_code.trim().isEmpty()) {

                    gender_code = "-1";
                }
                if (gender_code.contentEquals("0")) {

                    gender_code = "-1";
                }
                if (marital_code.trim().isEmpty()) {
                    marital_code = "-1";
                }
                if (marital_code.contentEquals("0")) {
                    marital_code = "-1";
                }
                if (condition_code.trim().isEmpty()) {
                    condition_code = "-1";
                }
                if (condition_code.contentEquals("0")) {
                    condition_code = "-1";
                }

                if (enrollmentS.trim().isEmpty()) {
                    enrollmentS = "-1";
                }
                if (art_dateS.trim().isEmpty()) {
                    art_dateS = "-1";
                }
                if (phoneS.trim().isEmpty()) {
                    phoneS = "-1";
                }
                if (language_code.trim().isEmpty()) {
                    language_code = "-1";
                }
                if (language_code.contentEquals("0")) {
                    language_code = "-1";
                }
                if (sms_code.trim().isEmpty()) {
                    sms_code = "-1";
                }
                if (sms_code.contentEquals("0")) {
                    sms_code = "-1";
                }
                if (wklyMotivation_code.trim().isEmpty()) {
                    wklyMotivation_code = "-1";
                }
                if (wklyMotivation_code.contentEquals("0")) {
                    wklyMotivation_code = "-1";
                }
                if (messageTime_code.trim().isEmpty()) {
                    messageTime_code = "-1";
                }

                if (messageTime_code.contentEquals("0")) {

                    messageTime_code = "-1";
                }
                if (Selectstatus_code.trim().isEmpty()) {
                    Selectstatus_code = "-1";
                }
                if (Selectstatus_code.contentEquals("0")) {
                    Selectstatus_code = "-1";
                }
                if (patientStatus_code.trim().isEmpty()) {
                    patientStatus_code = "-1";
                }
                if (patientStatus_code.contentEquals("0")) {

                    patientStatus_code = "-1";
                }
                if (altphoneE.isShown() && !altphoneE.getText().toString().isEmpty()) {

                    altphoneNumber = altphoneE.getText().toString();
                } else {

                    altphoneNumber = "-1";
                }

                /*if (email_address.isShown() && !email_address.getText().toString().isEmpty()) {

                    buddyphoneNumber = altphoneE.getText().toString();
                } else {

                    buddyphoneNumber = "-1";
                }*/

                if (!groupingL.isShown()) {

                    new_grouping_code = "-1";


                }

                if (!art_dateE.isShown()) {

                    art_dateS = "-1";

                }

//*******check empty values and set them to -1***

                String newupns = AppendFunction.AppendUniqueIdentifier(upnS);
                //String myccnumber = cccS + newupns;
                String myccnumber = cccS;

               //main String sendSms = myccnumber +  "*" + Service_No+ "*"+ f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + emailS + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" +serviceID + "*" + serviceUnitID+ "*" + ranksID;
                String sendSms = myccnumber +  "*" + Service_No+ "*"+ f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + emailS + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" +serviceID + "*" + serviceUnitID+ "*" + ranksID;
                    //Service_No
                //String sendSms =  f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + emailS + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" +serviceID + "*" + serviceUnitID;

//                    String sendSms = "Reg*" + cccS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + language_code + "*" + sms_code + "*" +wkly_code+"*"+pnt_code+"*"+message_code+"*"+ status_code;


                String encrypted = Base64Encoder.encryptString(sendSms);


                String mynumber = Config.mainShortcode;

                if (chkinternet.isInternetAvailable()) {
                    List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                    for (int x = 0; x < myl.size(); x++) {

                        String un = myl.get(x).getUname();
                        List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                        for (int y = 0; y < myl2.size(); y++) {

                            String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                            acs.sendDetailsToDbPost("Reg*" + encrypted, phne);
                        }
                    }


                } else {

                    sm.sendMessageApi("Reg*" + encrypted, mynumber);
                    LogindisplayDialog("Client registered successfully, kindly confirm that you have received the client registration successful SMS before booking an appointment");


                }


                final String mycc = cccE.getText().toString();
                final String myupn = upnE.getText().toString();

                saveClientArtData(mycc, myupn, art_dateS);

                clearFields();

                counter = counter + 1;


                RegisterCounter newcount = new RegisterCounter(counter);
                newcount.save();

//                        System.out.println("decrypted text is "+decrypted);

//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//
//                    alertDialogBuilder.setMessage("DO you want to make appointment");
//                    alertDialogBuilder.setPositiveButton("yes",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface arg0, int arg1) {
////                                    Toast.makeText(Appointment.this,"You clicked yes button",Toast.LENGTH_LONG).show();
//
//
//                                    Intent myint = new Intent(Registration.this , Appointment.class);
//                                    myint.putExtra("ccc",mycc);
//                                    myint.putExtra("upn",myupn);
//                                    startActivity(myint);
//
//                                    Toast.makeText(getApplicationContext(), "Registration was submitted successfully", Toast.LENGTH_SHORT).show();
//
//
////                                    myint.putExtra("value","ths value");
//                                }
//                            });
//
//                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//
//                            Toast.makeText(getApplicationContext(), "Registration was submitted successfully", Toast.LENGTH_SHORT).show();
//
////                            finish();
//                        }
//                    });
//
//
//
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();

//                    LogindisplayDialog("success submitting Registration");


            }


        } catch (Exception e) {
            Toast.makeText(this, "error in submission, try again " + e, Toast.LENGTH_SHORT).show();


        }
    }


    public void saveClientArtData(String mycc, String myupn, String art_dateS) {

        try {

            List<Clientartdate> myl = Clientartdate.findWithQuery(Clientartdate.class, "select * from Clientartdate where mfl=? and upn=?", mycc, myupn);
            if (myl.size() > 0) {

                Clientartdate.executeQuery("update Clientartdate set artdate=? where mfl=? and upn=?", art_dateS, mycc, myupn);

            } else {

                Clientartdate crd = new Clientartdate(mycc, myupn, art_dateS);
                crd.save();
            }
        } catch (Exception e) {


        }
    }


//    public static String MD5_Hash(String s) {
//        MessageDigest m = null;
//
//        try {
//            m = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        m.update(s.getBytes(),0,s.length());
//        String hash = new BigInteger(1, m.digest()).toString(16);
//        return hash;
//    }





    //get facilities








    //close facilities


    public void LogindisplayDialog(String message) {

        try {

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Success");
            adb.setIcon(R.mipmap.success);
            adb.setMessage(message);
            adb.setCancelable(false);

            adb.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Toast.makeText(Login.this,message,Toast.LENGTH_LONG).show();

                }
            });
            AlertDialog mydialog = adb.create();
            mydialog.show();
        } catch (Exception e) {


        }

    }


    //get Facilitiews main

    public void getFacilities(){

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET,
                Config.GET_DOD_SERVICES, null,  new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {



                try {


                    services = new ArrayList<Service>();
                    servicesList = new ArrayList<String>();

                    services.clear();
                    servicesList.clear();


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = (JSONObject) response.get(i);



                        int id = service.has("id") ? service.getInt("id") : 0;
                        String name = service.has("name") ? service.getString("name") : "";



                        //String org_id = service.has("service_id") ? service.getString("service_id") : "";

                            /*int partner_type_id = service.has("partner_type_id") ? service.getInt("partner_type_id") : 1 ;

                            String phone_no = service.has("phone_no") ? service.getString(null) : "";
                            String location = service.has("location") ? service.getString("Nairobi") : "";

                            String created_by = service.has("created_by") ? service.getString(null) : "";

                            String updated_by = service.has("updated_by") ? service.getString(null) : "";
                            String createdAt = service.has("createdAt") ? service.getString("2021-07-27T11:06:43.000Z") : "";
                            String updatedAt = service.has("updatedAt") ? service.getString("2021-07-30T05:52:34.000Z") : "";
                            String deletedAt = service.has("deletedAt") ? service.getString(null) : "";*/



                        //Service newService = new Service(id,name,partner_type_id,phone_no, location, created_by, updated_by, createdAt, updatedAt, deletedAt);
                        //Service newService = new Service(id,name, org_id);
                        Service newService = new Service(id,name);

                        services.add(newService);
                        servicesList.add(newService.getName());
                        // }

                        //services.add(new Service(0,"--select service--",1, "--select service--","--select service--", null, null, "2021","--select--","--select--"));
                        //services.add(new Service(0,"--select service--", "20"));
                        // services.add(new Service(0,"--select service--"));
                        // services.add(new Service(0,""));
                        // servicesList.add("--select service--");
                        //servicesList.add("");

                        ArrayAdapter<String> aa=new ArrayAdapter<String>(Registration.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                servicesList)
                        {
                            @Override
                            public int getCount() {
                                return super.getCount(); // you dont display last item. It is used as hint.
                            }
                        };

                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // if (ServiceSpinner != null){
                        ServiceSpinner.setAdapter(aa);
                        ServiceSpinner.setSelection(aa.getCount()-1);

                        serviceID = services.get(aa.getCount()-1).getId();

                        ServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                                // serviceUnitSpinner.setAdapter(null);

                                serviceID = services.get(position).getId();
                                //getDepartments(services.get(position).getService_id());

//
                                   /* if (serviceID !=0)
                                        Toast.makeText(Registration.this, "getting units", Toast.LENGTH_LONG).show();*/
                                try {
                                    getDepartments(serviceID);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                //getDepartments(serviceID);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {


                            }
                        });

                    }


                    //}

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                /*if (error instanceof TimeoutError) {
                    Toast.makeText(Registration.this,"Request Time-Out", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Registration.this,"No Connection Found", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Registration.this,"Server Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(Registration.this,"Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Registration.this,"Parse Error", Toast.LENGTH_SHORT).show();
                }*/



                // Toast.makeText(Registration.this, " cant get services", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getFacilities();
            }
        }
        )

        {

            /**
             * Passing some request headers
             */
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Authorization", loggedInUser.getToken_type()+" "+loggedInUser.getAccess_token());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };


        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //AppController.getInstance().addToRequestQueue(jsonObjReq);
        rq.add(jsonArrayRequest);


            }


            public void getDepartments(int ID) {

                JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.GET, Config.GET_DOD_UNITS+ID,
                        null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //Toast.makeText(Registration.this, "response", Toast.LENGTH_LONG).show();

                        try {

                            serviceUnits = new ArrayList<Unit>();
                            serviceUnitsList = new ArrayList<String>();

                            serviceUnits.clear();
                            serviceUnitsList.clear();


                            //JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject serviceUnit = (JSONObject) response.get(i);

                                int id = serviceUnit.has("id") ? serviceUnit.getInt("id") : 0;
                                int service_id = serviceUnit.has("service_id") ? serviceUnit.getInt("service_id") : 0;
                                String unit_name = serviceUnit.has("unit_name") ? serviceUnit.getString("unit_name") : "";




                                Unit newServiceUnit = new Unit(id, service_id, unit_name);

                                serviceUnits.add(newServiceUnit);
                                serviceUnitsList.add(newServiceUnit.getUnit_name());
                            }

                            serviceUnits.add(new Unit( 0, 0,"--select unit--" ));
                            serviceUnitsList.add("--select unit--");

                            ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    serviceUnitsList) {
                                @Override
                                public int getCount() {
                                    return super.getCount(); // you dont display last item. It is used as hint.
                                }
                            };

                            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            serviceUnitSpinner.setAdapter(aa);
                            serviceUnitSpinner.setSelection(aa.getCount() - 1);

                            serviceUnitID = serviceUnits.get(aa.getCount() - 1).getId();

                            serviceUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                //@Overide
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                    // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                                    serviceUnitID = serviceUnits.get(position).getId();



//                                Toast.makeText(context,facilityDepartments.get(position).getDepartment_name(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });



                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Registration.this, "cant get", Toast.LENGTH_LONG).show();


                        if (error instanceof NetworkError) {
                            Toast.makeText(Registration.this,"Network Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(Registration.this,"Parse Error", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError) {
                            Toast.makeText(Registration.this,"Server Error", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                        error.printStackTrace();
                        getDepartments(serviceID);
                    }
                }
                ){
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        //headers.put("Authorization", loggedInUser.getToken_type()+" "+loggedInUser.getAccess_token());

                        headers.put("Content-Type", "application/json");
                        headers.put("Accept", "application/json");
                        return headers;
                    }
                };
                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                //AppController.getInstance().addToRequestQueue(jsonObjReq);
                rq.add(jsonArrayRequest);


            }


            //END FACILITIES


public  void getRanks(){

    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
            Config.GET_DOD_RANKS, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            try {
               ranks = new ArrayList<Ranks>();
                RankList= new ArrayList<String>();

                ranks.clear();
                RankList.clear();

                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = (JSONObject) response.get(i);

                    int id = jsonObject.has("id") ? jsonObject.getInt("id") : 0;
                    String name = jsonObject.has("rank_name") ? jsonObject.getString("rank_name") : "";


                   Ranks rank = new Ranks(id,name);

                    ranks.add(rank);
                    RankList.add(rank.getRank_name());
                }

                ranks.add(new Ranks(0,"--select your rank--"));
                RankList.add("--select rank--");

                ArrayAdapter<String> aa=new ArrayAdapter<String>(Registration.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        RankList){
                    @Override
                    public int getCount() {
                        return super.getCount(); // you dont display last item. It is used as hint.
                    }
                };

                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

               rankSpin.setAdapter(aa);
               rankSpin.setSelection(aa.getCount()-1);

               ranksID = ranks.get(aa.getCount()-1).getId();

                rankSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ranksID= ranks.get(position).getId();
                        //getSubCountiesP(counties.get(position).getOrganisationunitid());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



            }catch(JSONException e){
                e.printStackTrace();

            }




        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            //Toast.makeText(Registration.this, " cant get ranks", Toast.LENGTH_LONG).show();

        }
    }){
        public Map<String, String> getHeaders() {
            HashMap<String, String> headers = new HashMap<String, String>();
             headers.put("Content-Type", "application/json");
            headers.put("Accept", "application/json");
            return headers;

        }
    };


   jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    rq.add(jsonArrayRequest);






}

}






