package com.example.mhealth.appointment_diary.pmtct;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.example.mhealth.appointment_diary.R;


public class HomeFragment extends Fragment {


   // private Unbinder unbinder;
    private View root;
    private Context context;





    CardView unscheduled_hei_apt;
    CardView hei_apt;
    CardView mother_apt;
    CardView update_hei;
    CardView reg_caregiver;
    CardView reg_hei;
    CardView pcr_positive_enrollment;
    CardView final_outcome;
    CardView pregnant_mothers;





    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
       // unbinder = ButterKnife.bind(this, root);

        unscheduled_hei_apt= root.findViewById(R.id.unscheduled_hei_apt);
        hei_apt= root.findViewById(R.id.hei_apt);
        mother_apt= root.findViewById(R.id.mother_apt);
        update_hei= root.findViewById(R.id.update_hei);
        reg_caregiver= root.findViewById(R.id.reg_caregiver);
        reg_hei= root.findViewById(R.id.reg_hei);
        pcr_positive_enrollment= root.findViewById(R.id.pcr_positive_enrollment);
        final_outcome= root.findViewById(R.id.final_outcome);
        pregnant_mothers= root.findViewById(R.id.pregnant_mothers);



        unscheduled_hei_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_exposures);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.unscheduled_hei_apt);

            }
        });

        pregnant_mothers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.pregnantBreastfeeding);

            }
        });

        hei_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Navigation.findNavController(v).navigate(R.id.nav_immunizations);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.hei_apt);

            }
        });

        mother_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_broadcasts);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.book_mother_apt);

            }
        });

        update_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_resources);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.update_hei);

            }
        });

        reg_caregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_check_in);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.caregiver_reg);

            }
        });

        reg_hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_feedback);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.hei_reg);

            }
        });

        pcr_positive_enrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_feedback);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.pcr_positive_enrollment);

            }
        });


        final_outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.nav_feedback);
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.hei_final_outcome);

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      //  unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}