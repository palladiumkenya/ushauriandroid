package com.example.mhealth.appointment_diary.utilitymodules;

import static android.R.layout.simple_spinner_item;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.SelectUrls;
import com.example.mhealth.appointment_diary.models.providerModel;
import com.example.mhealth.appointment_diary.pmtct.PNCVisit;
import com.example.mhealth.appointment_diary.pmtct.PNCVisitStart;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.urlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCaseManager extends AppCompatActivity {

    CheckInternet chkinternet;
    Dialogs dialogs;

    EditText startDate1, enddate1, other1;

    Spinner AssignCaseM1, provider1, rship1;

    Button save1;
    String[] ReasonS = {"--Select Reason--", "New Client", "Transfer In", "High VL", "Defaulters", "High VL", "LTFU", "High VL", "Co Infected", "Other Specify"};

    String[] RshipS = {"--Select Relationship--", "Case Manager"};
    private String REASON = "";
    String REASON_code, RSHIP_code, providername_code;

    private String RSHIP = "";
    String providername;

    String newCC;

    String  phone_no1;


    providerModel provider_Model;
    ArrayList<String> providerModelArrayList;
    ArrayList<providerModel> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case_manager);
        dialogs=new Dialogs(AddCaseManager.this);

        chkinternet =new CheckInternet(AddCaseManager.this);
        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no1=myl2.get(y).getPhone();
            }
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newCC= null;
            } else {
                newCC= extras.getString("Client_CCC");
            }
        } else {
            newCC= (String) savedInstanceState.getSerializable("Client_CCC");
        }




        try {
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Case Manager");

        } catch (Exception e) {

        }

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
                       // startDate1.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        startDate1.setText(year+"-" +(month + 1)+ "-"+dayOfMonth);
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
                  //      enddate1.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                        enddate1.setText(year+"-" +(month + 1)+ "-"+dayOfMonth);
                    }
                }, year, month, day);

                // set maximum date to be selected as today
                datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
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

                REASON_code= Integer.toString(position);
                if (REASON.contentEquals("Other Specify")){
                    other1.setVisibility(View.VISIBLE);

                }else{
                    other1.setVisibility(View.GONE);
                    other1.setText("");
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

                RSHIP_code = Integer.toString(position);
                //      HIV_status_Code = Integer.toString(position);

            //    Toast.makeText(AddCaseManager.this, "Data is"+RSHIP, Toast.LENGTH_SHORT).show();

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

                if (REASON_code.contentEquals("0")){
                    Toast.makeText(AddCaseManager.this, "Specify Reason for Assigning a Case Manager", Toast.LENGTH_SHORT).show();

                }else if(RSHIP_code.contentEquals("0")){
                    Toast.makeText(AddCaseManager.this, "Specify Relationship", Toast.LENGTH_SHORT).show();

                }else if(providername_code.contentEquals("0")){
                    Toast.makeText(AddCaseManager.this, "Choose Provider", Toast.LENGTH_SHORT).show();
                }
                else if(startDate1.getText().toString().isEmpty()){
                    Toast.makeText(AddCaseManager.this, "Enter Start Date", Toast.LENGTH_SHORT).show();
                }else if (enddate1.getText().toString().isEmpty()){
                    Toast.makeText(AddCaseManager.this, "Enter End Date", Toast.LENGTH_SHORT).show();
                }

                else if (!chkinternet.isInternetAvailable()){

                    Toast.makeText(AddCaseManager.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                }
                else {


                    send(phone_no1, newCC, REASON, other1.getText().toString(),  providername_code, RSHIP, startDate1.getText().toString(), enddate1.getText().toString());
                    Log.d("DATA", phone_no1+ newCC+ REASON+other1.getText().toString()+ RSHIP+  providername+ startDate1.getText().toString()+ enddate1.getText().toString());
                    AssignCaseM1.setSelection(0);
                    provider1.setSelection(0);
                    rship1.setSelection(0);
                    startDate1.setText("");
                    enddate1.setText("");

                }

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

                            providername_code= Integer.toString(position);

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




    private  void send(String phone1, String ccno1, String REASON11,String other111,String prov11, String RSHIP11, String startDate111, String enddate111){
        RequestQueue queue = Volley.newRequestQueue(this);
        String saveurl ="https://ushauriapi.kenyahmis.org/case/assign";
        JSONObject payload = new JSONObject();
        try {
            payload.put("phone_no", phone1);
            payload.put("clinic_number", ccno1);
            payload.put("reason_assign", REASON11);
            payload.put("other_reason", other111);
            payload.put("provider_id", prov11);
            payload.put("relationship", RSHIP11);
            payload.put("start_date", startDate111);
            payload.put("end_date", enddate111);







        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, saveurl, payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean success = response.getBoolean("success");
                    String message = response.getString("message");

                    // Check the "success" value and display the "message" accordingly
                    if (success) {
                        // Successful response
                        // Display or handle the success message




                        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(AddCaseManager.this);
                        builder1.setIcon(R.drawable.nascoplogonew);
                        builder1.setTitle(message);
                        builder1.setMessage( "Server Response");
                        builder1.setCancelable(false);

                        builder1.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {



                                        Intent intent = new Intent(AddCaseManager.this, CaseManagement.class);

                                        startActivity(intent);
                                        dialog.dismiss();







                                        //dialog.cancel();
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();



                        //         showToast(message); // Replace with your display logic
                    } else {
                        // Unsuccessful response
                        // Display or handle the error message
                       // showToast(message); // Replace with your display logic
                        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(AddCaseManager.this);
                        builder1.setIcon(R.drawable.nascoplogonew);
                        builder1.setTitle(message);
                        builder1.setMessage( "Server Response");
                        builder1.setCancelable(false);

                        builder1.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {



                                        Intent intent = new Intent(AddCaseManager.this, CaseManagement.class);

                                        startActivity(intent);
                                        dialog.dismiss();







                                        //dialog.cancel();
                                    }
                                });


                        AlertDialog alert11 = builder1.create();
                        alert11.show();


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle JSON parsing error
                }
 //               Toast.makeText(AddCaseManager.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error response
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        // Convert error response data to JSON object
                        JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));

                        // Now you can extract information from the JSON object
                        boolean success = jsonObject.optBoolean("success");
                        String message = jsonObject.optString("message");

                        // Handle the error message or any other information
                        Log.e("ErrorResponse", "Success: " + success + ", Message: " + message);
                        Toast.makeText(AddCaseManager.this, message, Toast.LENGTH_SHORT).show();
                       // dialogs.showErrorDialog(message)
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle other types of errors (e.g., network error or timeout)
                    Log.e("ErrorResponse", "An error occurred: " + error.getMessage());

                    Toast.makeText(AddCaseManager.this, "An error occurred: " + " "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                }

               // Log.d("Errors", error.toString());
               // Toast.makeText(AddCaseManager.this, "Error"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

       // RequestQueue requestQueue = Volley.newRequestQueue(AddCaseManager.this);
        queue.add(jsonObjectRequest);

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}