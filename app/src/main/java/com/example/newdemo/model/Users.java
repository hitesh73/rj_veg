package com.example.newdemo.model;

import android.text.Editable;

public class Users {
    public String mobile;
    public String name,email,password,address,pincode;

    public Users() {
    }

    public Users(String mobile, String name, String email, String password, String address, String pincode) {
        this.mobile = mobile;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.pincode = pincode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
