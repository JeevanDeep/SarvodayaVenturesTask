package com.jeevan.sarvodayaventurestask.model;

public class ContactsModel {
    private String phoneNumber,id,name;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactsModel() {

    }

    public ContactsModel(String phoneNumber, String id, String name) {

        this.phoneNumber = phoneNumber;
        this.id = id;
        this.name = name;
    }
}
