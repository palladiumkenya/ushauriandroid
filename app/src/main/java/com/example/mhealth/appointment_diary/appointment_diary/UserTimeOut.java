package com.example.mhealth.appointment_diary.appointment_diary;

import com.orm.SugarRecord;

public class UserTimeOut extends SugarRecord {

    String lasttime;

    public UserTimeOut() {
    }

    public UserTimeOut(String lt) {

        this.lasttime = lt;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }
}
