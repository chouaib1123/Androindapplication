package com.example.myapplication.Model;

import com.example.myapplication.Model.Extra.State;
import com.example.myapplication.Model.Extra.UserType;

import java.sql.Date;

public abstract class User {
    private int userId;
    private String username;
    private Date accountCreationDate;
    private String address;
    private String city;
    private String email;
    private String userPassword;
    private String userPhoneNumber;
    private State profileState;
    private UserType userType;

    public User(int userId, String username, Date accountCreationDate, String address, String city, String email,
                String userPassword, String userPhoneNumber, State profileState, UserType userType) {
        this.userId = userId;
        this.username = username;
        this.accountCreationDate = accountCreationDate;
        this.address = address;
        this.city = city;
        this.email = email;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.profileState = profileState;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(Date accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public State getProfileState() {
        return profileState;
    }

    public void setProfileState(State profileState) {
        this.profileState = profileState;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", accountCreationDate=" + accountCreationDate
                + ", address=" + address + ", city=" + city + ", email=" + email + ", userPassword=" + userPassword
                + ", userPhoneNumber=" + userPhoneNumber + ", profileState=" + profileState + ", userType=" + userType
                + "]";
    }
}