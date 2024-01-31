package com.example.myapplication.Controller;

import com.example.myapplication.Model.Client;
import com.example.myapplication.Model.Extra.LicenseCategory;
import com.example.myapplication.View.IRegisterView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RegisterClientController implements IRegisterClientController {
    IRegisterView registerView;

    public RegisterClientController(IRegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void onClientRegister(String userEmail, String password, String VPassword, String userPhoneNumber, String licenseCategoryStr, String licenseExpireDateStr, String licenseObtainDateStr) {

        Date licenseExpireDate = parseDate(licenseExpireDateStr);
        Date licenseObtainDate = parseDate(licenseObtainDateStr);
        LicenseCategory licenseCategory = parseLicenseCategory(licenseCategoryStr);

        Client client = new Client(userEmail, password, VPassword, userPhoneNumber, licenseCategory, licenseExpireDate, licenseObtainDate);
        int clientRegisterCode = client.isValid();

        if (clientRegisterCode == 0) {
            registerView.OnRegisterError("Please enter your information");
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
        } else if (clientRegisterCode == 6) {
            registerView.OnRegisterError("invalid License Category");

        } else if (clientRegisterCode == 7) {
            registerView.OnRegisterError("license Obtain Date should be before license Expire Date");
        } else if (clientRegisterCode == 8) {
            registerView.OnRegisterError("license Expire Date should be greater than today");

        } else
            registerView.OnRegisterSuccess("Register Success");
    }

    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return new Date(dateFormat.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }
    }

    private LicenseCategory parseLicenseCategory(String categoryStr) {
        try {
            return LicenseCategory.valueOf(categoryStr);
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return null;
        }

    }

}
