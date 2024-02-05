package com.example.myapplication.Model;

import java.sql.Date;
import java.util.Arrays;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;

public class Agency extends User {
    private int patentNumber;
    private String managerFullName;
    private String agencyName;
    
    public Agency(String username, Date accountCreationDate, String address, String city, String email,
            String userPassword, String userPhoneNumber, State profileState, UserType userType, int patentNumber, String managerFullName,
            String agencyName) {
        super(username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, profileState,
                userType);
        this.patentNumber = patentNumber;
        this.managerFullName = managerFullName;
        this.agencyName = agencyName;
    }

    public Agency(String username, Date accountCreationDate, String address, String city, String email,
            String userPassword, String userPhoneNumber, State profileState, UserType userType) {
                super(username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, profileState,
                userType);
    }

    public Agency(int patentNumber,
            String managerFullName, String agencyName) {
        super();
    }

    public Agency() {}

    public int getPatentNumber() {
        return patentNumber;
    }

    public void setPatentNumber(int patentNumber) {
        this.patentNumber = patentNumber;
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

    @Override
    public String toString() {
        return "Agency [patentNumber=" + patentNumber
                + ", managerFullName=" + managerFullName + ", agencyName=" + agencyName + "]";
    }
}
