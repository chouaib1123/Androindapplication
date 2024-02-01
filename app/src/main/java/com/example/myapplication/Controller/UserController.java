package com.example.myapplication.Controller;

import java.sql.Date;
import com.example.myapplication.DAO.UserDao;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Model.User;

public class UserController {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public void registerUser(String username, Date accountCreationDate, String address, String city,
                             String email, String userPassword, String userPhoneNumber, UserType userType) {
        userDao.insertUser(username, accountCreationDate, address, city, email, userPassword, userPhoneNumber, userType);
    }

    public void updateUserProfile(int userId, String address, String city, String email,
                                  String userPassword, String userPhoneNumber) {
        userDao.updateUser(userId, address, city, email, userPassword, userPhoneNumber);
    }
}
