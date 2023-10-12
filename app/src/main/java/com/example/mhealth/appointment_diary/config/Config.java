package com.example.mhealth.appointment_diary.config;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.loginmodule.LoginActivity;
import com.example.mhealth.appointment_diary.tables.UrlTable;
import com.orm.SugarRecord;

import java.util.List;

public class Config extends AppCompatActivity {

   // public static String BASE_URL= "";
    //https://ushaurinode.mhealthkenya.co.ke

    //startMLAB
    public static String mainShortcode="40147";
    public static String sendSmsShortcode="40147";
    public static String registerShortcode="40147";

    public static final String JSON_ARRAYRESULTS = "result";

    public static final String KEY_MESSAGECODE="message";

    public static final String[] SPINNERLISTLABS = {"KU Teaching and Referring Hospital",
            "Kemri Nairobi",
            "Kisumu lab",
            "Alupe",
            "Walter Reed",
            "Ampath",
            "Coast lab",
            "KNH"};

    public static final String statusBarColor="#3F51B5";


    public static final String[] SPINNERLISTSEX = {"Male", "Female"};
    public static final String[] SPINNERLISTDELIVERYPOINT = {"OPD", "MCH","IPD","CCC","Community"};
    public static final String[] SPINNERLISTTESTKITS = {"Screening kit-determine", "Confirmatory-first response"};
    public static final String[] SPINNERLISTFINALRESULTS = {"Negative", "Positive"};
    public static final String[] SPINNERLISTFIRSTRESULTS = {"Negative", "Positive"};
    public static final String[] SPINNERLISTENTRYPOINT = {"IPD", "OPD","MATERNITY","CCC","MCH/PMTCT","Other"};
    public static final String[] SPINNERLISTPROPHYLAXISCODE = {"AZT for 6 weeks + NVP for 12 weeks", "AZT for 6 weeks + NVP for >12 weeks","None","Other"};
    public static final String[] SPINNERLISTINFANTFEEDING = {"EBF (Exclusive Breast Feeding)", "ERF (Exclusive Replacement Feeding)","MF (Mixed feeding)","BF (Breast Feeding)","NBF (Not Breast Feeding)"};
    public static final String[] SPINNERLISTPCR = {"1= Initial PCR","2= 2nd PCR","3= 3rd PCR","4= Confirmatory PCR and Baseline VL","5= Discrepant PCR (tie breaker)","6= Sample redraw"};




    public static final String[] SPINNERLISTALIVEDEAD = {"Alive", "Dead"};


    public static final String[] SPINNERLISTREGIMEN = {"PM1X=Any other Regimen",
            "PM3= AZT+3TC+NVP",
            "PM4= AZT+ 3TC+ EFV",
            "PM5= AZT+3TC+ LPV/r",
            "PM6= TDC+3TC+NVP",
            "PM7= TDF+3TC+LPV/r",
            "PM9= TDF+3TC+EFV",
            "PM10= AZT+3TC+ATV/r",
            "PM11= TDF+3TC+ATV/r",
            "PM12=TDF+3TC+DTG",
            "PM13=None"};
    public static final String[] SPINNERLISTSAMPLETYPE = {"Frozen plasma","Venous blood(EDTA)","DBS capillary(infants only)","DBS venous","PPT"};


    //public static final String EIDVL_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/remote/login/all";
    public static final String EIDVL_DATA_URL = "https://mlabapi.kenyahmis.org/api/remote/login/all";
    //public static final String HTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/remote/login/hts";
    public static final String HTS_DATA_URL = "https://mlabapi.kenyahmis.org/api/remote/login/hts";
    // public static final String RESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/get/results";
    public static final String RESULTS_DATA_URL = "https://mlabapi.kenyahmis.org/api/get/results";
    //public static final String HISTORICALRESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/historical/results";
    public static final String HISTORICALRESULTS_DATA_URL = "https://mlabapi.kenyahmis.org/api/historical/results";

