package com.example.myapplication.DAO;

import java.sql.Date;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Model.User;

public interface UserDao {
    User getUserByUsername(String username);
    void insertUser(String username, Date accountCreationDate, String address, String city,
                    String email, String userPassword, String userPhoneNumber, UserType userType);
    
    void updateUser(String username, String address, String city, String email,
                    String userPassword, String userPhoneNumber);
}
