package com.example.myapplication.Model;

import java.sql.Date;
import java.util.Arrays;

import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Extra.LicenseCategory;

public class Client extends User {
    
    private int clientId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String cin;
    private byte[] cinRecto;
    private byte[] cinVerso;
    private LicenseCategory licenseCategory;
    private Date licenseExpireDate;
    private Date licenseObtainDate;
    private byte[] licenseRecto;
    private byte[] licenseVerso;
    
    public Client(int userId, String username, Date accountCreationDate, String address, String city, String email,
            String userPassword, String userPhoneNumber, State profileState, UserType userType, int clientId,
            String firstName, String lastName, Date birthDate, String cin, byte[] cinRecto, byte[] cinVerso,
            LicenseCategory licenseCategory, Date licenseExpireDate, Date licenseObtainDate, byte[] licenseRecto, byte[] licenseVerso) {
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

    public byte[] getCinRecto() {
        return cinRecto;
    }

    public void setCinRecto(byte[] cinRecto) {
        this.cinRecto = cinRecto;
    }

    public byte[] getCinVerso() {
        return cinVerso;
    }

    public void setCinVerso(byte[] cinVerso) {
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

    public byte[] getLicenseRecto() {
        return licenseRecto;
    }

    public void setLicenseRecto(byte[] licenseRecto) {
        this.licenseRecto = licenseRecto;
    }

    public byte[] getLicenseVerso() {
        return licenseVerso;
    }

    public void setLicenseVerso(byte[] licenseVerso) {
        this.licenseVerso = licenseVerso;
    }

    @Override
    public String toString() {
        return "Client [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
                + birthDate + ", cin=" + cin + ", cinRecto=" + Arrays.toString(cinRecto) + ", cinVerso="
                + Arrays.toString(cinVerso) + ", licenseCategory=" + licenseCategory + ", licenseExpireDate="
                + licenseExpireDate + ", licenseObtainDate=" + licenseObtainDate + ", licenseRecto="
                + Arrays.toString(licenseRecto) + ", licenseVerso=" + Arrays.toString(licenseVerso) + "]";
    }

    
}
