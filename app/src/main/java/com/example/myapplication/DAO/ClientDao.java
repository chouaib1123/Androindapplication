package com.example.myapplication.DAO;

import java.sql.Date;
import com.example.myapplication.Extra.LicenseCategory;
import com.example.myapplication.Model.Client;

public interface ClientDao {
    Client getClientByUsername(String clientUsername);
    void insertClient(String username, String address, String city,
                      String email, String userPassword, String userPhoneNumber, String firstName, String lastName,
                      String birthDate, String cin, String licenseCategory,
                      String licenseExpireDate, String licenseObtainDate);

    void updateClientCINImage(String clientUsername, byte[] cinRecto, byte[] cinVerso);

    void updateClientDrivingLicense(String clientUsername, Date licenseExpireDate,
                                    byte[] licenseRecto, byte[] licenseVerso);
}
