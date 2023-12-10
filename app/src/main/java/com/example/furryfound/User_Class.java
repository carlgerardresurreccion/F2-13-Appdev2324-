package com.example.furryfound;

import java.util.HashMap;
import java.util.Map;

public class User_Class {
    private String user_id;
    private String first_name;
    private String last_name;
    private String address;
    private String phone_number;
    private String email;
    private String profile_picture;

    public User_Class() {}

    public User_Class(String first_name, String last_name, String address, String phone_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("first_name", first_name);
        map.put("last_name", last_name);
        map.put("address", address);
        map.put("phone_number", phone_number);
        map.put("email", email);
        map.put("user_id", user_id);
        map.put("profile_picture", profile_picture);
        // ... add additional fields to the map ...
        return map;
    }
}
