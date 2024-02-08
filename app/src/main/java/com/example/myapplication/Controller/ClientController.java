package com.example.myapplication.Controller;

import java.sql.Date;
import com.example.myapplication.DAO.ClientDao;
import com.example.myapplication.DAO.ClientDaoImp;
import com.example.myapplication.Extra.LicenseCategory;
import com.example.myapplication.Model.Client;

public class ClientController {
    private final ClientDao clientDao;

    public ClientController() {
        this.clientDao = new ClientDaoImp();
    }

    public Client getClientByUsername(String clientUsername) {
        return clientDao.getClientByUsername(clientUsername);
    }

    public void registerClient(String username, String address, String city,
                               String email, String userPassword, String userPhoneNumber, String firstName, String lastName,
                               String birthDate, String cin, String licenseCategory,
                               String licenseExpireDate, String licenseObtainDate) {
        clientDao.insertClient(username, address, city, email, userPassword, userPhoneNumber, firstName, lastName,
                 birthDate, cin, licenseCategory, licenseExpireDate, licenseObtainDate);
    }

    public void updateClientCINImage(String clientUsername, byte[] cinRecto, byte[] cinVerso) {
        clientDao.updateClientCINImage(clientUsername, cinRecto, cinVerso);
    }

    public void updateClientDrivingLicense(String clientUsername, Date licenseExpireDate,
                                           byte[] licenseRecto, byte[] licenseVerso) {
        clientDao.updateClientDrivingLicense(clientUsername, licenseExpireDate, licenseRecto, licenseVerso);
    }
}

