package com.example.myapplication.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.myapplication.Extra.LicenseCategory;
import com.example.myapplication.Model.Client;
import com.example.myapplication.Util.ConnectionPool;

public class ClientDaoImp implements ClientDao {

    @Override
    public Client getClientById(int clientId) {
        String query = "call GetClientById(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractClientFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertClient(String firstName, String lastName, Date birthDate, String cin,
                             byte[] cinRecto, byte[] cinVerso, LicenseCategory licenseCategory,
                             Date licenseExpireDate, Date licenseObtainDate,
                             byte[] licenseRecto, byte[] licenseVerso, int userId) {
        String query = "call InsertClient(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, birthDate);
            preparedStatement.setString(4, cin);
            preparedStatement.setBytes(5, cinRecto);
            preparedStatement.setBytes(6, cinVerso);
            preparedStatement.setString(7, licenseCategory.toString());
            preparedStatement.setDate(8, licenseExpireDate);
            preparedStatement.setDate(9, licenseObtainDate);
            preparedStatement.setBytes(10, licenseRecto);
            preparedStatement.setBytes(11, licenseVerso);
            preparedStatement.setInt(12, userId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClientCINImage(int clientId, byte[] cinRecto, byte[] cinVerso) {
        String query = "call updateClientCINImage(?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBytes(1, cinRecto);
            preparedStatement.setBytes(2, cinVerso);
            preparedStatement.setInt(3, clientId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClientDrivingLicense(int clientId, Date licenseExpireDate,
                                           byte[] licenseRecto, byte[] licenseVerso) {
        String query = "call updateClientDrivingLicense(?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, clientId);
            preparedStatement.setDate(2, licenseExpireDate);
            preparedStatement.setBytes(3, licenseRecto);
            preparedStatement.setBytes(4, licenseVerso);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client extractClientFromResultSet(ResultSet resultSet) throws SQLException {
        int clientId = resultSet.getInt("client_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        Date birthDate = resultSet.getDate("birth_date");
        String cin = resultSet.getString("cin");
        byte[] cinRecto = resultSet.getBytes("cin_recto");
        byte[] cinVerso = resultSet.getBytes("cin_verso");
        LicenseCategory licenseCategory = LicenseCategory.valueOf(resultSet.getString("driving_license_category"));
        Date licenseExpireDate = resultSet.getDate("driving_license_expire_date");
        Date licenseObtainDate = resultSet.getDate("driving_license_obtain_date");
        byte[] licenseRecto = resultSet.getBytes("driving_license_recto");
        byte[] licenseVerso = resultSet.getBytes("driving_license_verso");
        int userId = resultSet.getInt("user_id");

        return new Client(clientId, firstName, lastName, birthDate, cin,
                cinRecto, cinVerso, licenseCategory, licenseExpireDate, licenseObtainDate,
                licenseRecto, licenseVerso, userId);
    }
}
