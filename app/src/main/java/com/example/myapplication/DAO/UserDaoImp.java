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
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public void insertUser(String username, Date accountCreationDate, String address, String city, String email,
                           String userPassword, String userPhoneNumber, UserType userType) {

    }

    @Override
    public void updateUser(String username, String address, String city, String email, String userPassword, String userPhoneNumber) {

    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }
}
