package com.example.mhealth.appointment_diary.loginmodule;


import static com.android.volley.Request.Method.POST;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.ProcessReceivedMessage.ProcessMessage;
import com.example.mhealth.appointment_diary.Progress.Progress;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.RequestPermissions.RequestPerms;
//import com.example.mhealth.appointment_diary.SSLTrustCertificate.SSLTrust;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Affiliationstable;
import com.example.mhealth.appointment_diary.tables.Mflcode;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.tables.Ucsfadmin;
import com.example.mhealth.appointment_diary.tables.Ucsftracers;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.example.mhealth.appointment_diary.utilitymodules.SpinnerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class Registration extends Activity implements AdapterView.OnItemSelectedListener {

    Dialogs dialogs;
    Dialog mydialog;

    private MaterialTextView privacy;
    private CheckBox consent;

    EditText username, password, repassword, securityhint, securityanswerE, affiliationE, trcidnoE, userphoneE, otp1,otp2,otp3,otp4,otp5;
    Button register, cancel, reg_btn, btnGetOtp, btnSendOtp;
    CheckBox check, cktracer;
    boolean istracer;
    String z;
    private JSONArray id_result;
   // String id_result;

    LinearLayout ucsfOptionsL, linearotp;
    Spinner spinner1;
    private static final int PERMS_REQUEST_CODE = 12345;
    String selectedQn, selectedAffiliation;
    SpinnerDialog spinnerDialog;
    //GetRemoteData grd;
    RequestPerms requestPerms;
    private ArrayAdapter<String> arrayAdapterFacility;
    SendMessage sm;
    AccessServer acs;

    String[] items = {"What is your favorite song", "what is the name of your first pet", "what is the name of Favorite movie", "what is the name of Favorite book"};
    String[] affiliationitems = {"NASCOP", "UCSF FACES", "CHS", "EGPAF"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        try {

            mydialog = new Dialog(Registration.this);
            dialogs=new Dialogs(Registration.this);

        } catch (Exception e) {


        }




        initialise();
       // getMflCode();

        addListenerOnChkTracer();
        requestNewPerms();
        //SSLTrust.nuke();

        populateAffiliation();
        populateSecQn();


        setSpinnerListeners();
        showPassword();
        cancelDetails();
        registerUser();
        btnGetOtp= findViewById(R.id.validate_phone);
        //btnSendOtp.setOnClickListener(new OnClickListener() {

        otp1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp1.getText().toString().length() == 1)     //size as per your requirement
                   {
                    otp2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        otp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp2.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        otp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp3.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp4.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        otp4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (otp4.getText().toString().length() == 1)     //size as per your requirement
                {
                    otp5.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });


        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userphoneE.getText().toString().isEmpty()){
                    Toast.makeText(Registration.this, "Phone Number Required", Toast.LENGTH_LONG).show();

                }
              else  if (!consent.isChecked()){
                    Toast.makeText(Registration.this, "Consent Required", Toast.LENGTH_LONG).show();
                }


                else{

                getOtp();}

            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPrivacyDialog();

            }
        });


        btnSendOtp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOtp(userphoneE.getText().toString(),otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString()+otp5.getText().toString());
            }
        });




    }


    private void getMflCode(){
      //  HttpsTrustManager.allowAllSSL();

        try{

           userphoneE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View v, boolean hasFocus) {

                   if (!hasFocus) {
                       if(userphoneE.getText().toString().trim().length()!=10){

                           Toast.makeText(Registration.this, "provide a valid phone number", Toast.LENGTH_SHORT).show();
                       }
                       else{

                           acs.getUserMflCode(userphoneE.getText().toString(),userphoneE);

                       }



                   } else {


                   }

               }
           });
        }
        catch(Exception e){

            Toast.makeText(this, "error occured getting mflcode", Toast.LENGTH_SHORT).show();
        }
    }


    public void addListenerOnChkTracer() {

        cktracer = (CheckBox) findViewById(R.id.chktracer);

        cktracer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {

                    istracer = true;

                } else {

                    istracer = false;

                }

            }
        });

    }

    public void requestNewPerms() {

        try {
            requestPerms.requestPerms();

        } catch (Exception e) {
            Toast.makeText(this, "error in granting permissions " + e, Toast.LENGTH_SHORT).show();


        }
    }


    /*public void getRemoteData() {

        try {
            Toast.makeText(this, "getting data", Toast.LENGTH_SHORT).show();

            List<Affiliationstable> myl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable");
            if (myl.size() > 0) {


            } else {
                //grd.getAffiliationData();

            }

        } catch (Exception e) {


        }
    }*/

    public void setFacilityAdapter() {

        try {

            ArrayList<String> y = new ArrayList<>();
            List<Affiliationstable> myl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable");
            for (int x = 0; x < myl.size(); x++) {
                String affname = myl.get(x).getAffiliationname();
                y.add(affname);

            }


            arrayAdapterFacility = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_checked, y);
            arrayAdapterFacility.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        } catch (Exception e) {


        }
    }

    public void initialise() {

        try {
            acs=new AccessServer(Registration.this);
            sm = new SendMessage(Registration.this);
            istracer = false;
            userphoneE = (EditText) findViewById(R.id.usr_phone);
            trcidnoE = (EditText) findViewById(R.id.trc_idno);
            ucsfOptionsL = (LinearLayout) findViewById(R.id.ucsfoptions);
            linearotp = (LinearLayout) findViewById(R.id.linearOTP);
            requestPerms = new RequestPerms(Registration.this, this);
            affiliationE = (EditText) findViewById(R.id.affiliationnewspinner);
            username = (EditText) findViewById(R.id.username_edt);
            password = (EditText) findViewById(R.id.password_edt);
            repassword = (EditText) findViewById(R.id.repassword_edt);
            securityanswerE = (EditText) findViewById(R.id.securityanswer);

            otp1= (EditText) findViewById(R.id.otp_edit_text1);
            otp2= (EditText) findViewById(R.id.otp_edit_text2);
            otp3= (EditText) findViewById(R.id.otp_edit_text3);
            otp4= (EditText) findViewById(R.id.otp_edit_text4);
            otp5= (EditText) findViewById(R.id.otp_edit_text5);
            //grd = new GetRemoteData(Registration.this);

            securityhint = (EditText) findViewById(R.id.securityhint_edt);
            register = (Button) findViewById(R.id.register_btn);
            btnSendOtp = (Button) findViewById(R.id.submit_otp);
            cancel = (Button) findViewById(R.id.cancel_btn);
            check = (CheckBox) findViewById(R.id.checkBox1);


            spinner1 = (Spinner) findViewById(R.id.spinnerreg);

            selectedAffiliation = "";
            selectedQn = "";

            privacy = (MaterialTextView) findViewById(R.id.tv_privacy);

            consent = (CheckBox) findViewById(R.id.terms);


        } catch (Exception e) {


        }
    }
    public void showPrivacyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_privacy);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow();
    }


    public void setAffiliationSpinner() {

        try {
            //getRemoteData();
            setFacilityAdapter();
            addListenerToAffiliationSpinnerEdt();


        } catch (Exception e) {


        }
    }

    public void addListenerToAffiliationSpinnerEdt() {

        try {


            final ArrayList<String> y = new ArrayList<>();


            List<Affiliationstable> mynl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable");
            for (int x = 0; x < mynl.size(); x++) {
                String affname = mynl.get(x).getAffiliationname();
                y.add(affname);

            }

            affiliationE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    spinnerDialog=new SpinnerDialog(CreateUser.this,items,"Select or Search City","Close Button Text");// With No Animation
                    spinnerDialog = new SpinnerDialog(Registration.this, y, "Select Affiliation", R.style.DialogAnimations_SmileWindow, "Close");// With 	Animation


                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
//                            Toast.makeText(Registration.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
//                            selectedFacility = item;
//                            facilitySpinnerEdt.setText(item);

                            if (item.contains("UCSF")) {

                                ucsfOptionsL.setVisibility(View.VISIBLE);
                            } else {

                                ucsfOptionsL.setVisibility(View.GONE);
                            }

                            affiliationE.setText(item);
                        }
                    });

                    spinnerDialog.showSpinerDialog();

                }
            });
        } catch (Exception e) {


        }
    }

    public void setSpinnerListeners() {

        try {


            spinner1.setOnItemSelectedListener(this);


        } catch (Exception e) {

        }
    }

    public void populateSecQn() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), items);

            spinner1.setAdapter(customAdapter);


        } catch (Exception e) {


        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        setAffiliationSpinner();
    }

    public void populateAffiliation() {


        try {

            SpinnerAdapter customAdapter = new SpinnerAdapter(getApplicationContext(), affiliationitems);


        } catch (Exception e) {


        }
    }


    public void showPassword() {

        try {

            check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// TODO Auto-generated method stub

                    if (!isChecked) {
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        repassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    } else {
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        repassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                }
            });

        } catch (Exception e) {


        }
    }


    public void cancelDetails() {

        try {

            cancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
                    Intent ii = new Intent(Registration.this, LoginActivity.class);
                    startActivity(ii);
                }
            });

        } catch (Exception e) {


        }
    }

    public void registerUser() {

        try {

            register.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub

                    String phonenum = userphoneE.getText().toString();
                    String Uname = username.getText().toString();
                    String Pass = password.getText().toString();
                    String Secuhint = securityhint.getText().toString();
                    String Repass = repassword.getText().toString();
                    String SecuanswerS = securityanswerE.getText().toString();
                    String affiliationS = affiliationE.getText().toString();
                    String affiliationIds = "";

                    List<Mflcode> mfllist=Mflcode.findWithQuery(Mflcode.class,"Select * from Mflcode");


                    if (phonenum.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Phonenumber is required", Toast.LENGTH_LONG).show();

                    }
                    /*else if (affiliationS.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Affiliation is required", Toast.LENGTH_LONG).show();
                        affiliationIds = "-1";
                    }*/


                    else if(mfllist.size()<1){

                        Toast.makeText(Registration.this, "mflcode is required", Toast.LENGTH_SHORT).show();
                    }

                    else if (ucsfOptionsL.isShown() && trcidnoE.getText().toString().trim().isEmpty()) {
                        trcidnoE.setError("ID number is required");
                        Toast.makeText(Registration.this, "ID number is required", Toast.LENGTH_SHORT).show();
                    } else if (Uname.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Username is required", Toast.LENGTH_LONG).show();


                    } else if (Pass.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_LONG).show();


                    } else if (!isTextValid(Pass)) {

                        Toast.makeText(getApplicationContext(), "Password should have atleast 5 characters in length", Toast.LENGTH_LONG).show();

                    } else if (Repass.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Confirm password is required", Toast.LENGTH_LONG).show();


                    } else if (!Pass.contentEquals(Repass)) {

                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();

                    } else if (Secuhint.trim().isEmpty()) {

                        Toast.makeText(Registration.this, "Security hint is required", Toast.LENGTH_SHORT).show();
                    } else if (SecuanswerS.trim().isEmpty()) {

                        Toast.makeText(getApplicationContext(), "Security Answer is required", Toast.LENGTH_LONG).show();


                    } else {

                        if (hasPermissions()) {


                            List<Registrationtable> myl = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=?", Uname);
                            if (myl.size() > 0) {
                                Toast.makeText(Registration.this, "the provided username already exists, try again", Toast.LENGTH_SHORT).show();
                            } else {

                               // List<Affiliationstable> affl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable where affiliationname=? limit 1", affiliationS);
                                List<Affiliationstable> affl = Affiliationstable.findWithQuery(Affiliationstable.class, "select * from Affiliationstable where affiliationname=? limit 1");
                                for (int y = 0; y < affl.size(); y++) {

                                    affiliationIds = affl.get(y).getAffiliationid();
                                }
                                //Registrationtable rt = new Registrationtable(Uname, Pass, Secuhint, SecuanswerS, affiliationS, phonenum);
                                Registrationtable rt = new Registrationtable(Uname, Pass, Secuhint, SecuanswerS, phonenum);
                                rt.save();


                                if (istracer) {

                                    String idnum = trcidnoE.getText().toString();
                                    List<Ucsftracers> trclist = Ucsftracers.findWithQuery(Ucsftracers.class, "select * from Ucsftracers where uname=? or idnumber=?", Uname, idnum);
                                    if (trclist.size() > 0) {
                                        Toast.makeText(Registration.this, "the provided details already exists under tracers", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Ucsftracers ut = new Ucsftracers(Uname, idnum);
                                        ut.save();
                                    }


                                } else if (ucsfOptionsL.isShown()) {

                                    String idnum = trcidnoE.getText().toString();
                                    List<Ucsfadmin> adminlist = Ucsfadmin.findWithQuery(Ucsfadmin.class, "select * from Ucsfadmin where uname=? or idnumber=?", Uname, idnum);
                                    if (adminlist.size() > 0) {
                                        Toast.makeText(Registration.this, "the provided details already exists under ucsf admins", Toast.LENGTH_SHORT).show();
                                    } else {

                                        Ucsfadmin ua = new Ucsfadmin(Uname, idnum);
                                        ua.save();
                                    }
                                }


                                Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(Registration.this, LoginActivity.class);
                                startActivity(i);
                                finish();

//                                sm.sendMessageApi("TRC_VALIDATE", Config.mainShortcode);


                            }

                            //
                        } else {

                            requestPerms();
                        }


                    }
                }
            });

        } catch (Exception e) {

            Toast.makeText(this, "error " + e, Toast.LENGTH_SHORT).show();


        }
    }


    private boolean hasPermissions() {


        int res = 0;

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
//                android.Manifest.permission.CALL_PHONE


        };

        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);

            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }

        }
        return true;


    }


    private void requestPerms() {

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.SEND_SMS,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setAutoCancel(true);

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spin = (Spinner) adapterView;


        if (spin.getId() == R.id.spinner) {

            selectedQn = Integer.toString(i);


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    /*    ****** password regular expression explanation*******

            (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{6,} at least 6 characters

*/

    public boolean isTextValid(String mytext) {
        boolean isCorrect = false;
        String pattern = "(?=\\S+$).{5,}";
        if (mytext.matches(pattern)) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }

        return isCorrect;
    }
    public void getOtp(){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone_no", userphoneE.getText().toString());
            jsonObject.put("otp", true);
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
        final int[] mStatusCode = new int[1];


        AndroidNetworking.post(z+ Config.VERIFYMFL)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .setContentType("application.json")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
                /*.setRetryPolicy(new DefaultRetryPolicy(
                        30000, //for 30 Seconds
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT))*/

                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, response.toString());
                       /* if (response.length()==0){
                            Toast.makeText(Registration.this, "No Data", Toast.LENGTH_LONG).show();
                        }*/

                       // if (mStatusCode[0]==200) {
                           /* linearotp.setVisibility(View.VISIBLE);
                            btnSendOtp.setVisibility(View.VISIBLE);*/

                            try {

                                JSONArray jsonArray = response.getJSONArray("result");


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    //JSONObject jsonObject2 = response.getJSONObject("mfl_code");
                                   // id_result = jsonObject2.getJSONArray(Config.JSON_ARRAYRESULTS);
                                    //getMyMflcode(id_result);
                                    String mfl_code = jsonObject1.getString("mfl_code");
                                   // String otp = jsonObject1.getString("otp");
                                  //  id_result=mfl_code;
                                   // getMyMflcode(id_result);


                                    Log.d("Value for MFLCODE", mfl_code);

                                    if (!mfl_code.isEmpty()){
                                        linearotp.setVisibility(View.VISIBLE);
                                        btnSendOtp.setVisibility(View.VISIBLE);

                                    }else{
                                        linearotp.setVisibility(View.GONE);
                                        btnSendOtp.setVisibility(View.GONE);
                                        Toast.makeText(Registration.this, userphoneE.getText().toString()+" " +"is not mapped", Toast.LENGTH_SHORT).show();
                                    }
                                   // Log.d("Value for OTP", otp);


                                   /* Mflcode.deleteAll(Mflcode.class);

                                    Mflcode mc=new Mflcode();
                                    mc.setMfl(id_result);
                                    mc.save();*/



                                    // Toast.makeText(Registration.this, otp, Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(Registration.this, mfl_code, Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();

                                System.out.println("***error 0****"+e.getMessage());
                               // Toast.makeText(Registration.this, "error getting mflcode, try again", Toast.LENGTH_SHORT).show();

                            }
                       // }
                      /*  else{
                            linearotp.setVisibility(View.GONE);
                            btnSendOtp.setVisibility(View.GONE);
                            dialogs.showErrorDialog(response.toString(),"Server responses");
                        }*/

                    }

                    @Override
                    public void onError(ANError error) {
                        linearotp.setVisibility(View.GONE);
                        btnSendOtp.setVisibility(View.GONE);
                        dialogs.showErrorDialog("No server connection", "Server Response");
                        System.out.println("***error 3****"+error.getMessage());
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                       // animationView.setVisibility(View.GONE);

                        // Snackbar.make(findViewById(R.id.signup_layout), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        //JSONObject jsonObject = new JSONObject();
                        int  errors = error.getErrorCode();
                        if (errors==400){
                           // Snackbar.make(findViewById(R.id.signup_layout), " Invalid CCC number", Snackbar.LENGTH_LONG).show();
                        }else {
                            //Snackbar.make(findViewById(R.id.signup_layout), "Error: " + error.getErrorDetail(), Snackbar.LENGTH_LONG).show();
                        }


                    }
                });



    }


