package com.example.michaelg.myapplication;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Misho on 9/21/2016.
 */
public class User implements Serializable{
    String firstname;
    String lastname;
    String gender;
    String bday;
    String userType;
    String username;
    Boolean isWantToPlay;

    public Boolean getWantToPlay() {
        return isWantToPlay;
}

    public void setWantToPlay(Boolean wantToPlay) {
        isWantToPlay = wantToPlay;
    }

    public User(String firstname, String lastname, String gender, String bday){
        this.firstname = firstname;
        this.lastname  =lastname;
        this.gender = gender;
        this.bday = bday;
    }

    public User() {
    }

    public User(String firstname, String lastname, String gender, String bday, String userType, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.bday = bday;
        this.userType = userType;
        this.username = username;
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

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
