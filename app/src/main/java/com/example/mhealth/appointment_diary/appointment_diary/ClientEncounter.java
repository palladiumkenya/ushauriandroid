package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.AccessServer.AccessServer;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.config.Config;
import com.example.mhealth.appointment_diary.encryption.Base64Encoder;
import com.example.mhealth.appointment_diary.sendmessages.SendMessage;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;
import com.example.mhealth.appointment_diary.utilitymodules.AddCaseManager;
import com.example.mhealth.appointment_diary.utilitymodules.Registration;

import java.util.List;

public class ClientEncounter extends AppCompatActivity {


    CheckInternet chkinternet;
    AccessServer acs;
    SendMessage sm;

    Spinner visitScheduledS, visitTypeS, chronicS, illnessS,NCDS, regimenS, WhoS;
    String visitScheduled_code, visitType_code, chronic_code, illness_code, NCDS_code, regimen_code, Who_code;
    String[] visitscheduled = {"", "Yes", "No"};
    String[] chronic = {"", "Yes", "No"};
    String[] visittype = {"", "Self", "Treatment Supporter", "Refill visit documentation","Other"};

    EditText weight, height,muac, bmi, zscore, blood_sugar,systolic, diastolic,specify;

    String weight1, height2, muac2, bmi2, zscore2, blood_sugar2, systolic2, diastolic2, specify2;
    Button btnRSubmit;

    String[] illness = {"", "Arthritis", "Asthma", "Cancer","cardiovascular diseases", "Chronic hepatitis", "Chronic Kidney disease",
            "Chronic obstructive pulmonary disease", "Chronic renal failure","Cystic fibrosis","Deafness and Hearing impairement","Diabetis",
            "Endometriosis", "Epilepsy", "Glaucoma", "Heart Disease", "Hyperlipidaemia", "Hypertension","Hypothyroidism", "Mental illness",
            "Multiple sclerosis","Obesity","Osteoporosis","Sickle cell Anaemia","Thyroid", "other"};

    String[] ncd = {"", "Controlled", "Not-controlled"};

    String[] regimen = {"", "ABC/3TC/NVP", "AZT/3TC/NVP","ABC/3TC/EFV", "TDF/3TC/AZT","AZT/3TC/DTG","ETR/RAL/DRV/RTV","AZT/3TC/LPV/r","AZT/TDF/3TC/LPV/r", "TDF/ABC/LPV/r", "ABC/TDF/3TC/LPV/r", "ETR/TDF/3TC/LPV/r", "ABC/3TC/LPV/r","D4T/3TC/LPV/r","ABC/DDI/LPV/r","TDF/3TC/NVP","AZT/3TC/EFV","TDF/3TC/ATV/r","AZT/3TC/ATV/r","D4T/3TC/EFV","AZT/3TC/ABC","TDF/3TC/DTG", "TDF/3TC/LPV/r","ABC/3TC/ATV/r","TDF/3TC/DTG/DRV/r","TDF/3TC/RAL/DRV/r","TDF/3TC/DTG/EFV/DRV/r","ABC/3TC/RAL", "AZT/3TC/RAL/DRV/r", "ABC/3TC/RAL/DRV/r","RAL/3TC/DRV/RTV/AZT","RAL/3TC/DRV/RTV/ABC", "ETV/3TC/DRV/RTV", "RAL/3TC/DRV/RTV/TDF","RAL/3TC/DRV/RTV","Other (Specify)"};
    String[] who_stage = {"", "WHO Stage 1", "WHO Stage 2", "WHO Stage 3","WHO Stage 4"};
    //String[] ncd = {"", "Controlled", "Not-controlled"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_encounter);

        getSupportActionBar().setTitle("Client Encounter");


        acs = new AccessServer(ClientEncounter.this);
        chkinternet = new CheckInternet(ClientEncounter.this);




        visitScheduledS=findViewById(R.id.visit_schedule_spinner);
        visitTypeS=findViewById(R.id.visittype_spinner);
        chronicS=findViewById(R.id.chronic_spinner);
        illnessS=findViewById(R.id.illness_spinner);
        NCDS=findViewById(R.id.ncd_spinner);
        regimenS=findViewById(R.id.regimen_spinner2);
        WhoS=findViewById(R.id.who_spinner);

        weight=findViewById(R.id.weight);
        height=findViewById(R.id.height);
        muac=findViewById(R.id.muac);

