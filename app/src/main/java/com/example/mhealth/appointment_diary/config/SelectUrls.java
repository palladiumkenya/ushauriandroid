package com.example.mhealth.appointment_diary.config;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.tables.urlModel;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import static android.R.layout.simple_spinner_item;
public class SelectUrls extends AppCompatActivity {


    urlModel url_Model;
    ArrayList<String> urlModelArrayList;
    ArrayList<urlModel> names;
    Spinner spinner1;

    public String z, zz;

    int dataId;
    String base_url;
    String stage_name;
    SharedPreferences sharedPreferences1;
    Button btn_prcd;

   /* @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_urls);
        setScreen();


        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();



        btn_prcd = findViewById(R.id.login_proceed);

         spinner1 =findViewById(R.id.spCompany);
         geturls1();


         btn_prcd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                try {
                    //UrlTable urlTable =new UrlTable(base_url, stage_name);
                    UrlTable urlTable =new UrlTable("https://ushauriapi.nascop.org", "Facility live server");
                    urlTable.save();
                }catch(Exception e){

                }
                 /*UrlTable urlTable =new UrlTable(base_url, stage_name);
                 urlTable.save();*/

                 try {

                     UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
                     z= _url.base_url1;
                     zz =_url.stage_name1;

                 }catch(Exception e){
                     //e.printStackTrace();
                 }

                /* UrlTable _url = SugarRecord.findById(UrlTable.class, 1);
                 z= _url.base_url1;
                 zz =_url.stage_name1;*/



                /* editor.putString("keyName", base_url);
                 editor.apply();

                 String getValue = sharedPreferences.getString("keyName", "defaultValue");*/

                 // Toast.makeText(MainActivity.this, x, Toast.LENGTH_SHORT).show();


                 /*if (dataId!=1 && dataId!=2){
                     Toast.makeText(SelectUrls.this, "Invalid", Toast.LENGTH_LONG).show();
                 }else {
                     //Toast.makeText(SelectUrls.this, getValue, Toast.LENGTH_LONG).show();
                     Intent intent = new Intent(SelectUrls.this, Config.class);
                     intent.putExtra("url", z);
                     intent.putExtra("stage_key", zz);
                     startActivity(intent);
                 }*/
                 Intent intent = new Intent(SelectUrls.this, Config.class);
                 intent.putExtra("url", z);
                 intent.putExtra("stage_key", zz);
                 startActivity(intent);



             }
         });



    }

    private void getUrls(){
        String URLstring = "https://ushaurinode.mhealthkenya.co.ke/config";
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLstring, null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            Log.d("", response.toString());

           try {
           urlModelArrayList = new ArrayList<>();
           names = new ArrayList<>();

           urlModelArrayList.clear();
           names.clear();


                for (int i=0; i<response.length(); i++){


                    JSONObject jsonObject =response.getJSONObject(i);

                    int url_id = jsonObject.getInt("id");
                    String url_stage =jsonObject.getString("stage");
                    String main_urls =jsonObject.getString("url");

                   url_Model = new urlModel(url_id, url_stage, main_urls);
                    names.add(url_Model);
                    urlModelArrayList.add(url_Model.getStage());

                }

               names.add(new urlModel(0, "", "--Select the system to connect to--"));
               urlModelArrayList.add("--Select the system to connect to--");


                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectUrls.this, simple_spinner_item, urlModelArrayList);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner1.setAdapter(spinnerArrayAdapter);
                //removeSimpleProgressDialog();

               spinner1.setSelection(spinnerArrayAdapter.getCount()-1);
               dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();

               //
              /* ArrayList<String> dataList;   //names
               ArrayList<data>  datas;      //urlModelArrayList
               public static int dataID;*/

               //


               //onSelct

               spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                   @Override
                   public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                       dataId = names.get(position).getId();

                       if (dataId==1){

                           base_url = names.get(position).getUrl();

                           stage_name =names.get(position).getStage();


                           //Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();

                          // Toast.makeText(SelectUrls.this, "zero", Toast.LENGTH_LONG).show();
                       }
                       else if(dataId==2){

                         base_url = names.get(position).getUrl();
                           stage_name =names.get(position).getStage();
                           //Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();
                       }


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
        RequestQueue requestQueue = Volley.newRequestQueue(SelectUrls.this);
     requestQueue.add(jsonArrayRequest);
    }

    private void setScreen(){
        SharedPreferences preferencesS =getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        String FirstTime =preferencesS.getString("FirstTimeInstall", "");

        if (FirstTime.equals("Yes")){
          Intent intent1 =new Intent(SelectUrls.this, LoginActivity.class);
          startActivity(intent1);

        }else{
            SharedPreferences.Editor editor =preferencesS.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }

    public void geturls1(){
        String URLstring = "https://ushaurinode.mhealthkenya.co.ke/config";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLstring, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("", response.toString());

                try {
                    urlModelArrayList = new ArrayList<>();
                    names = new ArrayList<>();

                    urlModelArrayList.clear();
                    names.clear();


                    JSONArray jsonArray =response.getJSONArray("USHAURI");

                    for (int i =0; i<jsonArray.length(); i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        int url_id = jsonObject.getInt("id");
                        String url_stage =jsonObject.getString("stage");
                        String main_urls =jsonObject.getString("url");

                        url_Model = new urlModel(url_id, url_stage, main_urls);
                        names.add(url_Model);
                        urlModelArrayList.add(url_Model.getStage());

                    }


                    /*for (int i=0; i<response.length(); i++){


                        JSONObject jsonObject =response.getJSONObject(i);

                        int url_id = jsonObject.getInt("id");
                        String url_stage =jsonObject.getString("stage");
                        String main_urls =jsonObject.getString("url");

                        url_Model = new urlModel(url_id, url_stage, main_urls);
                        names.add(url_Model);
                        urlModelArrayList.add(url_Model.getStage());

                    }*/

                    names.add(new urlModel(0, "", "--Select the system to connect to--"));
                    urlModelArrayList.add("--Select the system to connect to--");


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectUrls.this, simple_spinner_item, urlModelArrayList);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinner1.setAdapter(spinnerArrayAdapter);
                    //removeSimpleProgressDialog();

                    spinner1.setSelection(spinnerArrayAdapter.getCount()-1);
                    dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();

                    //
              /* ArrayList<String> dataList;   //names
               ArrayList<data>  datas;      //urlModelArrayList
               public static int dataID;*/

                    //


                    //onSelct

                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            dataId = names.get(position).getId();

                            if (dataId==1){

                                base_url = names.get(position).getUrl();

                                stage_name =names.get(position).getStage();


                                //Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();

                                // Toast.makeText(SelectUrls.this, "zero", Toast.LENGTH_LONG).show();
                            }
                            else if(dataId==2){

                                base_url = names.get(position).getUrl();
                                stage_name =names.get(position).getStage();
                                //Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();
                            }


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
        RequestQueue requestQueue = Volley.newRequestQueue(SelectUrls.this);
        requestQueue.add(jsonObjectRequest);
    }
}