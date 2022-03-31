package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.utilitymodules.Unit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class RequestKDOD extends AppCompatActivity {
   CardView card_next, card_unassigned;
   TextView nims;

   RecyclerView recyclerView;
   Adapter_dod_get adapter_dod_get;
   List<DOD_nums> numsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_kdod);
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


        card_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try1();
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
        String url = "https://ushauri-api.mod.go.ke/kdod/2";
        //String url2 = "https://ushauri-api.mod.go.ke/kdod/pkdod/2";

      JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try{
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(Integer.parseInt(String.valueOf(i)));
                        //JSONArray data = response.getJSONArray(Integer.parseInt(String.valueOf(i)));

                        //begin
                        int jj = jsonObject.getInt("kdod_num");
                        //nims.append("Available KDOD number is:"+ " "+String.valueOf(jj));

                        AlertDialog alert = new AlertDialog.Builder(RequestKDOD.this).
                                setTitle("Available KDOD number is:")
                                .setMessage(String.valueOf(jj)).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("", null).show();

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


}

