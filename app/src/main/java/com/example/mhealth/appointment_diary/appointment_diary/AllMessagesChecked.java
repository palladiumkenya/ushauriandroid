package com.example.mhealth.appointment_diary.appointment_diary;

import com.orm.SugarRecord;

public class AllMessagesChecked extends SugarRecord {

    String allchecked;

    public AllMessagesChecked(){}

    public AllMessagesChecked(String allchecked) {
        this.allchecked = allchecked;
    }

    public String getAllchecked() {
        return allchecked;
    }

    public void setAllchecked(String allchecked) {
        this.allchecked = allchecked;
    }
}
