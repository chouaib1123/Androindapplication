package com.example.myapplication.DAO;

import com.example.myapplication.Model.Agency;

public interface AgencyDao {
    Agency getAgencyById(int agencyId);
    void insertAgency(long patentNumber, String managerFullName, String agencyName);

    void updateAgency(String managerFullName, String agencyName);
}
