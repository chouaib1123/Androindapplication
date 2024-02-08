package com.example.myapplication.Controller;

import com.example.myapplication.DAO.AgencyDao;
import com.example.myapplication.DAO.AgencyDaoImp;
import com.example.myapplication.Model.Agency;

public class AgencyController {
    private final AgencyDao agencyDao;

    public AgencyController() {
        this.agencyDao = new AgencyDaoImp();
    }

    public Agency getAgencyByUsername(String agencyUsername) {
        return agencyDao.getAgencyByUsername(agencyUsername);
    }

    public void registerAgency(String agencyUsername, String address, String city,
                               String email, String userPassword, String userPhoneNumber, String patentNumber,
                               String managerFullName, String agencyName) {
        agencyDao.insertAgency(agencyUsername, address, city, email, userPassword, userPhoneNumber,
                patentNumber, managerFullName, agencyName);
    }

    public void updateAgencyDetails(String agencyUsername, String managerFullName, String agencyName) {
        agencyDao.updateAgency(agencyUsername, managerFullName, agencyName);
    }
}