    //public static final String GETHTSRESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/hts_results";
    public static final String GETHTSRESULTS_DATA_URL = "https://mlabapi.kenyahmis.org/api/hts_results";
    public static final String GETTBRESULTS_DATA_URL = "https://mlabapi.mhealthkenya.co.ke/api/tb_results";
    public static final String[] CURRENTARTREGIMENCODES = {
            "1= TDF+ 3TC+ EFV",
            "2=TDF+3TC+NVP",
            "3=TDF+3TC+ DTG",
            "4=AZT+3TC+NVP",
            "5=AZT+3TC+EFV|",
            "6=ABC+3TC+NVP",
            "7=ABC+3TC+EFV",
            "8= ABC+3TC+DTG",
            "9=ABC+3TC+LPV/r|"
            ,"10=AZT+3TC+LPV/r+ RTV",
            "11=TDF+3TC +ATV/r",
            "12=ABC+3TC+DTG",
            "13=ABC+3TC+ATV/r",
            "14=AZT+3TC+ATV/r",
            "15=AZT+3TC+DRV/r",
            "16=Other"};

    public static final String[] LINES = {
            "First Line",
            "Second Line",
    };


    public static final String[] JUSTIFICATIONCODE = {
            "1=Routine VL",
            "2=Confirmation of treatment failure (repeat VL)",
            "3=Clinical failure",
            "4=Single drug substitution",
            "5=Baseline VL (for infants diagnosed through EID)"
    };


    //END MLAB

    public static String STAGE_NAME= "";
    public String z, zz;
    ProgressDialog progressDialog;

   // public static final String statusBarColor="#3F51B5";


    //public static final String mainShortcode="40146"; CHANGED TO BELOW
    //public static final String mainShortcode="40149";

//******************live url******************

    public static final String GETAFFILIATION_URL = "https://ushaurinew.mhealthkenya.co.ke/chore/affiliation";
    public static final String GETAFFILIATION_URL1 = "/chore/affiliation";
    public static final String SENDDATATODB_URL1 = "/receiver/";

    public static final String ApproveReschedule = "/appnt/updatereschedule";
    public static final String Reschedule_LIST = "/appnt/reschedule_list";

    public static final String UPI_VERIFY = "/mohupi/verify";
    public static final String UPI_REQUEST = "/mohupi/getUPI";
    public static final String COUNTIES ="/locator/counties";
    public static final String S_COUNTIES ="/locator/scounties?county=";
    public static final String WARDS="/locator/wards?scounty=";
    public static final String COUNTRIES="/locator/countries";
    public static final String CALENDER_LIST="/appnt/applist";
    public static final String SEARCHANCPNC="/pmtct/search";

    public static final String UPIERRLIST="/mohupi/geterrorlist";

    public static final String UPIERR_DETAILS="/mohupi/search";
    public static final String POSTUPI_DETAILS="/mohupi/getupdateUPI";
    public static final String SEARCH_CLIENT="/mohupi/search_ccc";


    //https://ushauriapi.kenyahmis.org/past_appointments
    //https://ushauriapi.kenyahmis.org/mohupi/search_ccc+?client_id=1234500002
    //
    public static final String CALIST="/appnt/applist";

    //https://ushauriapi.kenyahmis.org/mohupi/verify

    //https://ushauriapi.nascop.org/past_appointments
    //https://ushauriapi.kenyahmis.org/verifyMFLCode

    public static final String GETTODAYSAPPOINTMENT_URL1 = "/today_appointments";
    public static final String GETUSERMFLCODE_URL1 = "/verifyMFLCode";
    //  public static final String REMOVEFAKEDEFAULTER_URL = "https://ushaurinew.mhealthkenya.co.ke/chore/toda";
    public static final String GETDEFAULTERSAPPOINTMENT_URL1 = "/past_appointments";
    public static final String SENDDATATODB_URL2 = "/chore/receiver_post";



    public static final String JSON_ARRAYAFFILIATIONS = "affiliations";

    public static final String KEY_AFFILIATION_NAME = "name";
    public static final String KEY_AFFILIATION_ID = "id";

   // public static final String JSON_ARRAYRESULTS = "result";
   // public static final String KEY_MESSAGECODE="message";

    public static final String KEY_MFLCODE="mfl_code";

