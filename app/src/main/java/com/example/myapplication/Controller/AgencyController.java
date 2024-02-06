package com.example.myapplication.Controller;

import com.example.myapplication.DAO.AgencyDao;
import com.example.myapplication.Model.Agency;

public class AgencyController {
    private final AgencyDao agencyDao;

    public AgencyController(AgencyDao agencyDao) {
        this.agencyDao = agencyDao;
    }

    public Agency getAgencyByUsername(String agencyUsername) {
        return agencyDao.getAgencyByUsername(agencyUsername);
    }

    public void registerAgency(long patentNumber, String managerFullName, String agencyName) {
        agencyDao.insertAgency(patentNumber, managerFullName, agencyName);
    }

    public void updateAgencyDetails(String agencyUsername, String managerFullName, String agencyName) {
        agencyDao.updateAgency(agencyUsername, managerFullName, agencyName);
    }
}
