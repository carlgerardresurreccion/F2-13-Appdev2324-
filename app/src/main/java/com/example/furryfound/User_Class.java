package com.example.furryfound;

public class User_Class {
    String user_id;
    String firstname;
    String lastname;
    String address;
    String phone_number;
    String email;
    String profile;

    public User_Class(String firstname, String lastname, String address, String phone_number, String email, String profile) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.profile = profile;
    }

    public User_Class() {
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUser_id() {
        return this.user_id;
    }
    public String getAddress() {
        return this.address;
    }
    public String getPhone_number() {
        return this.phone_number;
    }
    public String getEmail() {
        return this.email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
