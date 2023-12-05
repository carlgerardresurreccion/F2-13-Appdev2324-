package com.example.furryfound;

public class User {
    String user_id;
    String full_name;
    String address;
    String phone_number;
    String email;

    public User(String full_name, String address, String phone_number, String email) {
        this.full_name = full_name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public String getFull_name() {
        return this.full_name;
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

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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
