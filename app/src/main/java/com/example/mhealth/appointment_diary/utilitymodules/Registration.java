package com.example.mhealth.appointment_diary.utilitymodules;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.AppendFunction.AppendFunction;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.Mydates.MyDates;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.R;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.models.Country;
import com.example.mhealth.appointment_diary.models.RegisterCounter;
import com.example.mhealth.appointment_diary.models.counties;
import com.example.mhealth.appointment_diary.models.scounties;
import com.example.mhealth.appointment_diary.models.wards;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Clientartdate;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.tables.Myaffiliation;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.facebook.stetho.Stetho;
import com.google.android.material.snackbar.Snackbar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by DELL on 12/11/2015.
 */
public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Calendar calendar = Calendar.getInstance();
    /* calendar.set(Calendar.YEAR, year);
     calendar.set(Calendar.MONTH, month);
     calendar.set(Calendar.DAY_OF_MONTH, day);*/
    long date_ship_millis = calendar.getTimeInMillis();
    int smsCodeA, moteA;
    TextView birth_facilityText1;

    LinearLayout btnNew1;

    Button btnRUpdate1;



    private RequestQueue rq;
    boolean a;
    Progress pr;
    public String ENROLMENT_DATE = "";
    public String ART_DATE = "";

    public String ARTDATEF="";
    public String ENROLF="";
    public String DATE_CONFIRMED_POSITIVE="";


    ArrayList<String> countiesList;
    ArrayList<counties> countiess;

    ArrayList<String> scountyList;
    ArrayList<scounties> scountiess;

    ArrayList<String> wardsList;
    ArrayList<wards> wardss;

    private int countyID = 0;
    private int scountyID = 0;

    private int wardID = 0;


    ArrayList<String> countiesListb;
    ArrayList<counties> countiessb;
    private int countyIDb = 0;


    ArrayList<String> countriesList;
    ArrayList<Country> countries;

    private String origin_country = "";
    private int countryID = 0;


    Progress progress;
    public String z;
    String IDused;

    Dialogs dialogs;
    SweetAlertDialog mdialog;
    Dialog mydialog;
    public String jsonObject1, jsonObject2, jsonObject3;


    //Spinner ServiceSpinner, serviceUnitSpinner, rankSpinner, countrySpinner;
    private SearchableSpinner birthSpinner, ServiceSpinner, serviceUnitSpinner, rankSpinner, countrySpinner;


    LinearLayout smslayoutL, idnoL, orphanL, altphoneL, disableL, groupingL, birthL, UPIL;
    Button upibtn,  btnSearch11;

    EditText cccE, upnE, fileserialE, f_nameE, s_nameE, o_nameE, dobE, enrollment_dateE, art_dateE, phoneE, buddyphoneE, idnoE, altphoneE, ageinyearsE, locatorcountyE, locatorsubcountyE, locatorlocationE, locatorwardE, locatorvillageE, UPI_number, dobirth, dateconfirmed;

    Spinner genderS, maritalS, conditionS, enrollmentS, languageS, smsS, wklymotivation, messageTime, SelectstatusS, patientStatus, GroupingS, orphanS, schoolS, newGroupingS, regimenS, who_stageS;

    String gender_code, marital_code, who_code, regimen_code, condition_code, grouping_code, new_grouping_code, category_code, language_code, sms_code, sms_code1, Selectstatus_code, wklyMotivation_code, messageTime_code, patientStatus_code, school_code, orphan_code, idnoS, upi_no, birth_cert_no, locatorcountyS, locatorsubcountyS, locatorlocationS, locatorwardS, locatorvillageS;
    int genderid, group_id, groupCode, time1, selectStatusCode,client_statusCode, txt_time, language_id1, language_code1, gender_code1, marital_code1, marital_id, county_code1, scounty_code1, ward_code1;
    String fname1, mname1, lname1, SelectStatus, client_status,  dobb, CCCenroledDate, DateEnrolledCare, motivational_enable,  primaryPhone, smsenable,  smsenable1, motivational_enable1, clientStatus, village1, location1;
    int sex, marital, grouping1, language1;

    DatePickerDialog datePickerDialog;
    CheckInternet chkinternet;
    AccessServer acs;
    SendMessage sm;

    String[] genders = {"", "Female", "Male"};

    String[] regimens = {"", "ABC/3TC/NVP", "AZT/3TC/NVP","ABC/3TC/EFV", "TDF/3TC/AZT","AZT/3TC/DTG","ETR/RAL/DRV/RTV","AZT/3TC/LPV/r","AZT/TDF/3TC/LPV/r", "TDF/ABC/LPV/r", "ABC/TDF/3TC/LPV/r", "ETR/TDF/3TC/LPV/r", "ABC/3TC/LPV/r","D4T/3TC/LPV/r","ABC/DDI/LPV/r","TDF/3TC/NVP","AZT/3TC/EFV","TDF/3TC/ATV/r","AZT/3TC/ATV/r","D4T/3TC/EFV","AZT/3TC/ABC","TDF/3TC/DTG",   "TDF/3TC/LPV/r","ABC/3TC/ATV/r","TDF/3TC/DTG/DRV/r","TDF/3TC/RAL/DRV/r","TDF/3TC/DTG/EFV/DRV/r","ABC/3TC/RAL", "AZT/3TC/RAL/DRV/r", "ABC/3TC/RAL/DRV/r","RAL/3TC/DRV/RTV/AZT","RAL/3TC/DRV/RTV/ABC", "ETV/3TC/DRV/RTV", "RAL/3TC/DRV/RTV/TDF","RAL/3TC/DRV/RTV","Other (Specify)"};
    String[] who_stages = {"", "WHO Stage 1", "WHO Stage 2", "WHO Stage 3","WHO Stage 4"};

    String WHO_STAGE ="";
    String REGIMEN="";

    // Please Select Gender*

    String[] newgroupings = {"", "Adolescent", "PMTCT", "TB", "Adults", "Peads", "TB-HIV", "HEI"};
    //Please Select Grouping
    String[] gendersUcsf = {"", "Female", "Male"};
    //Please Select Sex
    String[] maritals = {"", "Single", "Married Monogomaus", "Married Polygamous", "Divorced", "Widowed", "Cohabiting", "Not Applicable"};
    //Please Select Marital Status*
    String[] maritalsInfants = {"", "Single", "Not Applicable"};
    //Please Select Marital Status
    String[] conditions = {"", "ART", "Pre-Art"};
    // Please Select Condition*
    String[] groups = {"", "peads", "adolescents", "PMTCT", "ART", "High VL (Suppressed & non suppressant)"};
    //Please Select Grouping
    String[] languages = {"", "Swahili", "English"};
    //Please Select Language
    String[] smss = {"", "Yes", "No"};
    // "Enable Sms*",
    String[] orphanOp = {"", "Yes", "No"};
    //Are you an Orphan
    String[] schoolOp = {"", "Yes", "No"};
    //Are you in school
    String[] weeklymotivation = {"", "Yes", "No"};
    // Enable weekly motivation alerts
    String[] msgtime = {"", "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "22:00", "23:00"};
    //Please select preffered messaging time
    String[] pntstatus = {"", "New client", "Update client", "Transfer Client In (Transfer in of a client from a facility without ushauri system)"};
    //Enable Sms
    String[] statuss = {"", "Active", "Disabled", "Deceased", "Transfer Out"};
    //Please Select Status*
    String[] statussnew = {"", "Active"};
    //Please Select Status

    //"Please select client type"
    String counter;

    LinearLayout birthCountyLinear1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        birth_facilityText1 =findViewById(R.id.birth_facilityText);
        birthCountyLinear1=findViewById(R.id.birthCountyLinear);
        btnNew1 = findViewById(R.id.btnNew);
        btnRUpdate1 = findViewById(R.id.btnRUpdate);
        smsCodeA =0;
        moteA =0;

        rq = Volley.newRequestQueue(Registration.this);


        ServiceSpinner = findViewById(R.id.ServiceSpinner);
        serviceUnitSpinner = findViewById(R.id.serviceUnit);
        rankSpinner = findViewById(R.id.RankSpinner);
        birthSpinner = findViewById(R.id.birthCountySpinner);
        countrySpinner = findViewById(R.id.countrySpinner);
        upibtn =findViewById(R.id.btnRSubmit);
        btnSearch11=findViewById(R.id.btnSearch1);





        try {
            //this.ctx = ctx;
            //pr = new Progress(ctx);
            mydialog = new Dialog(Registration.this);
            dialogs = new Dialogs(Registration.this);
            // pm=new ProcessMessage();

        } catch (Exception e) {


        }

        //getUPI();
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

        DateConfirmedListener();

       /* enrollment_dateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnrollmentdateListener();
            }
        });

        art_dateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArtdateListener();
            }
        });*/
        gender_code1 = 0;
        marital_code1 = 0;

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
        /*getCountries();
        getFacilities();
        getcountiesbirth();*/
        //getWards(wardID);

        if (chkinternet.isInternetAvailable()) {

            getCountries();
            // getFacilities();
            //  getcountiesbirth();

            // acs.getDefaultersAppointmentMessages(getUserPhoneNumber());

        } else {
            Toast.makeText(Registration.this, "Please check your internet connection", Toast.LENGTH_LONG).show();

        }

        final Context gratitude = this;
        final Button btnRSubmit = (Button) findViewById(R.id.btnRSubmit);
        final Button submitUPIrequest = (Button) findViewById(R.id.btnRequest);
        //btnRSubmit.setEnabled(true);
        submitUPIrequest.setEnabled(true);

        Stetho.initializeWithDefaults(this);


        btnSearch11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Registration.this, "Please check your internet connection", Toast.LENGTH_LONG).show();

                searchClient();

            }
        });



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

                    fileserialE.setText(upnE.getText().toString());

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
            regimenS.setOnItemSelectedListener(this);
            who_stageS.setOnItemSelectedListener(this);


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
            progress = new Progress(Registration.this);
            ageinyearsE = (EditText) findViewById(R.id.ageinyears);
            sm = new SendMessage(Registration.this);
            acs = new AccessServer(Registration.this);
            chkinternet = new CheckInternet(Registration.this);
            idnoE = (EditText) findViewById(R.id.idno);
            idnoS = "";
            upi_no = "";
            birth_cert_no = "";

            locatorcountyE = (EditText) findViewById(R.id.locatorcounty);
            locatorsubcountyE = (EditText) findViewById(R.id.locatorsubcounty);
            locatorlocationE = (EditText) findViewById(R.id.locatorlocation);
            locatorwardE = (EditText) findViewById(R.id.locatorward);
            locatorvillageE = (EditText) findViewById(R.id.locatorvillage);

            dateconfirmed = (EditText) findViewById(R.id.date_confirmed);


            buddyphoneE = (EditText) findViewById(R.id.buddyphone);
            altphoneE = (EditText) findViewById(R.id.altphone);
            disableL = (LinearLayout) findViewById(R.id.disablell);

            altphoneL = (LinearLayout) findViewById(R.id.altphonell);
            orphanL = (LinearLayout) findViewById(R.id.orphanll);
            idnoL = (LinearLayout) findViewById(R.id.idnoll);
            fileserialE = (EditText) findViewById(R.id.cfile);
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
            //UPI no
            UPI_number = (EditText) findViewById(R.id.UPIno);
            //dob no
            dobirth = (EditText) findViewById(R.id.birthno);

            birthL = (LinearLayout) findViewById(R.id.birthnoll);
            UPIL = (LinearLayout) findViewById(R.id.UPInoll);


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
//           GroupingS=(Spinner) findViewById(R.id.grouping_spinner);

            regimenS = (Spinner) findViewById(R.id.regimen_spinner);
            who_stageS = (Spinner) findViewById(R.id.who_spinner);


            gender_code = "";
            who_code="";
            regimen_code="";
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

            locatorcountyS = "-1";
            locatorsubcountyS = "-1";
            locatorlocationS = "-1";
            locatorwardS = "-1";
            locatorvillageS = "-1";
