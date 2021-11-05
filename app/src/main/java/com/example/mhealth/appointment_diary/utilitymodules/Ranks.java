package com.example.mhealth.appointment_diary.utilitymodules;

public class Ranks {

    private int id;
    private String rank_name;

    public Ranks() {
    }

    public Ranks(int id, String rank_name) {
        this.id = id;
        this.rank_name = rank_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id =id;
    }

    public String getRank_name() {
        return rank_name;
    }

    public void setRank_name(String rank_name) {
        this.rank_name = rank_name;
    }
}
