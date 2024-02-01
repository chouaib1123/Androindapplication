package com.example.myapplication.DAO;

import java.sql.Date;
import com.example.myapplication.Extra.LicenseCategory;
import com.example.myapplication.Model.Client;

public interface ClientDao {
    Client getClientById(int clientId);
    void insertClient(String firstName, String lastName, Date birthDate, String cin,
                      byte[] cinRecto, byte[] cinVerso, LicenseCategory licenseCategory,
                      Date licenseExpireDate, Date licenseObtainDate,
                      byte[] licenseRecto, byte[] licenseVerso, int userId);

    void updateClientCINImage(int clientId, byte[] cinRecto, byte[] cinVerso);

    void updateClientDrivingLicense(int clientId, Date licenseExpireDate,
                                    byte[] licenseRecto, byte[] licenseVerso);
}
