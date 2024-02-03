package com.example.myapplication.Model;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Extra.LicenseCategory;

import java.sql.Date;
import java.util.Arrays;
import  com.example.myapplication.clientregister;

public class Client extends User {

    private int clientId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String cin;
    private String cinRecto;
    private String cinVerso;
    private LicenseCategory licenseCategory;
    private Date licenseExpireDate;
    private Date licenseObtainDate;
    private String licenseRecto;
    private String licenseVerso;

    public Client(int userId, String username, Date accountCreationDate, String address, String city, String email,
                  String userPassword, String userPhoneNumber, State profileState, UserType userType, int clientId,
                  String firstName, String lastName, Date birthDate, String cin, String cinRecto, String cinVerso,
                  LicenseCategory licenseCategory, Date licenseExpireDate, Date licenseObtainDate, String  licenseRecto, String licenseVerso) {
        super(userId, username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, profileState, userType);
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.cin = cin;
        this.cinRecto = cinRecto;
        this.cinVerso = cinVerso;
        this.licenseCategory = licenseCategory;
        this.licenseExpireDate = licenseExpireDate;
        this.licenseObtainDate = licenseObtainDate;
        this.licenseRecto = licenseRecto;
        this.licenseVerso = licenseVerso;
    }

    public Client(int userId, String username, Date accountCreationDate, String address, String city, String email,
                  String userPassword, String userPhoneNumber, State profileState, UserType userType) {
        super(userId, username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, profileState,
                userType);
    }

    public Client(int clientId2, String firstName2, String lastName2, Date birthDate2, String cin2, byte[] cinRecto2,
                  byte[] cinVerso2, LicenseCategory licenseCategory2, Date licenseExpireDate2, Date licenseObtainDate2,
                  byte[] licenseRecto2, byte[] licenseVerso2, int userId) {
        super();
    }

    public Client(String username, String userEmail, String password, String userPhoneNumber) {
        super();
    }


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCinRecto() {
        return cinRecto;
    }

    public void setCinRecto(String cinRecto) {
        this.cinRecto = cinRecto;
    }

    public String getCinVerso() {
        return cinVerso;
    }

    public void setCinVerso(String cinVerso) {
        this.cinVerso = cinVerso;
    }

    public LicenseCategory getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(LicenseCategory licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public Date getLicenseExpireDate() {
        return licenseExpireDate;
    }

    public void setLicenseExpireDate(Date licenseExpireDate) {
        this.licenseExpireDate = licenseExpireDate;
    }

    public Date getLicenseObtainDate() {
        return licenseObtainDate;
    }

    public void setLicenseObtainDate(Date licenseObtainDate) {
        this.licenseObtainDate = licenseObtainDate;
    }

    public String getLicenseRecto() {
        return licenseRecto;
    }

    public void setLicenseRecto(String licenseRecto) {
        this.licenseRecto = licenseRecto;
    }

    public String getLicenseVerso() {
        return licenseVerso;
    }

    public void setLicenseVerso(String licenseVerso) {
        this.licenseVerso = licenseVerso;
    }

    @Override
    public String toString() {
        return "Client [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
                + birthDate + ", cin=" + cin + ", cinRecto=" + cinRecto + ", cinVerso="
                + cinVerso + ", licenseCategory=" + licenseCategory + ", licenseExpireDate="
                + licenseExpireDate + ", licenseObtainDate=" + licenseObtainDate + ", licenseRecto="
                + licenseRecto + ", licenseVerso=" + licenseVerso + "]";
    }
    private String getVPassword(){
        return new  clientregister().getVPassword();
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
