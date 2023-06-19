package com.example.mhealth.appointment_diary.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.MakeCalls.makeCalls;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.models.RescheduledRequestsModel;
import com.example.mhealth.appointment_diary.models.UpiErrModel;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.utilitymodules.Registration;
import com.example.mhealth.appointment_diary.utilitymodules.UPIUpdateActivity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RequestsAdapter extends BaseAdapter implements Filterable {

    private RequestQueue rq;
    private Context mycont;
    private List<RescheduledRequestsModel> mylist;
    RequestsAdapter.CustomFilter filter;
    private List<RescheduledRequestsModel> filterList;
    List<UpiErrModel> books = null;

    String itemselected;
    String appointmment_type_code;

    AccessServer acs;
    CheckInternet chkinternet;
    makeCalls mc;

    String z;

    public RequestsAdapter (Context mycont, List<RescheduledRequestsModel> mylist) {
        this.mycont = mycont;
        this.mylist = mylist;
        this.filterList=mylist;
        //  mylist=new ArrayList<>();
    }


    @Override
    public int getCount() {


        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mycont, R.layout.reschedule_requests_layout, null);


        try {
            TextView client_name= (TextView) v.findViewById(R.id.client_name);
            TextView clinic_no = (TextView) v.findViewById(R.id.clinic_no);
            TextView client_phone_no = (TextView) v.findViewById(R.id.client_phone_no);
            TextView appntmnt_date= (TextView) v.findViewById(R.id.appntmnt_date);
            TextView reschedule_date= (TextView) v.findViewById(R.id.reschedule_date);
//            final TextView patientID = (TextView) v.findViewById(R.id.patientid);


            Button callbutton = (Button) v.findViewById(R.id.confirm);

            Button rejectbutton = (Button) v.findViewById(R.id.reject);


            String  client_name1 = mylist.get(position).getClient_name();
            String clinic_no1 = mylist.get(position).getClinic_no();
            String client_phone_no1 = mylist.get(position).getClient_phone_no();
            String appntmnt_date1= mylist.get(position).getAppntmnt_date();
            String reschedule_date1 = mylist.get(position).getReschedule_date();

            int r_id =mylist.get(position).getReschedule_id();
            int r_status =mylist.get(position).getAppointment_id();


            chkinternet=new CheckInternet(mycont);
            acs=new AccessServer(mycont);
            mc=new makeCalls(mycont);


            client_name.setText(client_name1);
            clinic_no.setText(clinic_no1 );
            client_phone_no.setText(client_phone_no1);
            appntmnt_date.setText(appntmnt_date1);
            reschedule_date.setText(reschedule_date1);



            //Approve
            callbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mycont);
                    builder1.setIcon(mycont.getResources().getDrawable(R.drawable.ic_alert));
                    builder1.setTitle("Reschedule Approval Alert");
                    builder1.setMessage( "Do you want to approve this reschedule request");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "Approve",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                  //  UPI_number.setText(UPI_number1);

                                    //Log.d("Accepeted", "AccessServer");

                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("r_id", r_id);
                                        jsonObject.put("r_status", 1);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try{
                                        List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                                        if (_url.size()==1){
                                            for (int x=0; x<_url.size(); x++){
                                                z=_url.get(x).getBase_url1();
                                            }
                                        }

                                    } catch(Exception e){

                                    }


                                    AndroidNetworking.post(z+Config.ApproveReschedule)
                                            .addHeaders("Content-Type", "application.json")
                                            .addHeaders("Accept", "*/*")
                                            .addHeaders("Accept", "gzip, deflate, br")
                                            .addHeaders("Connection","keep-alive")
                                            .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                                            .addJSONObjectBody(jsonObject) // posting json
                                            .setPriority(Priority.MEDIUM)
                                            .build()
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    // do anything with response

                                                   Log.e("success", response.toString());
                                                   try {
                                                         boolean status = response.has("success") && response.getBoolean("success");
                                                        String msg = response.has("msg") ? response.getString("msg") : "";

                                                        if (status==true){
                                                            Toast.makeText(mycont, msg, Toast.LENGTH_LONG).show();

                                                        }else{
                                                            Toast.makeText(mycont, msg, Toast.LENGTH_LONG).show();

                                                        }



                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                  //  Toast.makeText(LoginActivity.this, errors1, Toast.LENGTH_LONG).show();



                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error
                                                    Log.e("ErrorConnection", error.getErrorDetail());
                                                    Toast.makeText(mycont, error.getErrorBody(), Toast.LENGTH_LONG).show();



                                                }
                                            });

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                   builder1.setCancelable(true);

                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });

            //reject
            rejectbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mycont);
                    builder1.setIcon(mycont.getResources().getDrawable(R.drawable.ic_alert));
                    builder1.setTitle("Reschedule Rejection Alert");
                    builder1.setMessage( "Do you want to Reject this reschedule request");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "Reject",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  UPI_number.setText(UPI_number1);

                                    //Log.d("Accepeted", "AccessServer");

                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("r_id", r_id);
                                        jsonObject.put("r_status", 2);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try{
                                        List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
                                        if (_url.size()==1){
                                            for (int x=0; x<_url.size(); x++){
                                                z=_url.get(x).getBase_url1();
                                            }
                                        }

                                    } catch(Exception e){

                                    }


                                    AndroidNetworking.post(z+Config.ApproveReschedule)
                                            .addHeaders("Content-Type", "application.json")
                                            .addHeaders("Accept", "*/*")
                                            .addHeaders("Accept", "gzip, deflate, br")
                                            .addHeaders("Connection","keep-alive")
                                            .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                                            .addJSONObjectBody(jsonObject) // posting json
                                            .setPriority(Priority.MEDIUM)
                                            .build()
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    // do anything with response

                                                    Log.e("success", response.toString());
                                                    try {
                                                        boolean status = response.has("success") && response.getBoolean("success");
                                                        String msg = response.has("msg") ? response.getString("msg") : "";

                                                        if (status==true){
                                                            Toast.makeText(mycont, msg, Toast.LENGTH_LONG).show();

                                                        }else{
                                                            Toast.makeText(mycont, msg, Toast.LENGTH_LONG).show();

                                                        }



                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    //  Toast.makeText(LoginActivity.this, errors1, Toast.LENGTH_LONG).show();



                                                }

                                                @Override
                                                public void onError(ANError error) {
                                                    // handle error
                                                    Log.e("ErrorConnection", error.getErrorDetail());
                                                    Toast.makeText(mycont, error.getErrorBody(), Toast.LENGTH_LONG).show();



                                                }
                                            });

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    builder1.setCancelable(true);

                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public Filter getFilter() {
        if(filter==null){

            //filter=new  CustomFilter();
            filter=new RequestsAdapter.CustomFilter();

        }
        return filter;

    }

    public class CustomFilter extends  Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {

                constraint=constraint.toString().toUpperCase();
                ArrayList<RescheduledRequestsModel> filters = new ArrayList<RescheduledRequestsModel>();

                for (int i = 0; i < filterList.size(); i++) {

                    if (filterList.get(i).client_name.toUpperCase().contains(constraint) || filterList.get(i).clinic_no.toUpperCase().contains(constraint)  || filterList.get(i).client_phone_no.toUpperCase().contains(constraint)) {

                        RescheduledRequestsModel am = new RescheduledRequestsModel(filterList.get(i).getClinic_id(), filterList.get(i).getClinic_no(), filterList.get(i).getClient_name(), filterList.get(i).getClient_phone_no(), filterList.get(i).getAppntmnt_date(), filterList.get(i).getReschedule_date(), filterList.get(i).getReason(), filterList.get(i).getAppointment_type(), filterList.get(i).getAppointment_id(), filterList.get(i).getReschedule_id());
                        filters.add(am);
                    }
                }

                results.count = filters.size();
                results.values = filters;

            } else {

                results.count = filterList.size();
                results.values = filterList;


            }
            return results;

        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            mylist = (List<RescheduledRequestsModel>) filterResults.values;
            notifyDataSetChanged();

        }
    }
}
