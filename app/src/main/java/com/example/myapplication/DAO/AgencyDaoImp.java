package com.example.myapplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.myapplication.Model.Agency;
import com.example.myapplication.Util.ConnectionPool;

public class AgencyDaoImp implements AgencyDao {

    @Override
    public Agency getAgencyByUsername(String agencyName) {
        return null;
    }

    @Override
    public void insertAgency(long patentNumber, String managerFullName, String agencyName) {

    }

    @Override
    public void updateAgency(String agencyUsername, String managerFullName, String agencyName) {

    }

    private Agency extractAgencyFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
    
}
