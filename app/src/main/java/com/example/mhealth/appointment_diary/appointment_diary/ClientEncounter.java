package com.example.mhealth.appointment_diary.appointment_diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.utilitymodules.AddCaseManager;

public class ClientEncounter extends AppCompatActivity {

    Spinner visitScheduledS, visitTypeS, chronicS, illnessS,NCDS, regimenS, WhoS;
    String[] visitcheduled = {"", "Yes", "No"};
    String[] chronic = {"", "Yes", "No"};
    String[] visittype = {"", "Self", "Treatment Supporter", "Refill visit documentation","Other"};

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

     //   Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null

      //  setSupportActionBar(toolbar);
   //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Client Encounter");




        visitScheduledS=findViewById(R.id.visit_schedule_spinner);
        visitTypeS=findViewById(R.id.visittype_spinner);
        chronicS=findViewById(R.id.chronic_spinner);
        illnessS=findViewById(R.id.illness_spinner);
        NCDS=findViewById(R.id.ncd_spinner);
        regimenS=findViewById(R.id.regimen_spinner2);
        WhoS=findViewById(R.id.who_spinner);


        // visitScheduledS
        ArrayAdapter<String> visitScheduled = new ArrayAdapter<String>(ClientEncounter.this, android.R.layout.simple_spinner_item, visitcheduled);
        visitScheduled.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        visitScheduledS.setAdapter(visitScheduled);
        visitScheduledS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               /* REASON =ReasonS[position];

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


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}