package com.example.myapplication.Model;

import java.sql.Date;
import java.util.Arrays;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;

public class Agency extends User {
    private int agencyId;
    private int cnssNumber;
    private byte[] cnssRegistrationImage;
    private int companyRegistrationNumber;
    private String managerFullName;
    private String agencyName;
    private int taxIdentificationNumber;
    
    public Agency(int userId, String username, Date accountCreationDate, String address, String city, String email,
            String userPassword, String userPhoneNumber, State profileState, UserType userType, int agencyId,
            int cnssNumber, byte[] cnssRegistrationImage, int companyRegistrationNumber, String managerFullName,
            String agencyName, int taxIdentificationNumber) {
        super(userId, username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, profileState,
                userType);
        this.agencyId = agencyId;
        this.cnssNumber = cnssNumber;
        this.cnssRegistrationImage = cnssRegistrationImage;
        this.companyRegistrationNumber = companyRegistrationNumber;
        this.managerFullName = managerFullName;
        this.agencyName = agencyName;
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Agency(int userId, String username, Date accountCreationDate, String address, String city, String email,
            String userPassword, String userPhoneNumber, State profileState, UserType userType) {
                super(userId, username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, profileState,
                userType);
    }

    public Agency(int agencyId, int cnssNumber, byte[] cnssRegistrationImage, int companyRegistrationNumber,
            String managerFullName, String agencyName, int taxIdentificationNumber, int userId) {
        super();
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public int getCnssNumber() {
        return cnssNumber;
    }

    public void setCnssNumber(int cnssNumber) {
        this.cnssNumber = cnssNumber;
    }

    public byte[] getCnssRegistrationImage() {
        return cnssRegistrationImage;
    }

    public void setCnssRegistrationImage(byte[] cnssRegistrationImage) {
        this.cnssRegistrationImage = cnssRegistrationImage;
    }

    public int getCompanyRegistrationNumber() {
        return companyRegistrationNumber;
    }

    public void setCompanyRegistrationNumber(int companyRegistrationNumber) {
        this.companyRegistrationNumber = companyRegistrationNumber;
    }

    public String getManagerFullName() {
        return managerFullName;
    }

    public void setManagerFullName(String managerFullName) {
        this.managerFullName = managerFullName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public int getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(int taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    @Override
    public String toString() {
        return "Agency [agencyId=" + agencyId + ", cnssNumber=" + cnssNumber + ", cnssRegistrationImage="
                + Arrays.toString(cnssRegistrationImage) + ", companyRegistrationNumber=" + companyRegistrationNumber
                + ", managerFullName=" + managerFullName + ", agencyName=" + agencyName + ", taxIdentificationNumber="
                + taxIdentificationNumber + "]";
    }
}
