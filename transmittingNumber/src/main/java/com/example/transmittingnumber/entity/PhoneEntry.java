package com.example.transmittingnumber.entity;

public class PhoneEntry {
    private int id;
    private String lastName;
    private String phoneNumber;

    public PhoneEntry() {}

    public PhoneEntry(String lastName, String phoneNumber) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
