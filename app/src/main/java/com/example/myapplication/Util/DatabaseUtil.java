package com.example.myapplication.Util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "https://clientregister-c1856-default-rtdb.firebaseio.com/";

    public static DatabaseReference connect() {
        try {
            return FirebaseDatabase.getInstance(URL).getReference();
        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return null;
    }
}
