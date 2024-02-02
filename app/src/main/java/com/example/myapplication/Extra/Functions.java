package com.example.myapplication.Extra;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Functions {
    public static String formatDate(String date) {
        // Define the input and output date format patterns
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the input date string
        LocalDate localDate = LocalDate.parse(date, inputFormatter);

        // Format the date to match MySQL format

        // Return the formatted date
        return localDate.format(outputFormatter);
    }
}
