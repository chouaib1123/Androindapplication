package com.example.myapplication.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Model.Agency;
import com.example.myapplication.Model.Client;
import com.example.myapplication.Model.User;
import com.example.myapplication.Util.ConnectionPool;

public class UserDaoImp implements UserDao {

    @Override
    public User getUserById(int userId) {
        String query = "call GetUserById(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        String query = "call GetUserByUsername(?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertUser(String username, Date accountCreationDate, String address, String city, String email,
                           String userPassword, String userPhoneNumber, UserType userType) {
        String query = "call InsertUser(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            // preparedStatement.setDate(2, new java.sql.Date(accountCreationDate.getTime())); // kept it just in case we countered any error using the following line
            preparedStatement.setDate(2, accountCreationDate);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, userPassword);
            preparedStatement.setString(7, userPhoneNumber);
            preparedStatement.setString(8, userType.toString());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(affectedRows + " rows have been affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(int userId, String address, String city, String email, String userPassword,
                           String userPhoneNumber) {
        String query = "call updateUser(?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionPool.getConnectionFromPool();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, city);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, userPassword);
                preparedStatement.setString(6, userPhoneNumber);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println(affectedRows + " rows have been affected");
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Extract a User object (either client or agency) from a ResultSet
    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("user_id");
        String username = resultSet.getString("username");
        Date accountCreationDate = resultSet.getDate("account_creation_date");
        String address = resultSet.getString("address");
        String city = resultSet.getString("city");
        String email = resultSet.getString("email");
        String userPassword = resultSet.getString("user_password");
        String userPhoneNumber = resultSet.getString("user_phone_number");
        State profileState = State.valueOf(resultSet.getString("profile_state"));
        UserType userType = UserType.valueOf(resultSet.getString("user_type"));

        return (userType == UserType.CLIENT)
                ? new Client(username, accountCreationDate, address, city, email,
                        userPassword, userPhoneNumber, profileState, userType)
                : new Agency(username, accountCreationDate, address, city, email,
                        userPassword, userPhoneNumber, profileState, userType);
    }
}