//           grouping_code="";
        } catch (Exception e) {

            Toast.makeText(this, "error initialising variables", Toast.LENGTH_SHORT).show();


        }
    }

    public void clearFields() {

        try {

            locatorcountyE.setText("");
            locatorsubcountyE.setText("");
            locatorlocationE.setText("");
            locatorwardE.setText("");
            locatorvillageE.setText("");

            ageinyearsE.setText("");


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

            UPI_number.setText("");
            dobirth.setText("");
            if (altphoneE.isShown()) {
                altphoneE.setText("");
            }

            buddyphoneE.setText("");
            fileserialE.setText("");


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

            gender_code = "";
            who_code="";
            regimen_code="";
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

    public  void DateConfirmedListener(){


        try {

            dateconfirmed.setOnClickListener(new View.OnClickListener() {
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
                                    dateconfirmed.setText(dayOfMonth + "/"
                                            + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {


        }

    }



    public void EnrollmentdateListener() {
       /* Calendar cur_calender = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        long date_ship_millis = calendar.getTimeInMillis();
                        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

                        ENROLMENT_DATE = newFormat.format(new Date(date_ship_millis));

                        enrollment_dateE.setText(ENROLMENT_DATE);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker();
        datePickerDialog.show();*/

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

        /*Calendar cur_calender = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(Registration.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        long date_ship_millis = calendar.getTimeInMillis();
                        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

                        ART_DATE = newFormat.format(new Date(date_ship_millis));

                        art_dateE.setText(ART_DATE);
                    }
                }, cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker();
        datePickerDialog.show();*/


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
                    datePickerDialog.show();
                }
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


                                    if (difference >= 18) {
                                        idnoL.setVisibility(View.VISIBLE);
                                        birthL.setVisibility(View.GONE);
                                        UPIL.setVisibility(View.VISIBLE);
                                        //UPI_number.setEnabled(false);
                                        getUPI_id();
                                        if (isUcsf()) {
                                            orphanL.setVisibility(View.GONE);
                                        }


                                    } else if (difference < 18) {
                                        idnoL.setVisibility(View.GONE);
                                        birthL.setVisibility(View.VISIBLE);
                                        UPIL.setVisibility(View.VISIBLE);
                                        getUPI_birth();
                                        //getUPI();

                                        if (isUcsf()) {
                                            orphanL.setVisibility(View.GONE);
                                        }
                                    } else {

                                        idnoL.setVisibility(View.GONE);
                                        if (isUcsf()) {

                                            orphanL.setVisibility(View.VISIBLE);
                                            setOrphanSpinners();
                                            populateOrphan();
                                            populateSchool();

                                        }


                                    }

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

    public void getUPI_id() {

        idnoE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Toast.makeText(Registration.this, "has focus", Toast.LENGTH_SHORT).show();
                } else {

                    progress.showProgress("Getting UPI number");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("identifier", "national-id");
                    //params.put("identifier_value", "2345678");
                    params.put("identifier_value", idnoE.getText().toString());


                    JSONObject jsonObject = new JSONObject(params);


                    try {
                        List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                        if (_url.size() == 1) {
                            for (int x = 0; x < _url.size(); x++) {
                                z = _url.get(x).getBase_url1();
                            }
                        }

                    } catch (Exception e) {

                    }

                    RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
                    String url = "https://ushauriapi.kenyahmis.org/mohupi/verify";

                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, z+Config.UPI_VERIFY, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            UPI_number.setText("");
                            Log.d("UPI", response.toString());
                            progress.dissmissProgress();

                            try {
                                //JSONObject jsonObject1 =response.getJSONObject("clientExists");

                                boolean x = response.getBoolean("clientExists");
                                if (x == true) {
                                    Toast.makeText(Registration.this, "Exists", Toast.LENGTH_SHORT).show();

                                    JSONObject jsonObject1 = response.getJSONObject("client");

                                    String UPI_number1 = jsonObject1.getString("clientNumber");
                                    String firstname = jsonObject1.getString("firstName");
                                    String lastname = jsonObject1.getString("lastName");

                                    // UPI_number.setText(UPI_number1);
                                    //UPI_number.setEnabled(false);

                                    //dialogs.showSuccessDialog("Clients UPI number is" + " " + UPI_number1, "Name:" + " " + firstname + " " + lastname);

                                    //Toast.makeText(Registration.this, "Clients UPI number is"+ " " +UPI_number1 +" "+ "Name:"+ " "+ firstname+ " "+ lastname, Toast.LENGTH_SHORT).show();

                                    //Log.d("", UPI_number1);


                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Registration.this);
                                    builder1.setIcon(getResources().getDrawable(R.drawable.moh1));
                                    builder1.setTitle("Client UPI Number Is" + " " + UPI_number1);
                                    builder1.setMessage( "Client Name:" + " " + firstname + " " + lastname);
                                    builder1.setCancelable(false);

                                    builder1.setPositiveButton(
                                            "Accept",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    UPI_number.setText(UPI_number1);

                                                    /*Intent intent = new Intent(Registration.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();*/

                                                    //dialog.cancel();
                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "Reject",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    UPI_number.setText("");

                       /* Intent intent = new Intent(Config.this, SelectUrls.class);
                        startActivity(intent);
                        dialog.cancel();*/
                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();








                                } else {
                                    dialogs.showErrorDialog("Client Has No UPI Number", "Please Request UPI Number For The Client");
                                    //  Toast.makeText(Registration.this, "Client has no UPI number", Toast.LENGTH_SHORT).show();
                                    UPI_number.setText("");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            // Toast.makeText(Registration.this, idnoE.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                         //   Log.d("", error.getMessage());
                            Toast.makeText(Registration.this, "error occured, try again" + "Server Error", Toast.LENGTH_SHORT).show();
                            progress.dissmissProgress();
                            //Toast.makeText(Registration.this, "Lost focus2"+idnoE.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    requestQueue.add(jsonObjectRequest1);


                    //   Toast.makeText(Registration.this, "Lost focus"+idnoE.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void getUPI_birth() {


        dobirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Toast.makeText(Registration.this, "has focus", Toast.LENGTH_SHORT).show();
                } else {
                    progress.showProgress("Getting UPI number");

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("identifier", "national-id");
                    // params.put("identifier_value", "2345678");

                    params.put("identifier_value", dobirth.getText().toString());


                    JSONObject jsonObject = new JSONObject(params);
                    //String url = "https://ushauriapi.kenyahmis.org/mohupi/verify";

                    try {
                        List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                        if (_url.size() == 1) {
                            for (int x = 0; x < _url.size(); x++) {
                                z = _url.get(x).getBase_url1();
                            }
                        }

                    } catch (Exception e) {

                    }


                    RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
                    String url = "https://ushauriapi.kenyahmis.org/mohupi/verify";

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, z+Config.UPI_VERIFY, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            UPI_number.setText("");
                            Log.d("UPI", response.toString());
                            progress.dissmissProgress();

                            try {
                                //JSONObject jsonObject1 =response.getJSONObject("clientExists");

                                boolean x = response.getBoolean("clientExists");
                                if (x == true) {
                                    //Toast.makeText(Registration.this, "Exists", Toast.LENGTH_SHORT).show();

                                    JSONObject jsonObject1 = response.getJSONObject("client");

                                    String UPI_number1 = jsonObject1.getString("clientNumber");
                                    String firstname = jsonObject1.getString("firstName");
                                    String lastname = jsonObject1.getString("lastName");

                                    //UPI_number.setText(UPI_number1);
                                    // UPI_number.setEnabled(false);
                                    // dialogs.showSuccessDialog("Clients UPI number is" + " " + UPI_number1, "Name:" + " " + firstname + " " + lastname);
                                    //Toast.makeText(Registration.this, "Clients UPI number is"+ " " +UPI_number1 +" "+ "Name"+ " "+ firstname+ " "+ lastname, Toast.LENGTH_SHORT).show();
                                    Log.d("", UPI_number1);





                                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Registration.this);
                                    builder1.setIcon(getResources().getDrawable(R.drawable.moh1));
                                    //ic_dialog_alert
                                    builder1.setTitle("Client UPI Number Is" + " " + UPI_number1);
                                    builder1.setMessage( "Client Name:" + " " + firstname + " " + lastname);
                                    builder1.setCancelable(false);

                                    builder1.setPositiveButton(
                                            "Accept",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    UPI_number.setText(UPI_number1);

                                                }
                                            });

                                    builder1.setNegativeButton(
                                            "Reject",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    UPI_number.setText("");

                                                }
                                            });

                                    AlertDialog alert11 = builder1.create();
                                    alert11.show();

                                } else {
                                    dialogs.showErrorDialog("Client has no UPI number", "Please request UPI number for the client");
                                    //Toast.makeText(Registration.this, "Client has no UPI number", Toast.LENGTH_SHORT).show();
                                    UPI_number.setText("");
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            // Toast.makeText(Registration.this, idnoE.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Registration.this, "error occured, try again", Toast.LENGTH_SHORT).show();
                            progress.dissmissProgress();
                            // Toast.makeText(Registration.this, "Lost focus2"+idnoE.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    requestQueue.add(jsonObjectRequest);


                    //   Toast.makeText(Registration.this, "Lost focus"+idnoE.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Spinner spin = (Spinner) parent;
        if (spin.getId()==R.id.who_spinner){
            WHO_STAGE =who_stages[position];
            who_code =Integer.toString(position);


        }
        if (spin.getId()==R.id.regimen_spinner){
            REGIMEN = regimens[position];
            regimen_code=Integer.toString(position);

        }
        if (spin.getId() == R.id.gender_spinner) {


            gender_code = Integer.toString(position);

        }
        if (spin.getId() == R.id.marital_spinner) {

            marital_code = Integer.toString(position);

            // String who = maritals[position];


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
                // clearFields();
                populateStatusNew();     //Active
                cccE.setEnabled(false);
                populateMflCode();
                btnSearch11.setVisibility(View.GONE);
                //btnNew1.setVisibility(View.VISIBLE);
                //btnRUpdate1.setVisibility(View.GONE);
                f_nameE.setText("");
                s_nameE.setText("");
                o_nameE.setText("");
                dobE.setText("");
                enrollment_dateE.setText("");
                art_dateE.setText("");
                dateconfirmed.setText("");
                phoneE.setText("");
                locatorvillageE.setText("");
                // cccE.setText("");
                fileserialE.setText("");
                //  upnE.setText("");
                getFacilities();


                populateCondition();
                populateGender();
                populateLanguage();
                populateMarital();

                populateNewGrouping();
                populateSms();
//              populateStatus();
                populateweekly();
                populatemsg();
                //populatepntstatus();
                populategrouping();

                //gender_code1 = 0;
                //marital_code1 = 0;
                // condition_code1 = "";
                category_code = "";
                language_code1 = 0;
                sms_code1 = "";
                locatorlocationE.setText("");
                /*Selectstatus_code1 = "";
                wklyMotivation_code1 = "";
                messageTime_code1 = "";
                patientStatus_code1 = "";*/
                // orphan_code = "";
                // school_code = "";



            } else if(patientStatus_code.contentEquals("2")){
                // clearFields();
                cccE.setEnabled(true);
                cccE.setText("");
                // upnE.setText("");
                populateStatus();
                btnSearch11.setVisibility(View.VISIBLE);
                // getFacilities1();
                getFacilities();
                // btnNew1.setVisibility(View.GONE);
                //btnRUpdate1.setVisibility(View.VISIBLE);
                // getDepartments1(countyID);
                // getWards1(scountyID);


            }

            else {
                //clearFields();
                cccE.setEnabled(true);
                cccE.setText("");
                populateStatus();
                btnSearch11.setVisibility(View.GONE);
                //   btnNew1.setVisibility(View.VISIBLE);
                // btnRUpdate1.setVisibility(View.GONE);

                f_nameE.setText("");
                s_nameE.setText("");
                o_nameE.setText("");
                dobE.setText("");
                enrollment_dateE.setText("");
                art_dateE.setText("");
                phoneE.setText("");
                locatorvillageE.setText("");

                cccE.setText("");
                fileserialE.setText("");
                upnE.setText("");
                getFacilities();


                populateCondition();
                populateGender();
                populateLanguage();
                populateMarital();

                populateNewGrouping();
                populateSms();
//              populateStatus();
                populateweekly();
                populatemsg();
                populate_Regimen();
                populateWho_stage();

                //populatepntstatus();
                populategrouping();
                gender_code1 = 0;
                marital_code1 = 0;
                // condition_code1 = "";
                category_code = "";
                language_code1 = 0;
                sms_code1 = "";
                locatorlocationE.setText("");



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
//        Toast.makeText(this, "you selected "+sel ected_item2+"the behind scene value is "+myselected2, Toast.LENGTH_SHORT).show();
    }
    public void populateWho_stage(){
        try {
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), who_stages);
            who_stageS.setAdapter(spinnerAdapter);

        }catch (Exception e){

        }

    }
    public void populate_Regimen(){
        try {
            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getApplicationContext(), regimens);
            regimenS.setAdapter(spinnerAdapter);

        }catch (Exception e){

        }

    }

    public void populategrouping() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), groups);

            GroupingS.setAdapter(customAdapter);


        } catch (Exception e) {


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
    public void populateGenderUpdate() {


        try {

            if (isUcsf()) {
                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), gendersUcsf);

                genderS.setAdapter(customAdapter);


                genderS.setSelection(gender_code1);

            } else {

                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), genders);

                genderS.setAdapter(customAdapter);
                genderS.setSelection(gender_code1);

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
    public void populateNewGrouping1() {


        try {

            if (displayGrouping()) {

                SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), newgroupings);


                newGroupingS.setAdapter(customAdapter);
                newGroupingS.setSelection(groupCode);


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
    public void populateMaritalUpdate() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), maritals);


            maritalS.setAdapter(customAdapter);
            maritalS.setSelection(marital_code1);


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
    public void populateCondition1() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), conditions);

            conditionS.setAdapter(customAdapter);
            conditionS.setSelection(client_statusCode);


        } catch (Exception e) {


        }
    }

    //client_status

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
    public void populateLanguage1() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), languages);


            languageS.setAdapter(customAdapter);
            languageS.setSelection(language_code1);


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

    public void populateSms1() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), smss);
            smsS.setAdapter(customAdapter);

            // smsS.setSelection(Integer.parseInt(sms_code1));
            smsS.setSelection(smsCodeA);

            /*int selectionPosition= custdapter.getPosition("YOUR_VALUE");
            spinner.setSelection(selectionPosition);

            spinnerPosition =customAdapter.getPosition();*/



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
    public void populateStatus1() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), statuss);


            SelectstatusS.setAdapter(customAdapter);
            SelectstatusS.setSelection( selectStatusCode);


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

    public void populateweekly1() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), weeklymotivation);


            wklymotivation.setAdapter(customAdapter);
            wklymotivation.setSelection(moteA);


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

    public void populatemsg1() {

        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), msgtime);


            messageTime.setAdapter(customAdapter);
            messageTime.setSelection(time1);


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

            /*if(countryID==0){


            }else if (countyID==0){

            }else if (scountyID==0){

            }else if (wardID==0){

            }else if (countyIDb==0){

            }*/


            //start set locator information variables

