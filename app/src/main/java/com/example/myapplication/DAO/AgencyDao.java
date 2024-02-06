package com.example.myapplication.DAO;

import com.example.myapplication.Model.Agency;

public interface AgencyDao {
    Agency getAgencyByUsername(String agencyName);
    void insertAgency(String agencyUsername, String address, String city,
                      String email, String userPassword, String userPhoneNumber,
                      String patentNumber, String managerFullName, String agencyName);

    void updateAgency(String agencyUsername, String managerFullName, String agencyName);
}
