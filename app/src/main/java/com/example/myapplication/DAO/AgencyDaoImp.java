package com.example.myapplication.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.myapplication.Model.Agency;
import com.example.myapplication.Util.ConnectionPool;

public class AgencyDaoImp implements AgencyDao {

    @Override
    public Agency getAgencyById(int agencyId) {
        String query = "call GetAgencyById(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, agencyId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractAgencyFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertAgency(long patentNumber, String managerFullName, String agencyName) {
        String query = "call InsertAgency(?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(3, patentNumber);
            preparedStatement.setString(4, managerFullName);
            preparedStatement.setString(5, agencyName);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAgency(String managerFullName, String agencyName) {
        String query = "call updateAgency(?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(2, managerFullName);
            preparedStatement.setString(3, agencyName);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Agency extractAgencyFromResultSet(ResultSet resultSet) throws SQLException {
        int agencyId = resultSet.getInt("agency_id");
        int cnssNumber = resultSet.getInt("cnss_number");
        byte[] cnssRegistrationImage = resultSet.getBytes("cnss_registration_image");
        int patentNumber = resultSet.getInt("company_registration_number");
        String managerFullName = resultSet.getString("manager_full_name");
        String agencyName = resultSet.getString("agency_name");
        int taxIdentificationNumber = resultSet.getInt("tax_identification_number");
        int userId = resultSet.getInt("user_id");

        return new Agency(patentNumber, managerFullName, agencyName);
    }
    
}
