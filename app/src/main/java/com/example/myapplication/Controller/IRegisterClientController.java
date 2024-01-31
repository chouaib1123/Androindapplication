package com.example.myapplication.Controller;

import com.example.myapplication.Model.Extra.LicenseCategory;

import java.sql.Date;

public interface IRegisterClientController {
    void onClientRegister(String userEmail ,
                          String password ,
                          String VPassword ,
                          String userPhoneNumber,
                          String licenseCategory ,
                          String licenseExpireDateStr ,
                          String licenseObtainDateStr );
}