public void submitOtp(String phone, String otpA){
   /* JSONObject jsonObject = new JSONObject();
    try {
        jsonObject.put("phone_no", phone);
        jsonObject.put("otp", otpA);


    } catch (JSONException e) {
        e.printStackTrace();
    }*/

    try{
        List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
        if (_url.size()==1){
            for (int x=0; x<_url.size(); x++){
                z=_url.get(x).getBase_url1();
            }
        }

    } catch(Exception e){

    }
    final int[] mStatusCode = new int[1];


    try{

       // pr.showProgress("getting user mflcode...");


        // StringRequest stringRequest = new StringRequest(POST,Config.GETUSERMFLCODE_URL,
        StringRequest stringRequest = new StringRequest(POST, z+Config.VERIFYOTP,
                new Response.Listener<String>() {



                    @Override
                    public void onResponse(String response) {
//                            pd.dismissDialog();

                        //pr.dissmissProgress();

//                            Toast.makeText(ctx, "status code "+mStatusCode[0], Toast.LENGTH_LONG).show();
                        if(mStatusCode[0]==200){

//                                Toast.makeText(ctx, "success code "+mStatusCode[0], Toast.LENGTH_SHORT).show();
                            System.out.println(response);

                            JSONObject j = null;
                            try {
                                j = new JSONObject(response);
                                id_result = j.getJSONArray(Config.JSON_ARRAYRESULTS);

                                getMyMflcode(id_result);

                                Intent intent = new Intent(Registration.this, Registration2otp.class);
                                intent.putExtra("PhoneNo", userphoneE.getText().toString());
                                startActivity(intent);
                               // dialogs.showErrorDialog(response,"Server responses"+id_result);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("***error 0****"+e.getMessage());
                                Toast.makeText(Registration.this, "error getting mflcode, try again", Toast.LENGTH_SHORT).show();

                            }

                        }
                        else{
                            System.out.println("***error 1****"+response);

                            dialogs.showErrorDialog(response,"Server responses");
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try{

                            byte[] htmlBodyBytes = error.networkResponse.data;

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            //dialogs.showErrorDialog(new String(htmlBodyBytes),"Server Response");
                            dialogs.showErrorDialog("No server connection", "Server Response");
                            System.out.println("***error 3****"+error.getMessage());
                            //Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();

                        //    pr.dissmissProgress();

                        }
                        catch(Exception e){

                            System.out.println("***error 2****"+e.getMessage());

//                            Toast.makeText(ctx,  ""+error.networkResponse.statusCode+" error mess "+new String(htmlBodyBytes), Toast.LENGTH_SHORT).show();
                            dialogs.showErrorDialog("error getting mflcode, try again","Server Response");

                          //  pr.dissmissProgress();


                        }





                    }
                })


        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                mStatusCode[0] = response.statusCode;

//                    return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
                return super.parseNetworkResponse(response);
            }


//                    @Override
//                    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
//                        return Response.success(networkResponse, HttpHeaderParser.parseCacheHeaders(networkResponse));
//
//                    }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                mStatusCode[0] = volleyError.hashCode();
                return super.parseNetworkError(volleyError);
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("phone_no", phone);
                params.put("otp", otpA);

                return params;
            }

//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<>();
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//
//                    return headers;
//                }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                800000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Registration.this);
        requestQueue.add(stringRequest);


    }
    catch(Exception e){

        Toast.makeText(Registration.this, "error getting mflcode "+e, Toast.LENGTH_SHORT).show();
    }
}
    private void getMyMflcode(JSONArray j) {


        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);


                String mflcode=json.getString(Config.KEY_MFLCODE);
//                Toast.makeText(ctx, ""+mflcode, Toast.LENGTH_SHORT).show();
                Mflcode.deleteAll(Mflcode.class);

                Mflcode mc=new Mflcode();
                mc.setMfl(mflcode);
                mc.save();




            } catch (JSONException e) {
                e.printStackTrace();
//                Toast.makeText(CreateUser.this, "an error getting facilities "+ e, Toast.LENGTH_SHORT).show();
            }
        }

}}