//            locatorsubcountyS,locatorlocationS,locatorwardS,locatorvillageS

           /* if (locatorcountyE.getText().toString().trim().isEmpty()) {

                locatorcountyS = "-1";

            } else {

                locatorcountyS = locatorcountyE.getText().toString();

            }

            if (locatorsubcountyE.getText().toString().trim().isEmpty()) {

                locatorsubcountyS = "-1";

            } else {

                locatorsubcountyS = locatorsubcountyE.getText().toString();

            }*/


            if (locatorlocationE.getText().toString().trim().isEmpty()) {

                locatorlocationS = "-1";

            } else {

                locatorlocationS = locatorlocationE.getText().toString();

            }

           /* if (locatorwardE.getText().toString().trim().isEmpty()) {

                locatorwardS = "-1";

            } else {

                locatorwardS = locatorwardE.getText().toString();

            }*/

            if (locatorvillageE.getText().toString().trim().isEmpty()) {

                locatorvillageS = "-1";

            } else {

                locatorvillageS = locatorvillageE.getText().toString();

            }


            //end set locator information variables

            String cccS = cccE.getText().toString();
            String fileserialS = fileserialE.getText().toString();
            String upnS = upnE.getText().toString();
            String f_nameS = f_nameE.getText().toString();
            String s_nameS = s_nameE.getText().toString();
            String o_nameS = o_nameE.getText().toString();
            String dobS = dobE.getText().toString();
            String enrollmentS = enrollment_dateE.getText().toString();
            String art_dateS = art_dateE.getText().toString();
            String date_confirmedS =dateconfirmed.getText().toString();
            String phoneS = phoneE.getText().toString();
            String altphoneNumber = "-1";
            String buddyphoneNumber = "-1";
            String idNumber = "-1";

            String[] dobArray = new String[]{};
            String[] enrollmentArray = new String[]{};
            String[] artArray = new String[]{};

            String[] confirmArray = new String[]{};

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



            String confirmYear = "";
            String confirmMnth = "";
            String confirmDay = "";

            int confirmYearv = -1;
            int confirmMnthv = -1;
            int confirmDayv = -1;


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

            if (!date_confirmedS.isEmpty()) {
                confirmArray = date_confirmedS.split("/");
                confirmYear = confirmArray[2];
                confirmMnth = confirmArray[1];
                confirmDay = confirmArray[0];

                confirmYearv = Integer.parseInt(confirmYear);
                confirmMnthv = Integer.parseInt(confirmMnth);
                confirmDayv = Integer.parseInt(dobDay);

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

                Toast.makeText(this, "Please Select client type", Toast.LENGTH_SHORT).show();


            }




            else if (cccS.trim().isEmpty()) {

                cccE.setError("mfl code is required");

            } else if (upnS.trim().isEmpty()) {

                upnE.setError("CCC No. required");

            }
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
                if (idnoE.isShown() && !idnoE.getText().toString().trim().isEmpty()) {
                    idnoS = idnoE.getText().toString();
                } else {

                    idnoS = "-1";
                }

                if (UPI_number.isShown() && !UPI_number.getText().toString().trim().isEmpty()) {
                    upi_no = UPI_number.getText().toString();
                } else {
                    upi_no = "-1";
                }

                if (dobirth.isShown() && !dobirth.getText().toString().trim().isEmpty()) {
                    birth_cert_no = dobirth.getText().toString();
                } else {
                    birth_cert_no = "-1";
                }

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

                if (date_confirmedS.trim().isEmpty()){
                    date_confirmedS ="-1";
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

                if (buddyphoneE.isShown() && !buddyphoneE.getText().toString().isEmpty()) {

                    buddyphoneNumber = altphoneE.getText().toString();
                } else {

                    buddyphoneNumber = "-1";
                }

                if (!groupingL.isShown()) {

                    new_grouping_code = "-1";


                }

                if (!art_dateE.isShown()) {

                    art_dateS = "-1";

                }

//*******check empty values and set them to -1***

                String newupns = AppendFunction.AppendUniqueIdentifier(upnS);
                String myccnumber = cccS + newupns;

                //String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + upi_no + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" + locatorcountyS + "*" + locatorsubcountyS + "*" + locatorlocationS + "*" + locatorwardS + "*" + locatorvillageS;
                // String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" +upi_no+ "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code+"*"+new_grouping_code+"*"+locatorcountyS+"*"+locatorsubcountyS+"*"+locatorlocationS+"*"+locatorwardS+"*"+locatorvillageS;
                // String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + upi_no + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" +DATE_CONFIRMED_POSITIVE+ "*" + REGIMEN+"*" +WHO_STAGE+"*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" + countryID+"*" +countyIDb+"*"+countyID + "*" + scountyID + "*" + locatorlocationS + "*" + wardID + "*" + locatorvillageS;
                String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + upi_no + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS +"*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" + countryID+"*" +countyIDb+"*"+countyID + "*" + scountyID + "*" + locatorlocationS + "*" + wardID + "*" + locatorvillageS+ "*" +date_confirmedS+ "*" +regimen_code+"*" +who_code;
                Log.d("Data submitted", "Reg*"+sendSms.toString()+"0712311264");



                String encrypted = Base64Encoder.encryptString(sendSms);
                Log.d("ENCRYPTED", "Reg*"+encrypted.toString()+"0712311264");


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
                countiesList.clear();
                countiess.clear();
                countriesList.clear();
                countries.clear();
                scountyList.clear();
                scountiess.clear();
                wardsList.clear();
                wardss.clear();


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
            Log.d("Error", e.getMessage());
            Log.d("Error2", e.toString());
            e.printStackTrace();
            Toast.makeText(this, "error in submission, try again please" + e, Toast.LENGTH_SHORT).show();


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

    //begin requesting UPI
    public void submitUPIrequest(View v) {

        if (UPI_number.getText().toString().isEmpty()){
            // upibtn.setEnabled(true);
        }else if (!UPI_number.getText().toString().isEmpty()){
            //upibtn.setEnabled(false);
            //Toast.makeText(this, "Client has UPI Number", Toast.LENGTH_SHORT).show();
        }

        if (chkinternet.isInternetAvailable()) {

            // acs.getDefaultersAppointmentMessages(getUserPhoneNumber());

        } else {
            Toast.makeText(Registration.this, "Please Check Your Internet Connection", Toast.LENGTH_LONG).show();

        }


        /*else if (!(dobirth.getText().toString().length() > 2)){
            Toast.makeText(Registration.this, "Birth certificate number must be atleast 3 characters", Toast.LENGTH_LONG).show();
        }*/

        try {
            if(countryID==0){

                Toast.makeText(Registration.this, "Please select country of birth", Toast.LENGTH_LONG).show();

            }else if (countyID==0){
                Toast.makeText(Registration.this, "Please select county of residence", Toast.LENGTH_LONG).show();

            }else if (scountyID==0){
                Toast.makeText(Registration.this, "Please select sub-county", Toast.LENGTH_LONG).show();

            }else if (wardID==0){
                Toast.makeText(Registration.this, "Please select ward", Toast.LENGTH_LONG).show();

            }else if (countyIDb==0){
                Toast.makeText(Registration.this, "Please select county of birth", Toast.LENGTH_LONG).show();

            }



            //start set locator information variables

//            locatorsubcountyS,locatorlocationS,locatorwardS,locatorvillageS

           /* if (locatorcountyE.getText().toString().trim().isEmpty()) {

                locatorcountyS = "-1";

            } else {

                locatorcountyS = locatorcountyE.getText().toString();

            }

            if (locatorsubcountyE.getText().toString().trim().isEmpty()) {

                locatorsubcountyS = "-1";

            } else {

                locatorsubcountyS = locatorsubcountyE.getText().toString();

            }*/


            if (locatorlocationE.getText().toString().trim().isEmpty()) {

                locatorlocationS = "-1";

            } else {

                locatorlocationS = locatorlocationE.getText().toString();

            }


           /* if (locatorwardE.getText().toString().trim().isEmpty()) {

                locatorwardS = "-1";

            } else {

                locatorwardS = locatorwardE.getText().toString();

            }*/

            if (locatorvillageE.getText().toString().trim().isEmpty()) {

                locatorvillageS = "-1";

            } else {

                locatorvillageS = locatorvillageE.getText().toString();

            }


            //end set locator information variables

            String cccS = cccE.getText().toString();
            String fileserialS = fileserialE.getText().toString();
            String upnS = upnE.getText().toString();
            String f_nameS = f_nameE.getText().toString();
            String s_nameS = s_nameE.getText().toString();
            String o_nameS = o_nameE.getText().toString();
            String dobS = dobE.getText().toString();
            String enrollmentS = enrollment_dateE.getText().toString();
            String art_dateS = art_dateE.getText().toString();
            String phoneS = phoneE.getText().toString();
            String altphoneNumber = "-1";
            String buddyphoneNumber = "-1";
            String idNumber = "-1";

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


           /* TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(String.valueOf(s.length()) <= 10){
                        Toast.makeText(getContext(), "your text",Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            };
            edit_explain.addTextChangedListener(textWatcher);*/


            if (patientStatus_code.contentEquals("0")) {

                Toast.makeText(this, "Please Select client type", Toast.LENGTH_SHORT).show();


            } else if (cccS.trim().isEmpty()) {

                cccE.setError("mfl code is required");

            } else if (upnS.trim().isEmpty()) {

                upnE.setError("CCC No. required");

            }
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
            else if (!(o_nameS.length() >2)){
                o_nameE.setError("Last name must have atleast 3 characters");
                //Toast.makeText(,Re, "", Toast.LENGTH_SHORT).show();
                dialogs.showErrorDialog("The length of 'Last Name' must be at least 3 characters", "");

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
                if (idnoE.isShown() && !idnoE.getText().toString().trim().isEmpty()) {
                    idnoS = idnoE.getText().toString();
                } else {

                    idnoS = "-1";
                }

                if (UPI_number.isShown() && !UPI_number.getText().toString().trim().isEmpty()) {
                    upi_no = UPI_number.getText().toString();
                } else {
                    upi_no = "-1";
                }

                if (dobirth.isShown() && !dobirth.getText().toString().trim().isEmpty()) {
                    birth_cert_no = dobirth.getText().toString();
                } else {
                    birth_cert_no = "-1";
                }

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

                if (buddyphoneE.isShown() && !buddyphoneE.getText().toString().isEmpty()) {

                    buddyphoneNumber = altphoneE.getText().toString();
                } else {

                    buddyphoneNumber = "-1";
                }

                if (!groupingL.isShown()) {

                    new_grouping_code = "-1";


                }

                if (!art_dateE.isShown()) {

                    art_dateS = "-1";

                }

//*******check empty values and set them to -1***

                String newupns = AppendFunction.AppendUniqueIdentifier(upnS);
                String myccnumber = cccS + newupns;

                //String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + upi_no + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" + locatorcountyS + "*" + locatorsubcountyS + "*" + locatorlocationS + "*" + locatorwardS + "*" + locatorvillageS;
                String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" + upi_no + "*" + birth_cert_no + "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code + "*" + new_grouping_code + "*" + countryID+"*" +countyIDb+"*"+countyID + "*" + scountyID + "*" + locatorlocationS + "*" + wardID + "*" + locatorvillageS;
                // String sendSms = myccnumber + "*" + fileserialS + "*" + f_nameS + "*" + s_nameS + "*" + o_nameS + "*" + dobS + "*" + idnoS + "*" +upi_no+ "*" + gender_code + "*" + marital_code + "*" + condition_code + "*" + enrollmentS + "*" + art_dateS + "*" + phoneS + "*" + altphoneNumber + "*" + buddyphoneNumber + "*" + language_code + "*" + sms_code + "*" + wklyMotivation_code + "*" + messageTime_code + "*" + Selectstatus_code + "*" + patientStatus_code+"*"+new_grouping_code+"*"+locatorcountyS+"*"+locatorsubcountyS+"*"+locatorlocationS+"*"+locatorwardS+"*"+locatorvillageS;


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
                            // acs.requestUPI("Reg*" + encrypted, "13023");
                            //BEGIN UPI REQUEST


                            try {
                                List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                                if (_url.size() == 1) {
                                    for (int bb = 0; bb < _url.size(); bb++) {
                                        z = _url.get(bb).getBase_url1();
                                    }
                                }

                            } catch (Exception e) {

                            }
                            //pr.showProgress("Requesting UPI...");
                            final int[] mStatusCode = new int[1];

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, z+Config.UPI_REQUEST,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            //Toast.makeText(Registration.this, "message "+response, Toast.LENGTH_LONG).show();
                                            //dialogs.showSuccessDialog("response", response);



                                            try {
                                               /* for (int i=0; i<response.length(); i++){
                                                JSONObject jsonObject7 = (JSONObject) response.get(i); }*/
                                                //JSONObject jsonObject9 =re
                                                JSONObject jsonObject = new JSONObject(response);
                                                //JSONObject jsonObject4 =jsonObject.getJSONObject("errors");
                                                /*if (jsonObject.has("errors")){
                                                   IDused = jsonObject4.getString("identifications[0]");
                                                    dialogs.showErrorDialog("",IDused);
                                                }

                                                 IDused =jsonObject4.getString("identifications[0]");*/

                                                for (int a = 0; a < jsonObject.length(); a++) {
                                                    jsonObject1 = jsonObject.getString("clientNumber");
                                                    jsonObject2 = jsonObject.getString("firstName");
                                                    jsonObject3 = jsonObject.getString("lastName");


                                                    // Dialog

                                                    //end dialog


                                                    ////dialogs.showSuccessDialog("Clients UPI number is"+ " " +jsonObject1, "Name:" + " "+ jsonObject2 + " "+ jsonObject3);
                                                    //Toast.makeText(Registration.this, "Clients UPI number is"+ " " +jsonObject1, Toast.LENGTH_LONG).show();
                                                    //UPI_number.setText(jsonObject1);
                                                }
                                            } catch (JSONException e) {
                                                // Toast.makeText(Registration.this, "catch", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }



                                            // pr.dissmissProgress();


                                            if (mStatusCode[0] == 200) {
                                                JSONObject jsonObject = null;
                                                try {
                                                    jsonObject = new JSONObject(response);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                JSONObject jsonObject4 = null;
                                                try {
                                                    jsonObject4 = jsonObject.getJSONObject("errors");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                if (jsonObject.has("errors")){
                                                    try {
                                                        IDused = jsonObject4.getString("identifications[0]");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    dialogs.showErrorDialog(IDused, "Server response");
                                                    //dialogs.showErrorDialog(response, "Server response");
                                                }else{
                                                    dialogs.showSuccessDialog("Client UPI Number Is" + " " + jsonObject1, "Client Name:" + " " + jsonObject2 + " " + jsonObject3);
                                                    UPI_number.setText(jsonObject1);
                                                }


                                                //UPI_number.setText(jsonObject1);
                                                //dialogs.showSuccessDialog("Clients UPI number is" + " " + jsonObject1, "Name:" + " " + jsonObject2 + " " + jsonObject3);
                                                //dialogs.showSuccessDialog("Server response", response);
                                                // if (response.)
                                                //dialogs.showErrorDialog("NOTE:", IDused);

                                                //dialogs.showSuccessDialog(response,"Server Response");


                                            } else {

                                                dialogs.showErrorDialog("UPI not given", "Server response");

                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            dialogs.showErrorDialog( error.getMessage(), "Server response");

                                            // Toast.makeText(Registration.this, "error", Toast.LENGTH_SHORT).show();
                                            //pr.dissmissProgress();

                                            try {

                                                //byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                                // dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");
                                                Log.d("", error.getMessage());
                                                // Toast.makeText(Registration.this, error.getMessage(), Toast.LENGTH_LONG).show();

                                                //pr.dissmissProgress();

                                            } catch (Exception e) {


//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                                                //dialogs.showErrorDialog("error occured, try again","Server Response");

                                                // pr.dissmissProgress();


                                            }


                                        }
                                    }) {


                                @Override
                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                    mStatusCode[0] = response.statusCode;
                                    return super.parseNetworkResponse(response);
                                }

                                @Override
                                protected VolleyError parseNetworkError(VolleyError volleyError) {
                                    return super.parseNetworkError(volleyError);
                                }

                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("reg_payload", "Reg*" + encrypted);
                                    params.put("user_mfl", cccE.getText().toString());


                                    return params;
                                }

                            };

                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                    800000,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
                            requestQueue.add(stringRequest);

//        RequestQueue requestQueue = Volley.newRequestQueue(ctx);
//        requestQueue.add(stringRequest);

                        }


                        //END UPI REQUEST
                        //acs.requestUPI("Reg*" + encrypted, newupns);
                    }


                } else {

                    //sm.sendMessageApi("Reg*" + encrypted, mynumber);
                    // LogindisplayDialog("Client registered successfully, kindly confirm that you have received the client registration successful SMS before booking an appointment");


                }


                final String mycc = cccE.getText().toString();
                final String myupn = upnE.getText().toString();

                saveClientArtData(mycc, myupn, art_dateS);

                // clearFields();

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

    //getcounties
    public void getFacilities() {
        //String curl = "https://ushauriapi.kenyahmis.org/locator/counties";
        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                z+Config.COUNTIES, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {


                try {


                    countiess = new ArrayList<counties>();
                    countiesList = new ArrayList<String>();

                    countiess.clear();
                    countiesList.clear();


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = (JSONObject) response.get(i);


                        int id = service.has("id") ? service.getInt("id") : 0;
                        String name = service.has("name") ? service.getString("name") : "";
                        int code = service.has("code") ? service.getInt("code") : 0;


                        counties newCounty = new counties(id, name, code);

                        countiess.add(newCounty);
                        countiesList.add(newCounty.getName());
                    }
                    countiess.add(new counties(0, " ", 0));
                    countiesList.add(" ");




                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            countiesList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // if (ServiceSpinner != null){
                    ServiceSpinner.setAdapter(aa);
                    ServiceSpinner.setSelection(aa.getCount() - 1);

                    countyID = countiess.get(aa.getCount() - 1).getId();

                    ServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                            // serviceUnitSpinner.setAdapter(null);

                            countyID = countiess.get(position).getId();
                            //getDepartments(services.get(position).getService_id());

//
                                   /* if (serviceID !=0)
                                        Toast.makeText(Registration.this, "getting units", Toast.LENGTH_LONG).show();*/
                            try {
                                getDepartments(countyID);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //getDepartments(serviceID);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });




                    //}

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(Registration.this, " cant get services", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getFacilities();
            }
        }
        ) {

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

    //SubcountyList

    public void getDepartments(int ID) {

        String url = "https://ushauriapi.kenyahmis.org/locator/scounties?county=";
        //"https://ushauriapi.kenyahmis.org/locator/scounties?county=47";

        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, z+Config.S_COUNTIES+ID,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(Registration.this, "response", Toast.LENGTH_LONG).show();

                try {

                    scountiess = new ArrayList<scounties>();
                    scountyList = new ArrayList<String>();

                    scountiess.clear();
                    scountyList.clear();


                    //JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject serviceUnit = (JSONObject) response.get(i);

                        int id = serviceUnit.has("id") ? serviceUnit.getInt("id") : 0;
                        String name = serviceUnit.has("name") ? serviceUnit.getString("name") : "";


                        scounties newServiceUnit = new scounties(id, name);

                        scountiess.add(newServiceUnit);
                        scountyList.add(newServiceUnit.getName());
                    }

                    scountiess.add(new scounties(0, ""));
                    scountyList.add("");

                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            scountyList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    serviceUnitSpinner.setAdapter(aa);
                    serviceUnitSpinner.setSelection(aa.getCount() - 1);

                    scountyID = scountiess.get(aa.getCount() - 1).getId();

                    serviceUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                            scountyID = scountiess.get(position).getId();
                            getWards(scountyID);
                            //call wards here


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
                    Toast.makeText(Registration.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Registration.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Registration.this, "Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
                error.printStackTrace();
                getDepartments(countyID);
            }
        }
        ) {
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
//call wards

    public void getWards(int ID) {

        String url = "https://ushauriapi.kenyahmis.org/locator/wards?scounty=";
        //https://ushauriapi.kenyahmis.org/locator/wards?scounty=1
        //"https://ushauriapi.kenyahmis.org/locator/scounties?county=47";

        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,  z+Config.WARDS+ID,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(Registration.this, "response", Toast.LENGTH_LONG).show();

                try {

                    wardss = new ArrayList<wards>();
                    wardsList = new ArrayList<String>();

                    wardss.clear();
                    wardsList.clear();


                    //JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject serviceUnit = (JSONObject) response.get(i);

                        int id = serviceUnit.has("id") ? serviceUnit.getInt("id") : 0;
                        String name = serviceUnit.has("name") ? serviceUnit.getString("name") : "";
                        int scounty_id = serviceUnit.has("scounty_id") ? serviceUnit.getInt("scounty_id") : 0;


                        wards newServiceUnit = new wards(id, name, scounty_id);

                        wardss.add(newServiceUnit);
                        wardsList.add(newServiceUnit.getName());
                    }

                    wardss.add(new wards(0, "", 0));
                    wardsList.add("");

                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            wardsList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    rankSpinner.setAdapter(aa);
                    rankSpinner.setSelection(aa.getCount() - 1);

                    //wardID = wardss.get(aa.getCount() - 1).getId();
                    wardID = wardss.get(aa.getCount() -1).getId();
                    rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                            wardID = wardss.get(position).getId();

                            //call wards here


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
                    Toast.makeText(Registration.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Registration.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Registration.this, "Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
                error.printStackTrace();
                getWards(scountyID);
                //getDepartments(countyID);
            }
        }
        ) {
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

    //getcounties of birth

    public void getcountiesbirth() {
        String curl = "https://ushauriapi.kenyahmis.org/locator/counties";


        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                z+Config.COUNTIES, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {


                try {


                    countiessb = new ArrayList<counties>();
                    countiesListb = new ArrayList<String>();

                    countiessb.clear();
                    countiesListb.clear();


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = (JSONObject) response.get(i);


                        int id = service.has("id") ? service.getInt("id") : 0;
                        String name = service.has("name") ? service.getString("name") : "";
                        int code = service.has("code") ? service.getInt("code") : 0;


                        counties newCounty = new counties(id, name, code);

                        countiessb.add(newCounty);
                        countiesListb.add(newCounty.getName());
                    }



                    countiessb.add(new counties(0, "", 0));
                    countiesListb.add("");



                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            countiesListb) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // if (ServiceSpinner != null){
                    birthSpinner.setAdapter(aa);
                    birthSpinner.setSelection(aa.getCount() - 1);

                    countyIDb = countiessb.get(aa.getCount() - 1).getId();

                    birthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                            // serviceUnitSpinner.setAdapter(null);

                            countyIDb = countiessb.get(position).getId();


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });

                }


                //}

                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // Toast.makeText(Registration.this, " cant get services", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getcountiesbirth();
            }
        }
        ) {

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

//get countries

    public void getCountries(){

        String curl1 = "https://ushauriapi.kenyahmis.org/locator/countries";
        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                z+Config.COUNTRIES, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {
                //Toast.makeText(Registration.this, response,  Toast.LENGTH_LONG).show();
                //dialogs.showSuccessDialog("yes", response.toString());
                // error.printStackTrace();


                try {


                    countries = new ArrayList<Country>();
                    countriesList = new ArrayList<String>();

                    countries.clear();
                    countriesList.clear();


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = (JSONObject) response.get(i);


                        int id = service.has("id") ? service.getInt("id") : 0;
                        String name = service.has("name") ? service.getString("name") : "";
                        String code = service.has("code") ? service.getString("code") : "";


                        Country newCounty = new Country(id, name, code);

                        countries.add(newCounty);
                        countriesList.add(newCounty.getName());

                    }
                    countries.add(new Country(0, "", ""));
                    countriesList.add("");




                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            countriesList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // if (ServiceSpinner != null){
                    countrySpinner.setAdapter(aa);
                    countrySpinner.setSelection(aa.getCount() - 136);

                    countryID = countries.get(aa.getCount() - 1).getId();

                    countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                            // serviceUnitSpinner.setAdapter(null);

                            countryID = countries.get(position).getId();
                            Log.d("counryID", String.valueOf(countryID));
                            if (countryID==115){
                                getcountiesbirth();

                                birthSpinner.setVisibility(View.VISIBLE);
                                // birth_facilityText1.setVisibility(View.VISIBLE);
                                birthCountyLinear1.setVisibility(View.VISIBLE);

                            }else{
                                birthSpinner.setVisibility(View.GONE);
                                // birth_facilityText1.setVisibility(View.GONE);
                                birthCountyLinear1.setVisibility(View.GONE);


                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });




                    //}

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registration.this, " cant get country", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getCountries();
            }
        }
        ) {

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
    public void searchClient(){

        // pr.showProgress("Getting Clients Details");

        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }

        String urls ="?client_id=" + cccE.getText().toString()+upnE.getText().toString();
        AndroidNetworking.get(z+Config.SEARCH_CLIENT+urls)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection", "keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DATAA", response.toString());
                        //pr.dissmissProgress();
                        //Toast.makeText(UPIUpdateActivity.this, "sucess", Toast.LENGTH_SHORT).show();
                        try {
                            a = response.getBoolean("success");
                            Log.d("DATAA2222", String.valueOf(a));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (a) {

                            try {

                                JSONObject jsonObject = response.getJSONObject("message");

                                        /*"id": 1768055,
                                        "group_id": 1,
                                        "language_id": 1,
                                        "clinic_number": "1234500001",
                                        "f_name": "Betty",
                                        "m_name": "Mary",
                                        "l_name": "Edward",
                                        "dob": "1992-11-01",
                                        "txt_frequency": 168,
                                        "txt_time": 15,
                                        "phone_no": "0718373569",
                                        "alt_phone_no": null,
                                        "buddy_phone_no": null,
                                        "shared_no_name": null,
                                        "partner_id": 14,
                                        "mfl_code": 12345,
                                        "status": "Active",
                                        "client_status": "ART",
                                        "gender": 1,
                                        "marital": 3,
                                        "smsenable": "Yes",
                                        "enrollment_date": "2020-01-01T12:02:30.000Z",
                                        "art_date": "2020-01-01T12:02:41.000Z",
                                        "wellness_enable": "Yes",
                                        "motivational_enable": "Yes",
                                        "welcome_sent": "No",
                                        "client_type": "New",
                                        "consent_date": null,
                                        "physical_address": null,
                                        "transfer_date": null,
                                        "entry_point": "Mobile",
                                        "gods_number": null,
                                        "date_deceased": null,
                                        "patient_source": null,
                                        "prev_clinic": null,
                                        "ushauri_id": null,
                                        "db_source": null,
                                        "clnd_dob": null,
                                        "clinic_id": 2,
                                        "national_id": "00764352",
                                        "upi_no": "MOH1223232332",
                                        "birth_cert_no": null,
                                        "file_no": "00001",
                                        "locator_county": "0",
                                        "locator_sub_county": "0",
                                        "locator_ward": "0",
                                        "locator_location": null,
                                        "locator_village": null,
                                        "created_by": null,
                                        "updated_by": 1565,
                                        "hei_no": null,
                                        "citizenship": 0,
                                        "county_birth": 0,
                                        "createdAt": "2022-11-24T09:02:01.000Z",
                                        "updatedAt": "2023-06-26T10:16:24.000Z",
                                        "deletedAt": null*/

                                fname1 = jsonObject.getString("f_name");
                                mname1 = jsonObject.getString("m_name");
                                lname1 = jsonObject.getString("l_name");
                                genderid = jsonObject.getInt("gender");
                                sms_code1= jsonObject.getString("smsenable");
                                language_id1 = jsonObject.getInt("language_id");
                                txt_time = jsonObject.getInt("txt_time");
                                SelectStatus =jsonObject.getString("status");

                                client_status =jsonObject.getString("client_status");


                                //  "status": "Active",



                                marital_id = jsonObject.getInt("marital");
                                dobb = jsonObject.getString("dob");
                                CCCenroledDate = jsonObject.getString("enrollment_date");
                                DateEnrolledCare = jsonObject.getString("art_date");
                                primaryPhone = jsonObject.getString("phone_no");
                                smsenable= jsonObject.getString("smsenable");
                                motivational_enable= jsonObject.getString("motivational_enable");

                                clientStatus= jsonObject.getString("client_status");
                                village1= jsonObject.getString("locator_village");
                                location1= jsonObject.getString("locator_location");
                                group_id = jsonObject.getInt("group_id");


                                Log.d("GENDER_ID", String.valueOf(genderid));
                                Log.d("marital_ID", String.valueOf(marital_id));

                              /*  sex= jsonObject.getInt("gender");
                                marital= jsonObject.getInt("marital");*/
                               /* grouping1= jsonObject.getInt("f_name");
                                language1= jsonObject.getInt("f_name");*/



                                // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                // enroldate_format = format.format(Enrollment_date);

                               /* county_code1 = Integer.parseInt(jsonObject.getString("locator_county"));
                                scounty_code1= Integer.parseInt(jsonObject.getString("locator_sub_county"));
                                ward_code1= Integer.parseInt(jsonObject.getString("locator_ward"));
                                Log.d("COUNTY",String.valueOf(county_code1));*/

                                // county1 =jsonObject.getString("locator_county");
                                // county_code1 = Integer.parseInt(jsonObject.getString("locator_county"));
                                f_nameE.setText(fname1);
                                s_nameE.setText(mname1);
                                o_nameE.setText(lname1);
                                //  dobE.setText(dobb);
                                // enrollment_dateE.setText(CCCenroledDate);
                                //  art_dateE.setText(DateEnrolledCare);
                                phoneE.setText(primaryPhone);
                                locatorlocationE.setText(location1);
                                locatorvillageE.setText(village1);

                                //format 1

                                /*Calendar calendar = Calendar.getInstance();
                                int year,  month,  day;
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                long date_ship_millis = calendar.getTimeInMillis();
                                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

                                ART_DATE = newFormat.format(new Date(date_ship_millis));

                                art_dateE.setText(ART_DATE);*/

                                //end format1


                                //FORMATE DATES
                                // dd/MM/yyyy
                                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

                                //DateEnrolledCare = newFormat.format(new Date(date_ship_millis));
                                // art_dateE.setText(DateEnrolledCare);

                                //CCCenroledDate = newFormat.format(new Date(date_ship_millis));
                                //enrollment_dateE.setText(CCCenroledDate);

                                // dobb =newFormat.format(new Date(date_ship_millis));

                                //FORMAT art
                                SimpleDateFormat inputFormatart = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                Date dateart = inputFormatart.parse(DateEnrolledCare);
                                SimpleDateFormat outputFormatart = new SimpleDateFormat("dd/MM/yyyy");
                                String formattedDateart= outputFormatart.format(dateart);
                                art_dateE.setText(formattedDateart);

                                //FORMAT enrolment
                                SimpleDateFormat inputFormatenrol = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                Date dateenrol = inputFormatenrol.parse(CCCenroledDate);
                                SimpleDateFormat outputFormatenrol = new SimpleDateFormat("dd/MM/yyyy");
                                String formattedDateenrol= outputFormatenrol.format(dateenrol);
                                enrollment_dateE.setText(formattedDateenrol);


                                //FORMAT dob
                                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

                                // Parse the input date string into a Date object
                                Date date = inputFormat.parse(dobb);

                                // Create a SimpleDateFormat object for the output format
                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

                                // Format the Date object into the desired output format
                                String formattedDateofbirth = outputFormat.format(date);
                                dobE.setText(formattedDateofbirth);


                                gender_code1 = genderid;
                                marital_code1 = marital_id;
                                language_code1 =language_id1;
                                time1 = txt_time;
                                if (smsenable.contentEquals("Yes")){
                                    smsCodeA =1;
                                }else if (smsenable1.contentEquals("No")){
                                    smsCodeA=2;
                                }else{
                                    smsCodeA=0;
                                }
                                //sms_code1 = smsenable;
                                // smsCodeA =smsenable1;
                                //

                                if (motivational_enable.contentEquals("Yes")){
                                    moteA =1;
                                }else if(motivational_enable.contentEquals("No")){
                                    moteA=2;
                                }else{
                                    moteA=0;
                                }

                                if (SelectStatus.contentEquals("Active")){
                                    selectStatusCode=1;
                                }else if (SelectStatus.contentEquals("Disabled")){
                                    selectStatusCode=2;

                                }else if (SelectStatus.contentEquals("Deceased")){
                                    selectStatusCode=3;

                                }else if(SelectStatus.contentEquals("Transfer Out")){
                                    selectStatusCode=4;

                                }else{
                                    selectStatusCode=0;
                                }

                                if ( client_status.contentEquals("ART")){
                                    client_statusCode=1;
                                }else if (client_status.contentEquals("Pre-Art")){
                                    client_statusCode=2;
                                }else{
                                    client_statusCode=0;
                                }
                                groupCode=group_id;


                                populateGenderUpdate();
                                populateMaritalUpdate();
                                populateSms1();
                                populateLanguage1();
                                populatemsg1();
                                populateweekly1();
                                populateStatus1();
                                populateCondition1();
                                populateNewGrouping1();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            // dialogs.showSuccessDialog("", "No record found" );
                            Log.d("No record", "No record found");
                            Toast.makeText(Registration.this, "No record found", Toast.LENGTH_SHORT).show();
                            clearFields();
                        }



                        //art_dateE.setText(ART_DATE);

                        //FORMAT DATES
                        // buddyphoneE.setText();
                        // idnoE.setText();,
                        // altphoneE
                        // ageinyearsE,
                        //locatorcountyE
                        // locatorsubcountyE,
                        // locatorlocationE,
                        // locatorwardE,

                        //UPI_number,
                        //dobirth.setText;

                        //enroldate_format = new SimpleDateFormat("yyyy.MM.dd").format(Enrollment_date);
                        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        //enrollment_date=format.format(Enrollment_date);
                        // enrollment_date.setText(enroldate_format);

                           /* gender_code = sex;
                            marital_code = marital;
                            condition_code = "";
                            category_code = "";
                            language_code = "";*/

                        //marital_code = marital;
                        // sms_code=sms_id;
                        //populateGender();
                        //populateMarital();
                        // populateSms();
                            /*county_code1=countyID;
                            scounty_code1=scountyID;
                            ward_code1=wardID;*/
                        //getFacilities();

                        // Log.d("SMS", sms_code);
                        // Toast.makeText(UPIUpdateActivity.this, gender_code, Toast.LENGTH_SHORT).show();

                        //   o_name.setText(lname);
                       /* }
                        else {
                           // dialogs.showSuccessDialog("", "No record found" );
                            Toast.makeText(Registration.this, "No record found", Toast.LENGTH_SHORT).show();
                            clearFields();
                        }*/

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("noErr", "err");
                        Toast.makeText(Registration.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //RESIDENCE INFO FOR UPDATE FEATURE
    //getcounties
    public void getFacilities1() {
        //String curl = "https://ushauriapi.kenyahmis.org/locator/counties";
        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                z+Config.COUNTIES, null, new Response.Listener<JSONArray>() {


            @Override
            public void onResponse(JSONArray response) {


                try {


                    countiess = new ArrayList<counties>();
                    countiesList = new ArrayList<String>();

                    countiess.clear();
                    countiesList.clear();


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject service = (JSONObject) response.get(i);


                        int id = service.has("id") ? service.getInt("id") : 0;
                        String name = service.has("name") ? service.getString("name") : "";
                        int code = service.has("code") ? service.getInt("code") : 0;


                        counties newCounty = new counties(id, name, code);

                        countiess.add(newCounty);
                        countiesList.add(newCounty.getName());
                    }
                    countiess.add(new counties(0, " ", 0));
                    countiesList.add(" ");




                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            countiesList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // if (ServiceSpinner != null){
                    ServiceSpinner.setAdapter(aa);
                    //ServiceSpinner.setSelection(aa.getCount() - 1);
                    ServiceSpinner.setSelection(countyID);

                    countyID = countiess.get(aa.getCount() - 1).getId();
                    Log.d("countyID", String.valueOf(countyID));

                    ServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {


                            // serviceUnitSpinner.setAdapter(null);

                            countyID = countiess.get(position).getId();
                            //getDepartments(services.get(position).getService_id());

//
                                   /* if (serviceID !=0)
                                        Toast.makeText(Registration.this, "getting units", Toast.LENGTH_LONG).show();*/
                            try {
                                getDepartments1(countyID);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //getDepartments(serviceID);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {


                        }
                    });




                    //}

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(Registration.this, " cant get services", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                getFacilities1();
            }
        }
        ) {

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

    //SubcountyList

    public void getDepartments1(int ID) {

        String url = "https://ushauriapi.kenyahmis.org/locator/scounties?county=";
        //"https://ushauriapi.kenyahmis.org/locator/scounties?county=47";

        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, z+Config.S_COUNTIES+ID,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(Registration.this, "response", Toast.LENGTH_LONG).show();

                try {

                    scountiess = new ArrayList<scounties>();
                    scountyList = new ArrayList<String>();

                    scountiess.clear();
                    scountyList.clear();


                    //JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject serviceUnit = (JSONObject) response.get(i);

                        int id = serviceUnit.has("id") ? serviceUnit.getInt("id") : 0;
                        String name = serviceUnit.has("name") ? serviceUnit.getString("name") : "";


                        scounties newServiceUnit = new scounties(id, name);

                        scountiess.add(newServiceUnit);
                        scountyList.add(newServiceUnit.getName());
                    }

                    scountiess.add(new scounties(0, ""));
                    scountyList.add("");

                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            scountyList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    serviceUnitSpinner.setAdapter(aa);
                    // serviceUnitSpinner.setSelection(aa.getCount() - 1);
                    serviceUnitSpinner.setSelection(scountyID);

                    scountyID = scountiess.get(aa.getCount() - 1).getId();

                    serviceUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                            scountyID = scountiess.get(position).getId();
                            getWards1(scountyID);
                            //call wards here


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
                    Toast.makeText(Registration.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Registration.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Registration.this, "Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
                error.printStackTrace();
                getDepartments1(countyID);
            }
        }
        ) {
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
//call wards

    public void getWards1(int ID) {

        String url = "https://ushauriapi.kenyahmis.org/locator/wards?scounty=";
        //https://ushauriapi.kenyahmis.org/locator/wards?scounty=1
        //"https://ushauriapi.kenyahmis.org/locator/scounties?county=47";

        try {
            List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size() == 1) {
                for (int x = 0; x < _url.size(); x++) {
                    z = _url.get(x).getBase_url1();
                }
            }

        } catch (Exception e) {

        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,  z+Config.WARDS+ID,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Toast.makeText(Registration.this, "response", Toast.LENGTH_LONG).show();

                try {

                    wardss = new ArrayList<wards>();
                    wardsList = new ArrayList<String>();

                    wardss.clear();
                    wardsList.clear();


                    //JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject serviceUnit = (JSONObject) response.get(i);

                        int id = serviceUnit.has("id") ? serviceUnit.getInt("id") : 0;
                        String name = serviceUnit.has("name") ? serviceUnit.getString("name") : "";
                        int scounty_id = serviceUnit.has("scounty_id") ? serviceUnit.getInt("scounty_id") : 0;


                        wards newServiceUnit = new wards(id, name, scounty_id);

                        wardss.add(newServiceUnit);
                        wardsList.add(newServiceUnit.getName());
                    }

                    wardss.add(new wards(0, "", 0));
                    wardsList.add("");

                    ArrayAdapter<String> aa = new ArrayAdapter<String>(Registration.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            wardsList) {
                        @Override
                        public int getCount() {
                            return super.getCount(); // you dont display last item. It is used as hint.
                        }
                    };

                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    rankSpinner.setAdapter(aa);
                    // rankSpinner.setSelection(aa.getCount() - 1);

                    rankSpinner.setSelection(wardID);

                    //wardID = wardss.get(aa.getCount() - 1).getId();
                    wardID = wardss.get(aa.getCount() -1).getId();
                    rankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        //@Overide
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                            // Toast.makeText(Registration.this, "null selected", Toast.LENGTH_LONG).show();
                            wardID = wardss.get(position).getId();

                            //call wards here


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
                    Toast.makeText(Registration.this, "Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Registration.this, "Parse Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Registration.this, "Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
                error.printStackTrace();
                getWards1(scountyID);
                //getDepartments(countyID);
            }
        }
        ) {
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

}
