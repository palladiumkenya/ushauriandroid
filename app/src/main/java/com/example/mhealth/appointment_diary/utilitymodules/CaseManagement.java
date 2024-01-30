package com.example.mhealth.appointment_diary.utilitymodules;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.MainOptions;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.pmtct.ANCVisit;
import com.example.mhealth.appointment_diary.pmtct.PMTCT1;
import com.example.mhealth.appointment_diary.pmtct.PNCVisit;
import com.example.mhealth.appointment_diary.pmtct.PNCVisitStart;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CaseManagement extends AppCompatActivity {

    //update
    EditText startDate1, enddate1, other1, ccsearch;

    Spinner AssignCaseM1, provider1, rship1;

    Button save1,btn_search;
    LinearLayout search_details_layout;



    CardView card1, card2, card3;
    CheckInternet chkinternet;

    EditText csearch;
    Button ccbtn;
    EditText ccno;
    EditText fname;
    EditText lname;
    EditText other;

    Button startVisit, btn_cancel;

    EditText sex;
    EditText phone;
    LinearLayout details;

    String z, phoneurl,y;
   // EditText ccno,clinicno,fname,Mname,lname,dobi,reg, upino;
    Dialogs dialogs;
    boolean x;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(CaseManagement.this, MainOptions.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_management);

        try {
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Case Management");

        } catch (Exception e) {

        }

        chkinternet =new CheckInternet(CaseManagement.this);
        dialogs=new Dialogs(CaseManagement.this);
        details =(LinearLayout) findViewById(R.id.hei_details_layout);

        card1 = (CardView) findViewById(R.id.add_menu);
        card2 = (CardView) findViewById(R.id.homecheck);
        card3 = (CardView) findViewById(R.id.update_menu);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog =new Dialog(CaseManagement.this);
                dialog.setContentView(R.layout.fragment_case);

                Window mywindow = dialog.getWindow();
                dialog.setCanceledOnTouchOutside(true);
                mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.show();

                 csearch = dialog.findViewById(R.id.ccsearch);
                 ccbtn = dialog.findViewById(R.id.btn_search);
                 ccno = dialog.findViewById(R.id.clinicnocase);
                 fname = dialog.findViewById(R.id.fname);
                 lname = dialog.findViewById(R.id.lname);
                 other = dialog.findViewById(R.id.other);
                startVisit = dialog.findViewById(R.id.btn_startVisit);
                btn_cancel= dialog.findViewById(R.id.btn_cancel);

                 sex = dialog.findViewById(R.id.sex);
                 phone = dialog.findViewById(R.id.phone);

                 details =dialog.findViewById(R.id.cc_details_layout);



                //searchbutton

                ccbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchcc1();
                     //   Toast.makeText(CaseManagement.this, "great", Toast.LENGTH_LONG).show();
                    }
                });

                //stat visit
                startVisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sendCC =ccno.getText().toString();
                        Intent intent = new Intent(CaseManagement.this, AddCaseManager.class);
                        intent.putExtra("Client_CCC", sendCC);
                        startActivity(intent);


                        ccno.setText("");
                        fname.setText("");
                        other.setText("");
                        lname.setText("");
                        phone.setText("");
                        sex.setText("");
                        details.setVisibility(View.GONE);
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });


                //         getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer1, new CaseManager()).commit();
            }
        });

        //checklist
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog =new Dialog(CaseManagement.this);
                dialog.setContentView(R.layout.fragment_checklist);

                Window mywindow = dialog.getWindow();
                dialog.setCanceledOnTouchOutside(true);
                mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.show();

                csearch = dialog.findViewById(R.id.ccsearch);
                ccbtn = dialog.findViewById(R.id.btn_search);
                ccno = dialog.findViewById(R.id.clinicnocheck);
                fname = dialog.findViewById(R.id.fname);
                lname = dialog.findViewById(R.id.lname);
                other = dialog.findViewById(R.id.other);
                startVisit = dialog.findViewById(R.id.btn_startVisit);

                sex = dialog.findViewById(R.id.sex);
                phone = dialog.findViewById(R.id.phone);

                details =dialog.findViewById(R.id.cc_details_layout);
                btn_cancel= dialog.findViewById(R.id.btn_cancel);



                //searchbutton

                ccbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchcc1();
                        //   Toast.makeText(CaseManagement.this, "great", Toast.LENGTH_LONG).show();
                    }
                });

                //stat visit
                startVisit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String sendCC =ccno.getText().toString();
                        Intent intent = new Intent(CaseManagement.this, HomeVisitChecklist.class);
                        intent.putExtra("Client_CCC", sendCC);
                        startActivity(intent);

                        ccno.setText("");
                        fname.setText("");
                        other.setText("");
                        lname.setText("");
                        phone.setText("");
                        sex.setText("");
                        details.setVisibility(View.GONE);
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });


            }
        });

        //update
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog =new Dialog(CaseManagement.this);
                dialog.setContentView(R.layout.fragment_update_case);

                Window mywindow = dialog.getWindow();
                dialog.setCanceledOnTouchOutside(true);
                mywindow.setLayout(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.show();

                AssignCaseM1 = (Spinner) dialog.findViewById(R.id.AssignCaseM);
                provider1 =(Spinner) dialog.findViewById(R.id.provider);
                rship1 =(Spinner) dialog.findViewById(R.id.rship);

                save1 =(Button) dialog.findViewById(R.id.savebtn);


                startDate1 =(EditText) dialog.findViewById(R.id.startDate);
                enddate1 =(EditText) dialog.findViewById(R.id.endDate);
                other1 =(EditText) dialog.findViewById(R.id.other);

                ccsearch=(EditText) dialog.findViewById(R.id.ccsearch);
                btn_search=(Button) dialog.findViewById(R.id.btn_search);
                search_details_layout= (LinearLayout) dialog.findViewById(R.id.search_details_layout);


                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        search_details_layout.setVisibility(View.VISIBLE);

                    }
                });



            }
        });
    }

    public void  searchcc1(){

       if (csearch.getText().toString().isEmpty()){
            Toast.makeText(CaseManagement.this, "Enter CCC Number", Toast.LENGTH_LONG).show();
        }
        else if (!chkinternet.isInternetAvailable()){

            Toast.makeText(CaseManagement.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

        }

        else{
            searchCCC2();
         //  searchCCC1();
        }
        // details.setVisibility(View.VISIBLE);

   // }
        //searchCCC2();
    }



    public  void searchCCC2(){
        if (chkinternet.isInternetAvailable()) {

            List<Activelogin> al = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin limit 1");
            for (int x = 0; x < al.size(); x++) {
                String myuname = al.get(x).getUname();
                List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
                for (int y = 0; y < myl.size(); y++) {

                    phoneurl = myl.get(y).getPhone();

                }
            }

            try {
                List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                if (_url.size() == 1) {
                    for (int x = 0; x < _url.size(); x++) {
                        z = _url.get(x).getBase_url1();
                    }
                }

            } catch (Exception e) {

            }


            String tt1 = "?phone_no=" + phoneurl;
            String urls1 = "&clinic_number=" + csearch.getText().toString();
         //   String urls1 = "&clinic_number=" + 1234500001;

            // Create a JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, z + Config.SERCH_CLIENT + tt1+urls1, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the JSON response
                            try {
                                boolean success = response.getBoolean("success");
                                if (success) {
                                    details.setVisibility(View.VISIBLE);
                                    // Extract data if success is true
                                    JSONObject data = response.getJSONObject("data");

                                    // Now you can use the data as per your requirement


                                    String clinicnumber = data.getString("clinic_number");
                                    String f_name = data.getString("f_name");
                                    String m_name = data.getString("m_name");
                                    String l_name = data.getString("l_name");
                                   // String currentregimen = data.getString("currentregimen");
                                    String sex1 = data.getString("gender");
                                    String upi_no = data.getString("phone_no");


                                    ccno.setText(clinicnumber);
                                    fname.setText(f_name);
                                    other.setText(m_name);
                                    lname.setText(l_name);
                                    phone.setText(upi_no);
                                    sex.setText(sex1);
                                    // ... and so on for other fields


                                }else{
                                    ccno.setText("");
                                    fname.setText("");
                                    other.setText("");
                                    lname.setText("");
                                    phone.setText("");
                                    sex.setText("");

                                    details.setVisibility(View.GONE);


                                        String message = response.getString("message");

                                        // Check the "success" value and display the "message" accordingly

                                            // Successful response
                                            // Display or handle the success message
                                          //  showToast(message); // Replace with your display logic
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Handle the JSON parsing error
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ccno.setText("");
                            fname.setText("");
                            other.setText("");
                            lname.setText("");
                            phone.setText("");
                            sex.setText("");

                            details.setVisibility(View.GONE);
                            // Handle the error
                            if (error.networkResponse != null && error.networkResponse.data != null) {
                                try {
                                    // Convert error response data to JSON object
                                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));

                                    // Now you can extract information from the JSON object
                                    boolean success = jsonObject.optBoolean("success");
                                    String message = jsonObject.optString("message");

                                    // Handle the error message or any other information
                                    Log.e("ErrorResponse", "Success: " + success + ", Message: " + message);
                                    Toast.makeText(CaseManagement.this, message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // Handle other types of errors (e.g., network error or timeout)
                                Log.e("ErrorResponse", "An error occurred: " + error.getMessage());

                                Toast.makeText(CaseManagement.this, "An error occurred: " + " "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                           // Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

// Add the request to the RequestQueue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }

    public void search_update(){

        if (chkinternet.isInternetAvailable()) {

            List<Activelogin> al = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin limit 1");
            for (int x = 0; x < al.size(); x++) {
                String myuname = al.get(x).getUname();
                List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
                for (int y = 0; y < myl.size(); y++) {

                    phoneurl = myl.get(y).getPhone();

                }
            }

            try {
                List<UrlTable> _url = UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                if (_url.size() == 1) {
                    for (int x = 0; x < _url.size(); x++) {
                        z = _url.get(x).getBase_url1();
                    }
                }

            } catch (Exception e) {

            }


            String tt2 = "&phone_no=" + phoneurl;
            String urls2 = "?clinic_number=" + csearch.getText().toString();
            //   String urls1 = "&clinic_number=" + 1234500001;

          //  https://ushauriapi.kenyahmis.org/case/search/?clinic_number=1234500001&phone_no=0718373569

            // Create a JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, z + Config.SERCH_UPDATE_CLIENT + tt2+urls2, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the JSON response
                            try {
                                boolean success = response.getBoolean("success");
                                if (success) {
                                    details.setVisibility(View.VISIBLE);
                                    // Extract data if success is true
                                    JSONObject data = response.getJSONObject("data");

                                    // Now you can use the data as per your requirement


                                    String clinicnumber = data.getString("clinic_number");
                                    String f_name = data.getString("f_name");
                                    String m_name = data.getString("m_name");
                                    String l_name = data.getString("l_name");
                                    // String currentregimen = data.getString("currentregimen");
                                    String sex1 = data.getString("gender");
                                    String upi_no = data.getString("phone_no");


                                    ccno.setText(clinicnumber);
                                    fname.setText(f_name);
                                    other.setText(m_name);
                                    lname.setText(l_name);
                                    phone.setText(upi_no);
                                    sex.setText(sex1);
                                    // ... and so on for other fields


                                }else{
                                    ccno.setText("");
                                    fname.setText("");
                                    other.setText("");
                                    lname.setText("");
                                    phone.setText("");
                                    sex.setText("");

                                    details.setVisibility(View.GONE);


                                    String message = response.getString("message");

                                    // Check the "success" value and display the "message" accordingly

                                    // Successful response
                                    // Display or handle the success message
                                    //  showToast(message); // Replace with your display logic
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Handle the JSON parsing error
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ccno.setText("");
                            fname.setText("");
                            other.setText("");
                            lname.setText("");
                            phone.setText("");
                            sex.setText("");

                            details.setVisibility(View.GONE);
                            // Handle the error
                            if (error.networkResponse != null && error.networkResponse.data != null) {
                                try {
                                    // Convert error response data to JSON object
                                    JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));

                                    // Now you can extract information from the JSON object
                                    boolean success = jsonObject.optBoolean("success");
                                    String message = jsonObject.optString("message");

                                    // Handle the error message or any other information
                                    Log.e("ErrorResponse", "Success: " + success + ", Message: " + message);
                                    Toast.makeText(CaseManagement.this, message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // Handle other types of errors (e.g., network error or timeout)
                                Log.e("ErrorResponse", "An error occurred: " + error.getMessage());

                                Toast.makeText(CaseManagement.this, "An error occurred: " + " "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            // Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

// Add the request to the RequestQueue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }
}