package com.example.mhealth.appointment_diary.utilitymodules;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.example.mhealth.appointment_diary.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeVisitChecklist extends AppCompatActivity {
    Button send;
    EditText arvstoredE,  whodisdclosedE, arvstakenE, NotesE;

    Spinner independentS, needsS, sexualpartnerS, disclosehouseholdS, socialsupportS, socialsupportcommunityS, nonclinicalS, mentalhealthS,
            stressfulsituationS, drugs_alcoholS, side_effectsS;

 /*    "patient_independent": "Yes",
             "basic_need": "Yes",
             "sexual_partner": "No",
             "disclosed_hiv_status": "Yes",
             "disclosed_person": "Partner Full Name", edit
             "arv_stored": "Yes", edit
             "arv_taken": "Yes",
             "social_support_household": "Yes",
             "social_support_community": "No",
             "non_clinical_services": "No",
             "mental_health": "No",
             "stress_situation": "No",
             "use_drug": "No",
             "side_effect": "No",
                other_note edit*/



    String[] patient_independent = {"--Select Independence--", "Yes", "No"};
    String[] basic_needs_met = {"--Select if Needs Met--", "Yes", "No"};
    String[] sexual_partner = {"--Has  sexual_partner--", "Yes", "No"};
    String[] disclosed_hiv_status = {"--Disclosed Hiv Status--", "Yes", "No"};
    String[] social_support_household = {"--Household Social Support--", "Yes", "No"};
    String[] social_support_community = {"--Community Social Support--", "Yes", "No"};

    String[] non_clinical_services = {"--Non Clinical Services--", "Yes", "No"};
    String[] mental_health = {"--Have Mental Health--", "Yes", "No"};
    String[] stress_situation = {"--Stressful Situation--", "Yes", "No"};
    String[] use_drug = {"--Use Drugs--", "Yes", "No"};
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visit_checklist);


        send= (Button) findViewById(R.id.btn_save);
        whodisdclosedE = (EditText) findViewById(R.id.whodisdclosed);
        arvstoredE = (EditText) findViewById(R.id.arvstored);
        arvstakenE = (EditText) findViewById(R.id.arvstaken);
        NotesE = (EditText) findViewById(R.id.Notes);

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


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sev("0718373569","1234500002",  "family one", "0712311264",
                        "landmark Roysambu", patient_independent_st,   basic_needs_met_st,  sexual_partner_st,
                        disclosed_hiv_status_st,  whodisdclosedE.getText().toString(), arvstoredE.getText().toString(), arvstakenE.getText().toString(),
                        social_support_household_st,  social_support_community_st,  non_clinical_services_st,
                         mental_health_st, stress_situation_st, use_drug_st, side_effect_st, NotesE.getText().toString());
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
                        showToast(message); // Replace with your display logic
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
                if (error.networkResponse != null) {
                    int statusCode = error.networkResponse.statusCode;
                    // Handle HTTP errors
                } else {
                    // Handle network error or timeout
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