package com.example.myapplication.Controller;

import java.sql.Date;
import com.example.myapplication.DAO.UserDao;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Model.User;

public class UserController {
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public void registerUser(String username, Date accountCreationDate, String address, String city,
                             String email, String userPassword, String userPhoneNumber, UserType userType) {
        userDao.insertUser(username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, userType);
    }

    public void updateUserProfile(String username, String address, String city, String email,
                                  String userPassword, String userPhoneNumber) {
        userDao.updateUser(username, address, city, email, userPassword, userPhoneNumber);
    }
}
