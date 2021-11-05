package com.example.mhealth.appointment_diary.utilitymodules;

public class Service {

    private int id;
    private String name;
    //private String service_id;

   /* private int partner_type_id;
    private String phone_no;
    private String location;
    private String created_by;
    private String updated_by;
    private String createdAt;
    private  String updatedAt;
    private String deleteAt;*/

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public String getService_id() {
        return service_id;
    }*/

   /* public void setService_id(String service_id) {
        this.service_id = service_id;
    }*/


    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof Service)) {
            return false;
        }
        Service f = (Service) anotherObject;
        return (this.id == f.id);
    }





}