    public static final int DEFAULTERCALLSTARTPERIOD=3;
    public static final int DEFAULTERCALLENDPERIOD=4;
    public static final int DEFAULTERVISITSTARTPERIOD=4;
    public static final int DEFAULTERVISITENDPERIOD=30;
    public static final int MISSEDCALLPERIOD=1;
    public static final int MISSEDVISITSTARTPERIOD=1;
    public static final int MISSEDVISITENDPERIOD=3;
    public static final int LOSTTOFOLLOWUPPERIOD=30;

    public static final String GET_ENROLLMENT_DURATION1 = "/api/process_dfc/check/enrollment/duration";
    public static final String WELL_ADVANCED_BOOKING1 = "/api/process_dfc/well/advanced/booking";
    public static final String ON_DCM_BOOKING1 = "/api/process_dfc/client/dcm/create";
    public static final String NOT_ON_DCM_BOOKING1 = "/api/process_dfc/client/not/dcm";
    public static final String UNSTABLE_BOOKING1 = "/api/process_dfc/unstable/client/booking";


    public static final String CHECK_PMTCT1 = "/api/process_pmtct/check/pmtct/clinic";
    public static final String REGISTER_HEI1 = "/api/process_pmtct/register/hei/client";
    public static final String REGISTER_HEI_WITH_CAREGIVER1 = "/api/process_pmtct/register/hei/with/caregiver";
    public static final String GET_ATTACHED_HEIS1 = "/api/process_pmtct/check/attached/heis";
    public static final String BOOK_HEI_APT1 = "/api/process_pmtct/book/client/appointment";
    public static final String REGISTER_NON_BREASTFEEDING1 = "/api/process_pmtct/register/non/breastfeeding";
    public static final String BOOK_HEI_ONLY_APT1 = "/api/process_pmtct/book/hei/appointment";
    public static final String BOOK_UNSCHEDULED_HEI_ONLY_APT1 = "/api/process_pmtct/book/hei/unscheduled";
    public static final String SEARCH_HEI1 = "/api/process_pmtct/get/hei/details";
    public static final String UPDATE_HEI1 = "/api/process_pmtct/update/hei/details/";
    public static final String SEARCH_PCR1 = "/api/process_pmtct/pcr/positive/details";
    public static final String UPDATE_PCR1 = "/api/process_pmtct/enroll/positive/pcr/";
    public static final String SEARCH_HEI_FINAL1 = "/api/process_pmtct/outcome/get/details";
    public static final String POST_FINAL_OUTOME1 = "/api/process_pmtct/confirm/final/outcome";
    public static final String SEARCH_RESCHEDULE_APT1 = "/api/edit_appointment/get/client/apps";
    public static final String RESCHEDULE_APT1 = "/api/edit_appointment/edit/appointment/date/";

    public static final String ANC="/pmtct/pnc";
    public static final String ANCstart="/pmtct/anc";
    public static final String LDstart="/pmtct/lad";
    public static final String PNCstart="/pmtct/pnc";

    public static final String HEIpost= "/pmtct/hei";

    public static final String VERIFYMFL= "/verifyMFLCode";
    public static final String VERIFYOTP= "/verifyMFLCode/verifyotp/";

    /*
    https://ushauriapi.kenyahmis.org/pmtct/pnc
            10:15
    https://ushauriapi.kenyahmis.org/pmtct/lad*/



    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        TextView x =findViewById(R.id.show);
        Button xx =findViewById(R.id.show1);

        getAlert();

    }

    private void getAlert(){

        try {


            List<UrlTable> _url =UrlTable.findWithQuery(UrlTable.class, "SELECT *from URL_TABLE ORDER BY id DESC LIMIT 1");
            if (_url.size()==1){
                for (int x=0; x<_url.size(); x++){
                    z=_url.get(x).getBase_url1();
                    zz=_url.get(x).getStage_name1();
                    //Toast.makeText(LoginActivity.this, "You are connected to" + " " +zz, Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Log.d("No baseURL", e.getMessage());
        }



        AlertDialog.Builder builder1 = new AlertDialog.Builder(Config.this);
        builder1.setIcon(android.R.drawable.ic_dialog_alert);
        builder1.setTitle("You are connected to");
        builder1.setMessage( zz);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Config.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        //dialog.cancel();
                    }
                });

        /*builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Config.this, SelectUrls.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
    }
