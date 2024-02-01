package com.example.myapplication.DAO;

import com.example.myapplication.Model.Agency;

public interface AgencyDao {
    Agency getAgencyById(int agencyId);
    void insertAgency(int cnssNumber, byte[] cnssRegistrationImage,
                      int companyRegistrationNumber, String managerFullName,
                      String agencyName, int taxIdentificationNumber, int userId);

    void updateAgency(int agencyId, String managerFullName, String agencyName);
}
