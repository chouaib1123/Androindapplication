package com.example.myapplication.Model;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.myapplication.Model.Extra.State;
import com.example.myapplication.Model.Extra.UserType;
import com.example.myapplication.Model.Extra.LicenseCategory;

import java.sql.Date;
import java.util.Arrays;

public class Client extends User implements IRegisterClient {

    private int clientId;
    private String VPassword;
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



    public Client(int userId , String VPassword, String username, Date accountCreationDate, String address, String city, String email,
                  String userPassword, String userPhoneNumber, State profileState, UserType userType, int clientId,
                  String firstName, String lastName, Date birthDate, String cin, byte[] cinRecto, byte[] cinVerso,
                  LicenseCategory licenseCategory, Date licenseExpireDate, Date licenseObtainDate, byte[] licenseRecto,
                  byte[] licenseVerso) {
        super(userId, username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, profileState,
                userType);
        this.clientId = clientId;
        this.VPassword = VPassword;
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

    public Client(String userEmail, String userPassword, String VPassword, String userPhoneNumber, LicenseCategory licenseCategory ,Date licenseExpireDate ,Date licenseObtainDate ) {
        // Assuming you want to provide default or initial values for other attributes
        super(0, null, null, null, null, userEmail, userPassword, userPhoneNumber, null, null);

        // Set specific attributes for email, password, and phone number
        this.VPassword = VPassword;
        this.licenseCategory = licenseCategory;
        this.licenseExpireDate = licenseExpireDate;
        this.licenseObtainDate = licenseObtainDate;
    }


    public int getClientId() {
        return clientId;
    }
    public String getVPassword() {
        return VPassword;
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

    @Override
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

        if (TextUtils.isEmpty(getEmail()))
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
        else if (!isValidLicenseCategory())
            return 6;
        else if (!isLicenseDatesValid())
            return 7;
        else if (!isLicenseExpireDateValid())
            return 8;
        else
            return -1;
    }

    private boolean isValidLicenseCategory() {
        try {
            LicenseCategory.valueOf(licenseCategory.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isLicenseDatesValid() {
        return licenseObtainDate != null && licenseExpireDate != null && licenseObtainDate.before(licenseExpireDate);
    }

    private boolean isLicenseExpireDateValid() {
        java.util.Date today = new java.util.Date();
        return licenseExpireDate != null && licenseExpireDate.after(today);
    }



}
