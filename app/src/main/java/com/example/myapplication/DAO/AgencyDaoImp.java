package com.example.myapplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.myapplication.Model.Agency;
import com.example.myapplication.Util.ConnectionPool;
import com.example.myapplication.Util.DatabaseUtil;
import com.google.firebase.database.DatabaseReference;

public class AgencyDaoImp implements AgencyDao {

    @Override
    public Agency getAgencyByUsername(String agencyName) {
        return null;
    }

    @Override
    public void insertAgency(String agencyUsername, String address, String city,
                             String email, String userPassword, String userPhoneNumber,
                             String patentNumber, String managerFullName, String agencyName) {
        DatabaseReference identifier = DatabaseUtil.connect().child("Agency").child(agencyUsername);

        identifier.child("email").setValue(email);
        identifier.child("password").setValue(userPassword);
        identifier.child("managerFullName").setValue(managerFullName);
        identifier.child("agencyName").setValue(agencyName);
        identifier.child("city").setValue(city);
        identifier.child("address").setValue(address);
        identifier.child("phoneNumber").setValue(userPhoneNumber);
        identifier.child("patentNumber").setValue(patentNumber);
    }

    @Override
    public void updateAgency(String agencyUsername, String managerFullName, String agencyName) {

    }

    private Agency extractAgencyFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
    
}
