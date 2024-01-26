package com.example.mhealth.appointment_diary.utilitymodules;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mhealth.appointment_diary.Checkinternet.CheckInternet;
import com.example.mhealth.appointment_diary.Dialogs.Dialogs;
import com.example.mhealth.appointment_diary.R;
import com.example.mhealth.appointment_diary.pmtct.PMTCT1;
import com.example.mhealth.appointment_diary.pmtct.PNCVisit;
import com.example.mhealth.appointment_diary.pmtct.PNCVisitStart;
import com.example.mhealth.appointment_diary.tables.Activelogin;
import com.example.mhealth.appointment_diary.tables.Registrationtable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeVisitChecklist extends AppCompatActivity {
    CheckInternet chkinternet;
    Button send;
    EditText arvstoredE,  whodisdclosedE, arvstakenE, NotesE;
    EditText memberNameE1, memberTelephoneE1, landmarkE1;
    String  phone_no1;

    Dialogs dialogs;

    Spinner independentS, needsS, sexualpartnerS, disclosehouseholdS, socialsupportS, socialsupportcommunityS, nonclinicalS, mentalhealthS,
            stressfulsituationS, drugs_alcoholS, side_effectsS;


    String[] patient_independent = {"--Patient Independent--", "Yes", "No"};
    String[] basic_needs_met = {"--Basic Needs Met--", "Yes", "No"};
    String[] sexual_partner = {"--Has Sexual Partner--", "Yes", "No"};
    String[] disclosed_hiv_status = {"--Disclosed HIV Status--", "Yes", "No"};
    String[] social_support_household = {"--Household Social Support--", "Yes", "No"};
    String[] social_support_community = {"--Community Social Support--", "Yes", "No"};
    String[] non_clinical_services = {"--Non Clinical Services--", "Yes", "No"};
    String[] mental_health = {"--Have Mental Health--", "Yes", "No"};
    String[] stress_situation = {"--Stressful Situation--", "Yes", "No"};
    String[] use_drug = {"--Uses Drugs--", "Yes", "No"};
    String[] side_effect = {"--Side Effects--", "Yes", "No"};

    private String patient_independent_st = "";
    private String  basic_needs_met_st = "";
    private String sexual_partner_st = "";
    private String disclosed_hiv_status_st = "";
    private String social_support_household_st = "";
    private String social_support_community_st = "";
    private String non_clinical_services_st = "";
    private String mental_health_st = "";

    private String stress_situation_st = "";
    private String use_drug_st = "";
    private String side_effect_st = "";
    String patient_independent_st_code, basic_needs_met_st_code, sexual_partner_st_code, disclosed_hiv_status_st_code, social_support_household_st_code,
            social_support_community_st_code, non_clinical_services_st_code, mental_health_st_code, stress_situation_st_code, use_drug_st_code, getSide_effect_st_code;


    String newCC;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visit_checklist);
        dialogs=new Dialogs(HomeVisitChecklist.this);

        chkinternet =new CheckInternet(HomeVisitChecklist.this);

        List<Activelogin> myl=Activelogin.findWithQuery(Activelogin.class,"select * from Activelogin");

        for(int x=0;x<myl.size();x++){
            String un=myl.get(x).getUname();
            List<Registrationtable> myl2=Registrationtable.findWithQuery(Registrationtable.class,"select * from Registrationtable where username=? limit 1",un);
            for(int y=0;y<myl2.size();y++){
                phone_no1=myl2.get(y).getPhone();
            }
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newCC= null;
            } else {
                newCC= extras.getString("Client_CCC");
            }
        } else {
            newCC= (String) savedInstanceState.getSerializable("Client_CCC");
        }

        try {
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Home Visit CheckList");

        } catch (Exception e) {

        }


        send= (Button) findViewById(R.id.btn_save);
        whodisdclosedE = (EditText) findViewById(R.id.whodisdclosed);
        arvstoredE = (EditText) findViewById(R.id.arvstored);
        arvstakenE = (EditText) findViewById(R.id.arvstaken);
        NotesE = (EditText) findViewById(R.id.Notes);

        memberNameE1 = (EditText) findViewById(R.id.memberName);
        memberTelephoneE1 = (EditText) findViewById(R.id.memberTelephone);
        landmarkE1 = (EditText) findViewById(R.id.landmark);



        independentS = (Spinner) findViewById(R.id.independent);
        needsS = (Spinner) findViewById(R.id.needs);
        sexualpartnerS = (Spinner) findViewById(R.id.sexualpartner);
        disclosehouseholdS = (Spinner) findViewById(R.id.disclosehousehold);
        socialsupportS = (Spinner) findViewById(R.id.socialsupport);
        socialsupportcommunityS = (Spinner) findViewById(R.id.socialsupportcommunity);
        nonclinicalS = (Spinner) findViewById(R.id.nonclinical);
        mentalhealthS = (Spinner) findViewById(R.id.mentalhealth);
        stressfulsituationS = (Spinner) findViewById(R.id.stressfulsituation);
        drugs_alcoholS = (Spinner) findViewById(R.id.drugs_alcohol);
        side_effectsS = (Spinner) findViewById(R.id.side_effects);

        //independentS
        ArrayAdapter<String> reasonAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,  patient_independent);
        reasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        independentS.setAdapter(reasonAdapter);
        independentS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                patient_independent_st = patient_independent[position];
                patient_independent_st_code = Integer.toString(position);


               /* if (REASON.contentEquals("Other Specify")){
                    other1.setVisibility(View.VISIBLE);

                }else{
                    other1.setVisibility(View.GONE);
                }*/


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //needsS
        ArrayAdapter<String> needsAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,  basic_needs_met);
        needsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        needsS.setAdapter(needsAdapter);
        needsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                basic_needs_met_st = basic_needs_met[position];

                basic_needs_met_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // sexual_partner
        ArrayAdapter<String> sexualAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item, sexual_partner);
        sexualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexualpartnerS.setAdapter(sexualAdapter);
        sexualpartnerS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sexual_partner_st = sexual_partner[position];

                sexual_partner_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // disclosehousehold
        ArrayAdapter<String> disclosehouseholdSAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,disclosed_hiv_status);
        disclosehouseholdSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disclosehouseholdS.setAdapter(disclosehouseholdSAdapter);
        disclosehouseholdS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                disclosed_hiv_status_st =disclosed_hiv_status[position];

                if (disclosed_hiv_status_st.contentEquals("Yes")){
                    whodisdclosedE.setVisibility(View.VISIBLE);

                }else{
                    whodisdclosedE.setVisibility(View.GONE);
                    //other1.setText("");
                }


                disclosed_hiv_status_st_code = Integer.toString(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //social_support_household
        ArrayAdapter<String> social_support_householdSAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,social_support_household);
        social_support_householdSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        socialsupportS.setAdapter(social_support_householdSAdapter);
        socialsupportS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                social_support_household_st =social_support_household[position];

                social_support_household_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //social_support_community
        ArrayAdapter<String> social_support_communitySAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,social_support_community);
        social_support_communitySAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        socialsupportcommunityS.setAdapter(social_support_communitySAdapter);
        socialsupportcommunityS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                social_support_community_st =social_support_community[position];
                social_support_community_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //non_clinical_services
        ArrayAdapter<String> non_clinical_servicesSAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,non_clinical_services);
        non_clinical_servicesSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nonclinicalS.setAdapter(non_clinical_servicesSAdapter);
        nonclinicalS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                non_clinical_services_st =non_clinical_services[position];
                non_clinical_services_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //mental_health
        ArrayAdapter<String> mental_healthSAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,mental_health);
        mental_healthSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mentalhealthS.setAdapter(mental_healthSAdapter);
        mentalhealthS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mental_health_st =mental_health[position];
                mental_health_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //stress_situation
        ArrayAdapter<String> stress_situationAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,stress_situation);
        stress_situationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stressfulsituationS.setAdapter(stress_situationAdapter);
        stressfulsituationS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stress_situation_st =stress_situation[position];

                stress_situation_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //use_drug
        ArrayAdapter<String> use_drugAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,use_drug);
        use_drugAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drugs_alcoholS.setAdapter( use_drugAdapter);
        drugs_alcoholS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                use_drug_st =use_drug[position];
                use_drug_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //side_effect
        ArrayAdapter<String>side_effectAdapter = new ArrayAdapter<String>(HomeVisitChecklist.this, android.R.layout.simple_spinner_item,side_effect);
        side_effectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        side_effectsS.setAdapter( side_effectAdapter);
        side_effectsS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                side_effect_st =side_effect[position];
                getSide_effect_st_code = Integer.toString(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if (patient_independent_st_code.contentEquals("0")){
                    Toast.makeText(HomeVisitChecklist.this, "Specify patient's independence", Toast.LENGTH_SHORT).show();
                }else if (basic_needs_met_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Specify if patient's needs are met", Toast.LENGTH_SHORT).show();
                   }

                   else if (sexual_partner_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Specify if has sexual partner ", Toast.LENGTH_SHORT).show();
                   }
                   else if (disclosed_hiv_status_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Specify if HIV status is disclosed ", Toast.LENGTH_SHORT).show();
                   }
                   else if(arvstoredE.getText().toString().isEmpty()){
                       Toast.makeText(HomeVisitChecklist.this, "Specify how ARVs are stored ", Toast.LENGTH_SHORT).show();
                   }
                   else if(arvstakenE.getText().toString().isEmpty()){
                       Toast.makeText(HomeVisitChecklist.this, "Specify how ARVs are taken ", Toast.LENGTH_SHORT).show();
                   }
                   else if(social_support_household_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Specify if the patient receives social support from the household", Toast.LENGTH_SHORT).show();
                   }

                   else if(social_support_community_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Specify if the patient receives social support from the community", Toast.LENGTH_SHORT).show();
                   }

                   else if(non_clinical_services_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Patient linked to any non_clinical services", Toast.LENGTH_SHORT).show();
                   }
                   else if(mental_health_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Have any mental health issues", Toast.LENGTH_SHORT).show();
                   }
                   else if(stress_situation_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Have any stressful situation", Toast.LENGTH_SHORT).show();
                   }
                   else if(use_drug_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Takes drug or Alcohol?", Toast.LENGTH_SHORT).show();
                   }
                   else if(getSide_effect_st_code.contentEquals("0")){
                       Toast.makeText(HomeVisitChecklist.this, "Any side effects from medication?", Toast.LENGTH_SHORT).show();
                   }

                   else if (!chkinternet.isInternetAvailable()){

                       Toast.makeText(HomeVisitChecklist.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                   }

else{
                sev(phone_no1, newCC, memberNameE1.getText().toString(), memberTelephoneE1.getText().toString(),
                        landmarkE1.getText().toString(), patient_independent_st,   basic_needs_met_st,  sexual_partner_st,
                        disclosed_hiv_status_st,  whodisdclosedE.getText().toString(), arvstoredE.getText().toString(), arvstakenE.getText().toString(),
                        social_support_household_st,  social_support_community_st,  non_clinical_services_st,
                         mental_health_st, stress_situation_st, use_drug_st, side_effect_st, NotesE.getText().toString());

                       independentS.setSelection(0);
                       needsS.setSelection(0); sexualpartnerS.setSelection(0);
                       disclosehouseholdS.setSelection(0); socialsupportS.setSelection(0); socialsupportcommunityS.setSelection(0);
                       nonclinicalS.setSelection(0); mentalhealthS.setSelection(0);


                             //  stressfulsituationS, drugs_alcoholS, side_effectsS;

}
            }
        });

    }


    private void sev(String phone_no, String clinic_number, String family_member_name, String telephone_no,
                     String landmark, String patient_independent,  String basic_need, String sexual_partner,
                     String disclosed_hiv_status, String disclosed_person, String arv_stored, String arv_taken,
                     String social_support_household, String social_support_community, String non_clinical_services,
                     String mental_health, String stress_situation, String use_drug, String side_effect, String other_note){
        RequestQueue queue = Volley.newRequestQueue(this);
        String saveurl ="https://ushauriapi.kenyahmis.org/case/home/visit";
        JSONObject payload = new JSONObject();

        try {
            payload.put("phone_no", phone_no);
            payload.put("clinic_number", clinic_number);
            payload.put("family_member_name", family_member_name);
            payload.put("telephone_no", telephone_no);
            payload.put("landmark", landmark);
            payload.put("patient_independent", patient_independent);
            payload.put("basic_need", basic_need);
            payload.put("sexual_partner", sexual_partner);
            payload.put("disclosed_hiv_status", disclosed_hiv_status);
            payload.put("disclosed_person", disclosed_person);
            payload.put("arv_stored", arv_stored);
            payload.put("arv_taken", arv_taken);
            payload.put("social_support_household", social_support_household);
            payload.put("social_support_community",social_support_community);
            payload.put("non_clinical_services", non_clinical_services);
            payload.put("mental_health", mental_health);

            payload.put("stress_situation", stress_situation);
            payload.put("use_drug", use_drug);
            payload.put("side_effect", side_effect);
            payload.put("other_note", other_note);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, saveurl, payload, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean success = response.getBoolean("success");
                    String message = response.getString("message");

                    // Check the "success" value and display the "message" accordingly
                    if (success) {
                        // Successful response
                        // Display or handle the success message




                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(HomeVisitChecklist.this);
                    builder1.setIcon(R.drawable.nascoplogonew);
                    builder1.setTitle(message);
                    builder1.setMessage( "Server Response");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                    Intent intent = new Intent(HomeVisitChecklist.this, CaseManagement.class);

                                    startActivity(intent);
                                    dialog.dismiss();







                                    //dialog.cancel();
                                }
                            });


                    AlertDialog alert11 = builder1.create();
                    alert11.show();



               //         showToast(message); // Replace with your display logic
                    } else {
                        // Unsuccessful response
                        // Display or handle the error message
                        showToast(message); // Replace with your display logic
                    }




















                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle JSON parsing error
                }
                //               Toast.makeText(AddCaseManager.this, response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error response
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        // Convert error response data to JSON object
                        JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));

                        // Now you can extract information from the JSON object
                        boolean success = jsonObject.optBoolean("success");
                        String message = jsonObject.optString("message");

                        // Handle the error message or any other information
                        Log.e("ErrorResponse", "Success: " + success + ", Message: " + message);
                        Toast.makeText(HomeVisitChecklist.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle other types of errors (e.g., network error or timeout)
                    Log.e("ErrorResponse", "An error occurred: " + error.getMessage());

                    Toast.makeText(HomeVisitChecklist.this, "An error occurred: " + " "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                }

                // Log.d("Errors", error.toString());
                // Toast.makeText(AddCaseManager.this, "Error"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });

        // RequestQueue requestQueue = Volley.newRequestQueue(AddCaseManager.this);
        queue.add(jsonObjectRequest);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}