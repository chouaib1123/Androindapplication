package com.example.myapplication.Model;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.clientregister;

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

    public User() {}

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

    public User(String username, String userEmail, String password, String userPhoneNumber) {
        this.username = username;
        this.email = userEmail;
        this.userPassword = password;
        this.userPhoneNumber = userPhoneNumber;
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
    private String getVPassword(){
        return new clientregister().getVPassword();
    }
    public int isValid() {
        // 0. Check for No information
        // 1. Check for Email Match pattern
        // 2. Check for Password is empty
        // 3. Check the Password >= 8
        // 4. Check if userPassword == VerifyPassword
        // 5. Check if the UserPhone number len = 13 and starts with +
        // 6. Check if LicenseCategory is valid
        // 7. Check if licenseObtainDate is before licenseExpireDate
        // 8. Check if licenseExpireDate is greater than today

        if (TextUtils.isEmpty(getUsername()))
            return 0;
        else if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches())
            return 1;
        else if (TextUtils.isEmpty(getUserPassword()))
            return 2;
        else if (getUserPassword().length() <= 8)
            return 3;
        else if (!getUserPassword().equals(getVPassword()))
            return 4;
        else if (!(getUserPhoneNumber().length() == 13 && getUserPhoneNumber().startsWith("+")))
            return 5;
        else
            return -1;
    }
}