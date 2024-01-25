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
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.pmtct.ANCVisit;
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

    CardView card1, card2;
    CheckInternet chkinternet;

    EditText csearch;
    Button ccbtn;
    EditText ccno;
    EditText fname;
    EditText lname;
    EditText other;

    Button startVisit;

    EditText sex;
    EditText phone;
    LinearLayout details;

    String z, phoneurl,y;
   // EditText ccno,clinicno,fname,Mname,lname,dobi,reg, upino;
    Dialogs dialogs;
    boolean x;


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
           searchCCC();}
        // details.setVisibility(View.VISIBLE);

   // }

    }

    private void  searchCCC() {

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
            String urls1 = "?ccc=" + csearch.getText().toString();
            String tt1 = "&phone_number=" + phoneurl;
            //z+ Config.CALENDER_LIST+urls+tt,
            //  String url1 ="https://ushauriapi.kenyahmis.org/pmtct/search?ccc=1305701529&phone_number=0780888928";
            //https://ushauriapi.kenyahmis.org/pmtct/search?ccc=1305701529&phone_number=0780888928
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, z + Config.SEARCHANCPNC + urls1 + tt1, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    //Toast.makeText(ANCVisit.this, "SUCCESS", Toast.LENGTH_SHORT).show();


                    details.setVisibility(View.VISIBLE);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                    /*x = jsonObject.getBoolean("success");
                    if (x==true){*/

                            String clinicnumber = jsonObject.getString("clinic_number");
                            String f_name = jsonObject.getString("f_name");
                            String m_name = jsonObject.getString("m_name");
                            String l_name = jsonObject.getString("l_name");
                            String currentregimen = jsonObject.getString("currentregimen");
                            String dob = jsonObject.getString("dob");
                            String upi_no = jsonObject.getString("upi_no");


                            ccno.setText(clinicnumber);
                            fname.setText(f_name);
                            other.setText(m_name);
                            lname.setText(l_name);
                        //    reg.setText(currentregimen);
                         //   dobi.setText(dob);
                            phone.setText(upi_no);
                            Log.d("phone no", upi_no);
                            Log.d("DOB", dob);
                            Log.d("CURRENT REGIMEN",currentregimen);
                            Log.d("phone no", upi_no);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int j = 0; j < response.length(); j++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                x = jsonObject.getBoolean("success");
                                y= jsonObject.getString("message");

                                if (!x){
                                    dialogs.showSuccessDialog(y, "Server Response");
                                }
                            }

                            catch (Exception e){
                                e.printStackTrace();
                            }}

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if(response != null && response.data != null){
                        String body;

                        String statusCode = String.valueOf(error.networkResponse.statusCode);

                        if(error.networkResponse.data!=null) {
                            try {
                                JSONArray jsonArray =new JSONArray(error.networkResponse.data);
                                body = new String(error.networkResponse.data,"UTF-8");

                                JSONObject json = new JSONObject(body);

                                String message = json.has("message") ? json.getString("message") : "";
                                dialogs.showErrorDialog(message, "Server");

                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }else {

                        Log.e("VOlley error :", error.getLocalizedMessage()+" message:"+error.getMessage());
                        // dialogs.showErrorDialog(VolleyErrors.getVolleyErrorMessages(error, ANCVisit.this), "Server Response");
                        dialogs.showErrorDialog("Invalid CCC Number", "Server Response");
                        //  Toast.makeText(ANCVisit.this, VolleyErrors.getVolleyErrorMessages(error, ANCVisit.this),Toast.LENGTH_LONG).show();
                    }




                    details.setVisibility(View.GONE);
                    //Toast.makeText(ANCVisit.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(CaseManagement.this);
            requestQueue.add(jsonArrayRequest);
        }

    }
}