package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.utilitymodules.Registration;
import com.example.mhealth.appointment_diary.utilitymodules.Unit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class RequestKDOD extends AppCompatActivity {
   CardView card_next, card_unassigned;
   TextView nims;

   RecyclerView recyclerView;
   Adapter_dod_get adapter_dod_get;
   List<DOD_nums> numsList;
   String mflcode = "";
   EditText editTextKDOD;
   Button btnsubmit,btncancel;
   String x ="";
   int jj;
   int un;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_kdod);

        editTextKDOD = findViewById(R.id.editKDOD);
        btnsubmit = findViewById(R.id.kdodsubmit);
        btncancel =findViewById(R.id.btncancel);

        numsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recKDOD);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter_dod_get adapter_dod_get =new Adapter_dod_get(this, numsList);
        recyclerView.setAdapter(adapter_dod_get);
       // nims = findViewById(R.id.t1);
        editTextKDOD.setEnabled(false);


        card_next = findViewById(R.id.kdod_next);
        card_unassigned = findViewById(R.id.kdod_unass);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                postkdod();
                editTextKDOD.setText("");

                String  z =editTextKDOD.getText().toString();


            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextKDOD.setText("");
                finish();
            }
        });
        card_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //try1();
                getUnassigned();

            }
        });


    }

    //}

    private void try1(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/";

        //begin mfl
        List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");

        for (int x = 0; x < myl.size(); x++) {

            mflcode = myl.get(x).getMfl();
        }

        //end mfl
      JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url+mflcode, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(Integer.parseInt(String.valueOf(i)));
                        //JSONArray data = response.getJSONArray(Integer.parseInt(String.valueOf(i)));

                        //begin
                        jj = jsonObject.getInt("kdod_num");

                        editTextKDOD.setText(String.valueOf(jj));
                        editTextKDOD.setVisibility(View.VISIBLE);
                        btnsubmit.setVisibility(View.VISIBLE);
                        btncancel.setVisibility(View.VISIBLE);

                    }

                }
                catch (JSONException e) {
                  e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(RequestKDOD.this,"Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(RequestKDOD.this,"Parse Error", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError) {
                    Toast.makeText(RequestKDOD.this,"Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
               // error.printStackTrace();

            }
        });
requestQueue.add(jsonArrayRequest);

    }

    private void getUnassigned(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/pkdod/";

        //get mfl

        List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");

            for (int x = 0; x < myl.size(); x++) {

                mflcode = myl.get(x).getMfl();
                //Integer.parseInt(mflcode);

            }
            // get mfl


        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url+mflcode,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(RequestKDOD.this, "nullss", Toast.LENGTH_SHORT).show();

                try{
                    un = response.getInt("kdod_num");

                    //if second API response is null call the first api
                    if (response.isNull("null")){
                        try1();
                    }

                    else{

                        editTextKDOD.setText(String.valueOf(un));
                        editTextKDOD.setVisibility(View.VISIBLE);
                        btnsubmit.setVisibility(View.VISIBLE);
                        btncancel.setVisibility(View.VISIBLE);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(RequestKDOD.this, "Error occured", Toast.LENGTH_SHORT).show();
                if (error instanceof NetworkError) {
                    Toast.makeText(RequestKDOD.this,"Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(RequestKDOD.this,"Parse Error", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError) {
                    Toast.makeText(RequestKDOD.this,"Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }

               // error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    private void postkdod(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/ukdod/";

        //get mfl

        List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");
        //String mflcode = "";

        for (int x = 0; x < myl.size(); x++) {

            mflcode = myl.get(x).getMfl();
            //Integer.parseInt(mflcode);

        }
        //end get mfl

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+editTextKDOD.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RequestKDOD.this, "successful", Toast.LENGTH_SHORT).show();
                if(response !=null) {

                    //try alert
                    AlertDialog alert = new AlertDialog.Builder(RequestKDOD.this).
                            setTitle("")
                            .setMessage(response).setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    editTextKDOD.setText("");


                                }
                            }).setNegativeButton("", null).show();
                }
                //end alert

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(RequestKDOD.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
               // params.put("kdod_num", editTextKDOD.getText().toString());
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);

    }

    private void unsign(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/pkdod/";
        //get mfl


        List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");

        for (int x = 0; x < myl.size(); x++) {

            mflcode = myl.get(x).getMfl();
            //Integer.parseInt(mflcode);

        }
        // get mfl
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + mflcode,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RequestKDOD.this, "Unsuccessful", Toast.LENGTH_SHORT).show();

                if (response.equals(null)){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(RequestKDOD.this,"Network Error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(RequestKDOD.this,"Parse Error", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError) {
                    Toast.makeText(RequestKDOD.this,"Server Error", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }

                //Toast.makeText(RequestKDOD.this, "onerror", Toast.LENGTH_SHORT).show();
                //error.printStackTrace();

            }
        });
        requestQueue.add(stringRequest);

    }

}

