package com.example.myapplication.Controller;

import java.sql.Date;
import com.example.myapplication.DAO.ClientDao;
import com.example.myapplication.Extra.LicenseCategory;
import com.example.myapplication.Model.Client;

public class ClientController {
    private ClientDao clientDao;

    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    public Client getClientById(int clientId) {
        return clientDao.getClientById(clientId);
    }

    public void registerClient(String firstName, String lastName, Date birthDate, String cin,
                               byte[] cinRecto, byte[] cinVerso, LicenseCategory licenseCategory,
                               Date licenseExpireDate, Date licenseObtainDate,
                               byte[] licenseRecto, byte[] licenseVerso, int userId) {
        clientDao.insertClient(firstName, lastName, birthDate, cin, cinRecto, cinVerso,
                                licenseCategory, licenseExpireDate, licenseObtainDate,
                                licenseRecto, licenseVerso, userId);
    }

    public void updateClientCINImage(int clientId, byte[] cinRecto, byte[] cinVerso) {
        clientDao.updateClientCINImage(clientId, cinRecto, cinVerso);
    }

    public void updateClientDrivingLicense(int clientId, Date licenseExpireDate,
                                           byte[] licenseRecto, byte[] licenseVerso) {
        clientDao.updateClientDrivingLicense(clientId, licenseExpireDate, licenseRecto, licenseVerso);
    }
}

