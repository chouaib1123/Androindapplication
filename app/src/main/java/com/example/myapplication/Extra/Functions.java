package com.example.myapplication.Extra;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Functions {
    public static String formatToMySQLDateFormat(Date javaSqlDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(javaSqlDate);
    }
}
