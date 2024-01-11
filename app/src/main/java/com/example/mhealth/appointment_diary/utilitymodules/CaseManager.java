package com.example.mhealth.appointment_diary.utilitymodules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mhealth.appointment_diary.R;

public class CaseManager extends Fragment
{
    public  CaseManager(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_case, container, false);
    }
}
