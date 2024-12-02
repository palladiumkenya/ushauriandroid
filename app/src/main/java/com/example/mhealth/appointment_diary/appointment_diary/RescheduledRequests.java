package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.adapter.RequestsAdapter;
import com.example.mhealth.appointment_diary.adapter.UpiErrAdapter;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.models.RescheduledRequestsModel;
import com.example.mhealth.appointment_diary.models.UpiErrModel;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.wellnesstab.UPIErrorList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RescheduledRequests extends AppCompatActivity {
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    String passedUname,passedPassword;
    FloatingActionButton fab;
    TextView appCounterTxtV;
    private ProgressDialog progress;
    Dialogs dialogs;
    JSONArray jsonarray;

    long  diffdate;
    String z, dates, phone;
    private AppointmentAdapter myadapt;
    private List<RescheduledRequestsModel> mymesslist = new ArrayList<>();
    private List<RescheduledRequestsModel> requestlist= new ArrayList<>();

    RequestsAdapter requestsAdapter;

    CheckInternet chkInternet;
    AccessServer acs;

    ListView listView;
    ArrayAdapter arrayAdapter;
    EditText input;
    int appointmentCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescheduled_requests);
      //  fab=(FloatingActionButton) findViewById(R.id.fabtodays);
        try{

            appCounterTxtV=(TextView) findViewById(R.id.appointmentcount);
            chkInternet=new CheckInternet(RescheduledRequests.this);
            appointmentCounter=0;
            acs=new AccessServer(RescheduledRequests.this);
            fab=(FloatingActionButton) findViewById(R.id.fabtodays);
            passedUname="";
            passedPassword="";
            listView = (ListView)findViewById(R.id.messages);
            //messages = (ListView) findViewById(R.id.messages);
            input = (EditText) findViewById(R.id.input);

        }
        catch(Exception e){

        }



       // setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rescheduled Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialogs=new Dialogs(RescheduledRequests.this);

        postapi();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ApiCall();
                postapi();

            }
        });
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id==R.id.action_search2){
            handleMenuSearch();
            return true;
        }else if (id==R.id.logout){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
//            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //Toast.makeText(getApplicationContext(), "searching", Toast.LENGTH_SHORT).show();


                    doSearching(s.toString());
                    //  int x = listView.getCount();
                    // textView1.setText("Total appointments"+ " "+ String.valueOf(x));
                    // upiErrAdapter1.notifyDataSetChanged();
                    //myadapt.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    //  upiErrAdapter1.notifyDataSetChanged();

                    // int x = listView.getCount();
                    //textView1.setText("Total appointments"+ " "+ String.valueOf(x));

                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);



            //add the close icon
//            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.cancel));

            isSearchOpened = true;
        }
    }

    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {

            requestsAdapter.getFilter().filter(s.toString());
            // upiErrAdapter1.notifyDataSetChanged();

            //Toast.makeText(getApplicationContext(), "searching appointments"+s, Toast.LENGTH_SHORT).show();
            // myadapt.getFilter().filter(s.toString());
            //myadapt.filter.performFiltering(s.toString());
        }
        catch(Exception e){

            Toast.makeText(getApplicationContext(), "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }

    }

    public  void postapi() {

        JSONArray jsonArray = new JSONArray();

       // JSONObject jsonObject = new JSONObject();
        try {
            List<Activelogin> al=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin limit 1");
            for(int x=0;x<al.size();x++) {
                String myuname = al.get(x).getUname();
                List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", myuname);
                for (int y = 0; y < myl.size(); y++) {

                    phone = myl.get(y).getPhone();

                }
            }

           // jsonObject.put("phone_no", phone);

        } catch (Exception e) {
            e.printStackTrace();
        }

       // jsonArray.put(jsonObject);
        try{
            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                }
            }

        } catch(Exception e){

        }
        String urls ="?telephone="+phone;

        AndroidNetworking.get(z+Config.Reschedule_LIST+urls)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Connection","keep-alive")
                .addHeaders("Accept", "application/json")

                .setPriority(Priority.MEDIUM)
                .build().getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("SERVER RESPONSE", response.toString());
                        Log.d("total number", String.valueOf(response.length()));

                        String test =response.toString();

                        if (test==null){
                            Toast.makeText(RescheduledRequests.this, "success", Toast.LENGTH_SHORT).show();

                        }

                        //Toast.makeText(UPIErrorList.this, "success"+response, Toast.LENGTH_SHORT).show();
                                  requestlist= new ArrayList<>();
                         for (int i = 0; i < response.length(); i++) {





                             try {
                                 JSONObject item = (JSONObject) response.get(i);


                             // JSONObject jsonObject = jsonArray1.getJSONObject(i);

                                 String  client_name1 = item.getString("client_name");
                                 String clinic_no1 =item.getString("clinic_no");
                                 String client_phone_no1 = item.getString("client_phone_no");
                                 String appntmnt_date1= item.getString("appntmnt_date");
                                 String reschedule_date1 = item.getString("reschedule_date");
                                 String reason1= item.getString("reason");
                                 int clinic_id1= item.getInt("clinic_id");
                                 String appointment_type= item.getString("appointment_type");

                                 int appointment_id= item.getInt("appointment_id");
                                 int reschedule_id= item.getInt("reschedule_id");



                                requestsAdapter =new RequestsAdapter(RescheduledRequests.this, requestlist);



                                RescheduledRequestsModel rescheduledRequestsModel= new RescheduledRequestsModel(clinic_id1, clinic_no1, client_name1, client_phone_no1, appntmnt_date1, reschedule_date1, reason1, appointment_type, appointment_id, reschedule_id);
                                //upilist=new ArrayList<>();
                                requestlist.add(rescheduledRequestsModel);

                                listView.setAdapter(requestsAdapter);




                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }}



                    }




                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(RescheduledRequests.this, anError.getMessage(), Toast.LENGTH_LONG).show();

                        Log.d("", anError.getMessage());

                    }
                });








    }
}