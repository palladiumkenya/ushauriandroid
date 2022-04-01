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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.tables.Mflcode;
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
   Button btnsubmit;
   String x ="";
   int jj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_kdod);
        editTextKDOD = findViewById(R.id.editKDOD);
        btnsubmit = findViewById(R.id.kdodsubmit);
        numsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recKDOD);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter_dod_get adapter_dod_get =new Adapter_dod_get(this, numsList);
        recyclerView.setAdapter(adapter_dod_get);
        nims = findViewById(R.id.t1);

       // try1();
       // getData();

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
        card_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //try1();
                getUnassigned();
               // Toast.makeText(RequestKDOD.this, "null", Toast.LENGTH_SHORT).show();

            }
        });


    }




    private void retrieve() {
        /*RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/2";
        //String url ="https://ushauri-api.mod.go.ke/kdod/pkdod/2";

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        DOD_nums dod_nums = new DOD_nums();
                        dod_nums.setKdod_num(jsonObject.getInt("dod_nums"));
                        numsList.add(dod_nums);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RequestKDOD.this, "null", Toast.LENGTH_SHORT).show();

            }
        });*/
    }


    //}
    private void getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/2";


    }
    private void try1(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/";
        //String url2 = "https://ushauri-api.mod.go.ke/kdod/pkdod/2";

        //begin mfl

        List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");
        //String mflcode = "";

        for (int x = 0; x < myl.size(); x++) {

            mflcode = myl.get(x).getMfl();
            //Integer.parseInt(mflcode);

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
                        //nims.append("Available KDOD number is:"+ " "+String.valueOf(jj));

                       /* AlertDialog alert = new AlertDialog.Builder(RequestKDOD.this).
                                setTitle("Available KDOD number is:")
                                .setMessage(String.valueOf(jj)).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                //getUnassigned();

                            }
                        }).setNegativeButton("", null).show();*/

                        //end





                        //begin
                        //DOD_nums dod_nums = new DOD_nums();
                        //dod_nums.setKdod_num(Integer.parseInt(String.valueOf(jj)));
                        //numsList.add(dod_nums);
                        //end

                       // Toast.makeText(RequestKDOD.this, "null", Toast.LENGTH_SHORT).show();

                    }

                }
                catch (JSONException e) {
                  e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
requestQueue.add(jsonArrayRequest);

    }

    private void getUnassigned(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        String url = "https://ushauri-api.mod.go.ke/kdod/pkdod/";
        //String url2 = "https://ushauri-api.mod.go.ke/kdod/pkdod/2";

        //////////get mfl


            List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");


            for (int x = 0; x < myl.size(); x++) {

                mflcode = myl.get(x).getMfl();
                //Integer.parseInt(mflcode);

            }



        ///////////// get mfl


        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url+mflcode, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(RequestKDOD.this, "nullss", Toast.LENGTH_SHORT).show();


                try{
                    //for (int i = 0; i < response.length(); i++){
                        //JSONObject jsonObject = response.getJSONObject(String.valueOf(i));
                        //JSONObject jsonObject = response.getJSONObject("kdod_num");

                        //JSONObject jsonObject = response.getJSONObject(String.valueOf(Integer.parseInt(String.valueOf(i))));
                        //JSONArray data = response.getJSONArray(Integer.parseInt(String.valueOf(i)));

                        //begin
                    //response = response.getJSONObject("kdod_num");
                    int un = response.getInt("kdod_num");
                    if (un!=0){
                     try1();
                    }

                     //x= response.getString("kdod_num");
                    //editTextKDOD.setText(x);
                        //int kk = jsonObject.getInt("kdod_num");


                       // nims.append("Unassigned KDOD number is:"+ " "+String.valueOf(un));
                        //Toast.makeText(RequestKDOD.this, "nullss"+un, Toast.LENGTH_SHORT).show();


                        /*AlertDialog alert = new AlertDialog.Builder(RequestKDOD.this).
                                setTitle("Unassigned KDOD number is:")
                                .setMessage(String.valueOf(un)).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("", null).show();*/

                        //end





                        //begin
                        //DOD_nums dod_nums = new DOD_nums();
                        //dod_nums.setKdod_num(Integer.parseInt(String.valueOf(jj)));
                        //numsList.add(dod_nums);
                        //end

                        // Toast.makeText(RequestKDOD.this, "null", Toast.LENGTH_SHORT).show();



                }
                catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RequestKDOD.this, "nullable", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    private void postkdod(){
        RequestQueue requestQueue = Volley.newRequestQueue(RequestKDOD.this);
        //String url = "https://ushauri-api.mod.go.ke/kdod/ukdod/4001";
        String url = "https://ushauri-api.mod.go.ke/kdod/ukdod/";

        //get mfl

        List<Mflcode> myl = Mflcode.findWithQuery(Mflcode.class, "select * from Mflcode limit 1");
        //String mflcode = "";

        for (int x = 0; x < myl.size(); x++) {

            mflcode = myl.get(x).getMfl();
            //Integer.parseInt(mflcode);

        }

        //end get mfl




        StringRequest stringRequest = new StringRequest(Request.Method.POST, url+mflcode, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                //try alert
                AlertDialog alert = new AlertDialog.Builder(RequestKDOD.this).
                        setTitle(" KDOD number:"+jj)
                        .setMessage("updated successful").setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();


                            }
                        }).setNegativeButton("", null).show();
                //end alert



                //Toast.makeText(RequestKDOD.this, "succesful", Toast.LENGTH_SHORT).show();
                //editTextKDOD.setText("");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RequestKDOD.this, "Unsuccesful", Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kdod_num", editTextKDOD.getText().toString());
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);

    }

}

