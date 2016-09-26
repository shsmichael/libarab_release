package com.example.michaelg.myapplication;

import android.widget.TextView;

import java.util.Date;

/**
 * Created by Misho on 9/21/2016.
 */
public class User {
    String firstname;
    String lastname;
    String gender;
    Date   bday;
    Boolean userType;
    String username;

    public User(){
    }

    public User(String firstname, String lastname, String gender, Date bday) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.bday = bday;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public Boolean getUserType() {
        return userType;
    }

    public void setUserType(Boolean userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
