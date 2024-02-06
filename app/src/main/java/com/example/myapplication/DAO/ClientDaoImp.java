package com.example.myapplication.DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.myapplication.Extra.LicenseCategory;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Model.Client;
import com.example.myapplication.Util.DatabaseUtil;
import com.google.firebase.database.DatabaseReference;

public class ClientDaoImp implements ClientDao {

    @Override
    public Client getClientByUsername(String clientUsername) {
        return null;
    }

    @Override
    public void insertClient(String username, String address, String city,
                             String email, String userPassword, String userPhoneNumber, String firstName, String lastName,
                             String birthDate, String cin, String licenseCategory,
                             String licenseExpireDate, String licenseObtainDate) {

        String currentDate = new java.sql.Date(System.currentTimeMillis()).toString();
        DatabaseReference identifier = DatabaseUtil.connect().child("Client").child(username);

        identifier.child("accountCreationDate").setValue(currentDate);
        identifier.child("address").setValue(address);
        identifier.child("city").setValue(city);
        identifier.child("email").setValue(email);
        identifier.child("password").setValue(userPassword);
        identifier.child("phoneNumber").setValue(userPhoneNumber);
        identifier.child("profileState").setValue(State.PENDING.toString());
        identifier.child("userType").setValue(UserType.CLIENT.toString());
        identifier.child("firstName").setValue(firstName);
        identifier.child("lastName").setValue(lastName);
        identifier.child("birthDate").setValue(birthDate);
        identifier.child("cinNumber").setValue(cin);
        identifier.child("drivingLicenseCategory").setValue(licenseCategory);
        identifier.child("drivingLicenseObtainDate").setValue(licenseObtainDate);
        identifier.child("drivingLicenseExpireDate").setValue(licenseExpireDate);
    }

    @Override
    public void updateClientCINImage(String clientUsername, byte[] cinRecto, byte[] cinVerso) {

    }

    @Override
    public void updateClientDrivingLicense(String clientUsername, Date licenseExpireDate, byte[] licenseRecto, byte[] licenseVerso) {

    }

    private Client extractClientFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
}
