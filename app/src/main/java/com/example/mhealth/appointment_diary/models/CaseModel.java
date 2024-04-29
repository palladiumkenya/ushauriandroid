package com.example.mhealth.appointment_diary.models;

public class CaseModel {
    private int id;
    private int clientId;
    private int providerId;
    private String reasonAssign;
    private String relationship;
    private String startDate;
    private String endDate;

    public CaseModel(int id, int clientId, int providerId, String reasonAssign, String relationship, String startDate, String endDate) {
        this.id = id;
        this.clientId = clientId;
        this.providerId = providerId;
        this.reasonAssign = reasonAssign;
        this.relationship = relationship;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getReasonAssign() {
        return reasonAssign;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