        bmi=findViewById(R.id.bmi);
        zscore=findViewById(R.id.zscore);
        blood_sugar=findViewById(R.id.blood_sugar);
        systolic=findViewById(R.id.systolic);
        diastolic=findViewById(R.id.diastolic);
        specify=findViewById(R.id.specify);
        btnRSubmit=findViewById(R.id.btnRSubmit);





        // visitScheduledS
        ArrayAdapter<String> visitScheduled = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, visitscheduled);
        visitScheduled.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitScheduledS.setAdapter(visitScheduled);
        visitScheduledS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                visitScheduled_code=visitscheduled[position];

               /* REASON =ReasonS[position];
               visitScheduled_code, visitType_code, chronic_code, illness_code, NCDS_code, regimen_code, Who_code

                REASON_code= Integer.toString(position);
                if (REASON.contentEquals("Other Specify")){
                    other1.setVisibility(View.VISIBLE);

                }else{
                    other1.setVisibility(View.GONE);
                    other1.setText("");
                }*/

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //visitTypeS

        ArrayAdapter<String> reasonAdapter = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, visittype);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitTypeS.setAdapter(reasonAdapter);
        visitTypeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                visitType_code=visittype[position];


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //chronicS
        ArrayAdapter<String> chronicAdapter = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, chronic);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chronicS.setAdapter(chronicAdapter);
        chronicS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                chronic_code=chronic[position];


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //illnessS
        ArrayAdapter<String> illnessAdapter = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, illness);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        illnessS.setAdapter(illnessAdapter);
        illnessS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                illness_code=illness[position];


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //NCDS
        ArrayAdapter<String> NCDSAdapter = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, ncd);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NCDS.setAdapter(NCDSAdapter);
        NCDS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                NCDS_code=ncd[position];


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      //regimen
        ArrayAdapter<String> regimenAdapter = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, regimen);
        regimenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regimenS.setAdapter(regimenAdapter);
        regimenS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                regimen_code=regimen[position];


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //WhoS

        ArrayAdapter<String> WhoAdapter = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, who_stage);
        WhoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WhoS.setAdapter(WhoAdapter);
        WhoS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Who_code =who_stage[position];

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnRSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  weight1, height2, muac2, blood_sugar2, systolic2, diastolic2, specify2;

               // if (we)

                weight1 = weight.getText().toString();
                height2 = height.getText().toString();
                muac2 = muac.getText().toString();

                bmi2 = bmi.getText().toString();
                zscore2 =zscore.getText().toString();
                blood_sugar2=blood_sugar.getText().toString();
                systolic2=systolic.getText().toString();
                diastolic2=diastolic.getText().toString();
                specify2=specify.getText().toString();




                String sendSms = "1234500001" + "*" + visitScheduled_code+ "*" + visitType_code + "*" + weight1 + "*" + height2 + "*" + bmi2+ "*" + zscore2 + "*" + muac2 + "*" + blood_sugar2+ "*" + systolic2+ "*" + diastolic2 + "*" + "-1" + "*" + chronic_code + "*" + illness_code + "*" + "-1" + "*" + NCDS_code + "*" + regimen_code + "*" + Who_code;
                Log.d("Data submitted", "visit*"+sendSms.toString()+"0712311264");



                String encrypted = Base64Encoder.encryptString(sendSms);
                Log.d("visit", "visit*"+encrypted.toString()+"0712311264");


                String mynumber = Config.mainShortcode;

                if (chkinternet.isInternetAvailable()) {
                    List<Activelogin> myl = Activelogin.findWithQuery(Activelogin.class, "select * from Activelogin");
                    for (int x = 0; x < myl.size(); x++) {

                        String un = myl.get(x).getUname();
                        List<Registrationtable> myl2 = Registrationtable.findWithQuery(Registrationtable.class, "select * from Registrationtable where username=? limit 1", un);
                        for (int y = 0; y < myl2.size(); y++) {

                            String phne = myl2.get(y).getPhone();
//                                acs.sendDetailsToDb("Reg*"+sendSms+"/"+phne);
                            acs.sendClientEncounter("visit*" + encrypted, phne);



                        }
                    }


                } else {

//                    sm.sendMessageApi("Reg*" + encrypted, mynumber);
//
//                    LogindisplayDialog("Client registered successfully, kindly confirm that you have received the client registration successful SMS before booking an appointment");
//
//
//


                }



            }
        });

    }

    public  void send2(){

    }
}