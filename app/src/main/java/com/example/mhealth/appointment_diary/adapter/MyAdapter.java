package com.example.mhealth.appointment_diary.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mhealth.appointment_diary.appointment_diary.FragmentEidNegative;
import com.example.mhealth.appointment_diary.appointment_diary.FragmentEidPositive;

public class MyAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentEidPositive eiDpositive = new FragmentEidPositive();
                return eiDpositive;
            case 1:
                FragmentEidNegative eiDnegative = new FragmentEidNegative();
                return eiDnegative;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
