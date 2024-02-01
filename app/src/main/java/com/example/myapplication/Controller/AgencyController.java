package com.example.myapplication.Controller;

import com.example.myapplication.DAO.AgencyDao;
import com.example.myapplication.Model.Agency;

public class AgencyController {
    private AgencyDao agencyDao;

    public AgencyController(AgencyDao agencyDao) {
        this.agencyDao = agencyDao;
    }

    public Agency getAgencyById(int agencyId) {
        return agencyDao.getAgencyById(agencyId);
    }

    public void registerAgency(int cnssNumber, byte[] cnssRegistrationImage,
                               int companyRegistrationNumber, String managerFullName,
                               String agencyName, int taxIdentificationNumber, int userId) {
        agencyDao.insertAgency(cnssNumber, cnssRegistrationImage, companyRegistrationNumber,
                                managerFullName, agencyName, taxIdentificationNumber, userId);
    }

    public void updateAgencyDetails(int agencyId, String managerFullName, String agencyName) {
        agencyDao.updateAgency(agencyId, managerFullName, agencyName);
    }
}
