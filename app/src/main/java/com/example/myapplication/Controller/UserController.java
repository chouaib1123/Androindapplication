package com.example.myapplication.Controller;

import java.sql.Date;
import com.example.myapplication.DAO.UserDao;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Model.Client;
import com.example.myapplication.Model.User;
import com.example.myapplication.View.IRegisterView;

public class UserController {
    IRegisterView registerView;

    public void RegisterController(IRegisterView registerView) {
        this.registerView = registerView;
    }

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

    public void verifyUserInfo(String username, String userEmail, String password, String VPassword, String userPhoneNumber) {
        Client client = new Client(username , userEmail , password , userPhoneNumber);
        int clientRegisterCode = client.isValid();

        if (clientRegisterCode == 0) {
            registerView.OnRegisterError("Please enter your username");
        } else if (clientRegisterCode == 1) {
            registerView.OnRegisterError("Invalid email address");
        } else if (clientRegisterCode == 2) {
            registerView.OnRegisterError("please enter your password");
        } else if (clientRegisterCode == 3) {
            registerView.OnRegisterError("the length of password should be greater than 8 characters");
        } else if (clientRegisterCode == 4) {
            registerView.OnRegisterError("the password does not match up with verify password");
        } else if (clientRegisterCode == 5) {
            registerView.OnRegisterError("Invalid Phone number");
        } else
            registerView.OnRegisterSuccess("Register Success");
    }
}
