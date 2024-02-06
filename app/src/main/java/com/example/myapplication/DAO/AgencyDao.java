package com.example.myapplication.DAO;

import com.example.myapplication.Model.Agency;

public interface AgencyDao {
    Agency getAgencyByUsername(String agencyName);
    void insertAgency(long patentNumber, String managerFullName, String agencyName);

    void updateAgency(String agencyUsername, String managerFullName, String agencyName);
}
