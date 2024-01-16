package com.example.mhealth.appointment_diary.utilitymodules;

import static android.R.layout.simple_spinner_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.SelectUrls;
import com.example.mhealth.appointment_diary.models.providerModel;
import com.example.mhealth.appointment_diary.pmtct.PNCVisitStart;
import com.example.mhealth.appointment_diary.tables.urlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCaseManager extends AppCompatActivity {

    EditText startDate1, enddate1, other1;

    Spinner AssignCaseM1, provider1, rship1;

    Button save1;
    String[] ReasonS = {"--Select Reason--", "New Client", "Transfer In", "High VL", "Defaulters", "High VL", "LTFU", "High VL", "Co Infected", "Other Specify"};

    String[] RshipS = {"--Select Relationship--", "Case Manager"};
    private String REASON = "";

    private String RSHIP = "";
    String providername;


    providerModel provider_Model;
    ArrayList<String> providerModelArrayList;
    ArrayList<providerModel> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case_manager);

        Toolbar toolbar = findViewById(R.id.toolbar);

    //    setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assign CaseManager");
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Handle the back arrow click to navigate to the previous activity
            }
        });

        AssignCaseM1 = (Spinner) findViewById(R.id.AssignCaseM);
        provider1 =(Spinner) findViewById(R.id.provider);
        rship1 =(Spinner) findViewById(R.id.rship);

        save1 =(Button) findViewById(R.id.savebtn);


        startDate1 =(EditText) findViewById(R.id.startDate);
        enddate1 =(EditText) findViewById(R.id.endDate);
        other1 =(EditText) findViewById(R.id.other);

        startDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(AddCaseManager.this  , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        startDate1.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();

            }
        });

        enddate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(AddCaseManager.this  , new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        enddate1.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePicker.getDatePicker();

                // show the dialog
                datePicker.show();

            }
        });


        // Reason for assigning CM
        ArrayAdapter<String> reasonAdapter = new ArrayAdapter<String>(AddCaseManager.this, android.R.layout.simple_spinner_item, ReasonS);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AssignCaseM1.setAdapter(reasonAdapter);
        AssignCaseM1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                REASON =ReasonS[position];
          //      HIV_status_Code = Integer.toString(position);

            //    Toast.makeText(AddCaseManager.this, "Data is"+REASON, Toast.LENGTH_SHORT).show();

                //hivResultL1
                if (REASON.contentEquals("Other Specify")){
                    other1.setVisibility(View.VISIBLE);

                }else{
                    other1.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Rships

        ArrayAdapter<String> rshipAdapter = new ArrayAdapter<String>(AddCaseManager.this, android.R.layout.simple_spinner_item, RshipS);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rship1.setAdapter(rshipAdapter);
        rship1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                RSHIP =RshipS[position];
                //      HIV_status_Code = Integer.toString(position);

                Toast.makeText(AddCaseManager.this, "Data is"+RSHIP, Toast.LENGTH_SHORT).show();

                //hivResultL1

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getproviders();

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send(REASON, RSHIP, other1.getText().toString(), startDate1.getText().toString(), enddate1.getText().toString());
               // String REASON, String RSHIP, EditText other1, EditText startDate1, EditText enddate1


            }
        });

    }

    public void getproviders(){

        //String URLstring = "https://ushaurinode.mhealthkenya.co.ke/config";
        //String URLstring ="https://ushauriapi.kenyahmis.org/case/assign";
        String URLstring ="https://ushauriapi.kenyahmis.org/user/provider?facility_id=12345";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLstring, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("", response.toString());

                try {
                    providerModelArrayList = new ArrayList<>();
                    names = new ArrayList<>();

                    providerModelArrayList.clear();
                    names.clear();


                 //   JSONArray jsonArray =response.getJSONArray("USHAURI");

                    for (int i =0; i<response.length(); i++){

                        JSONObject jsonObject =response.getJSONObject(i);

                        int id = jsonObject.getInt("id");
                        String fullname =jsonObject.getString("full_name");
                        int facility_id =jsonObject.getInt("facility_id");
                        String phone_no =jsonObject.getString("phone_no");


                        provider_Model = new providerModel(id, facility_id,fullname, phone_no);
                        names.add(provider_Model);
                        providerModelArrayList.add(provider_Model.getFull_name());

                    }



                    names.add(new providerModel(0, 0,  "", "--Select the system to connect to--"));
                    providerModelArrayList.add("--Select the provider--");


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(AddCaseManager.this, simple_spinner_item, providerModelArrayList);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    provider1.setAdapter(spinnerArrayAdapter);
                    //removeSimpleProgressDialog();

                    provider1.setSelection(spinnerArrayAdapter.getCount()-1);
                 //   dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();


                    provider1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                      //      dataId = names.get(position).getId();

                       //     if (dataId==1){

                            providername = names.get(position).getFull_name();

                       //         stage_name =names.get(position).getStage();

                      //      }

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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(AddCaseManager.this);
        requestQueue.add(jsonArrayRequest);
    }

    private  void send(String REASON, String RSHIP, String other1, String startDate1, String enddate1){
        RequestQueue queue = Volley.newRequestQueue(this);
        String saveurl ="https://ushauriapi.kenyahmis.org/case/assign";
        JSONObject payload = new JSONObject();
        try {

            payload.put("phone_no", "0718373567");
            payload.put("clinic_number", 1234500022);
            payload.put("reason_assign", REASON);
            payload.put("other_reason", other1);
            payload.put("provider_id", 1573);
            payload.put("relationship", RSHIP);
            payload.put("start_date", startDate1);
            payload.put("end_date", enddate1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, saveurl, payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(AddCaseManager.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Errors", error.toString());
                Toast.makeText(AddCaseManager.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();


            }
        });

       // RequestQueue requestQueue = Volley.newRequestQueue(AddCaseManager.this);
        queue.add(jsonObjectRequest);

    }



}