package com.example.mhealth.appointment_diary.models;

public class RescheduledRequestsModel {

    public int clinic_id, appointment_id, reschedule_id;
  public  String clinic_no, client_name,  client_phone_no, appntmnt_date, reschedule_date, reason, appointment_type;

    public RescheduledRequestsModel() {

    }

    public RescheduledRequestsModel(int clinic_id, String clinic_no, String client_name, String client_phone_no, String appntmnt_date, String reschedule_date, String reason, String appointment_type, int appointment_id, int reschedule_id) {
        this.clinic_id = clinic_id;
        this.clinic_no = clinic_no;
        this.client_name = client_name;
        this.client_phone_no = client_phone_no;
        this.appntmnt_date = appntmnt_date;
        this.reschedule_date = reschedule_date;
        this.reason = reason;
        this.appointment_type = appointment_type;
        this.appointment_id = appointment_id;
        this.reschedule_id= reschedule_id;

    }

    public int getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(int clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getClinic_no() {
        return clinic_no;
    }

    public void setClinic_no(String clinic_no) {
        this.clinic_no = clinic_no;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_phone_no() {
        return client_phone_no;
    }

    public void setClient_phone_no(String client_phone_no) {
        this.client_phone_no = client_phone_no;
    }

    public String getAppntmnt_date() {
        return appntmnt_date;
    }

    public void setAppntmnt_date(String appntmnt_date) {
        this.appntmnt_date = appntmnt_date;
    }





    public String getReschedule_date() {
        return reschedule_date;
    }

    public void setReschedule_date(String reschedule_date) {
        this.reschedule_date = reschedule_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAppointment_type() {
        return appointment_type;
    }

    public void setAppointment_type(String appointment_type) {
        this.appointment_type = appointment_type;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getReschedule_id() {
        return reschedule_id;
    }

    public void setReschedule_id(int reschedule_id) {
        this.reschedule_id = reschedule_id;
    }
}
