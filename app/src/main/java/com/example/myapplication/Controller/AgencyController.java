package com.example.myapplication.Controller;

import com.example.myapplication.DAO.AgencyDao;
import com.example.myapplication.Model.Agency;

public class AgencyController {
    private final AgencyDao agencyDao;

    public AgencyController(AgencyDao agencyDao) {
        this.agencyDao = agencyDao;
    }

    public Agency getAgencyById(int agencyId) {
        return agencyDao.getAgencyById(agencyId);
    }

    public void registerAgency(long patentNumber, String managerFullName, String agencyName) {
        agencyDao.insertAgency(patentNumber, managerFullName, agencyName);
    }

    public void updateAgencyDetails(String managerFullName, String agencyName) {
        agencyDao.updateAgency(managerFullName, agencyName);
    }
}
