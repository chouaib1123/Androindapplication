package com.example.myapplication.Extra;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public interface Functions {
    static boolean isValidUsername(String username) {
        return !username.isEmpty() && username.length() >= 4  && username.matches("[a-zA-Z0-9\\s'-]+");
    }

    static boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static boolean isValidPassword(String password) {
        return !password.isEmpty() && password.length() >= 6;
    }

    static boolean isValidVPassword(String password, String vPassword) {
        return !vPassword.isEmpty() && password.equals(vPassword);
    }

    static boolean isValidPhoneNumber(String phoneNumber) {
        boolean startsWith0AndIs10 = phoneNumber.length() == 10 && phoneNumber.startsWith("0");
        boolean startsWithPlusAndIs13 = phoneNumber.length() == 13 && phoneNumber.startsWith("+");
        return !phoneNumber.isEmpty() && (startsWith0AndIs10 || startsWithPlusAndIs13);
    }

    static boolean isValidBirthDate(String birthDate) {
        // Assuming birthDate is in "yyyy-MM-dd" format
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            java.util.Date date = sdf.parse(birthDate);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -18);  // Minimum age required, you can adjust this
            return date != null && date.before(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;  // Invalid date format
        }
    }

    static boolean isValidDrivingLicenseCategory(String category) {
        if(!category.isEmpty()) {
            try {
                LicenseCategory.valueOf(category);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    static boolean isValidDrivingLicenseObtainDate(String obtainDate) {
        // Check if the obtain date is not empty
        if (obtainDate.isEmpty()) {
            return false;
        }

        // Parse obtain date and current date
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            java.util.Date currentDate = new java.util.Date();
            java.util.Date parsedObtainDate = sdf.parse(obtainDate);

            // Check if obtain date is at most today
            if(parsedObtainDate == null) return false;
            return !parsedObtainDate.after(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean isValidDrivingLicenseExpireDate(String expireDate) {
        // Check if the expire date is not empty
        if (expireDate.isEmpty()) {
            return false;
        }

        // Parse expire date and current date
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            java.util.Date currentDate = new java.util.Date();
            java.util.Date parsedExpireDate = sdf.parse(expireDate);

            // Check if expire date is at least today
            if(parsedExpireDate == null) return false;
            return !parsedExpireDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean isValidFirstName(String firstName) {
        return !firstName.isEmpty() && firstName.matches("[a-zA-Z\\s'-]+") && firstName.length() >= 2;
    }

    static boolean isValidLastName(String lastName) {
        return !lastName.isEmpty() && lastName.matches("[a-zA-Z\\s'-]+") && lastName.length() >= 2;
    }

    static boolean isValidCinNumber(String cinNumber) {
        return !cinNumber.isEmpty() && cinNumber.matches("[a-zA-Z0-9]+");
    }

    static boolean isValidFullName(String managerFullName) {
        return !managerFullName.isEmpty() && managerFullName.matches("[a-zA-Z\\s'-]+") && managerFullName.length() >= 2;
    }

    static boolean isValidAgencyName(String agencyName) {
        return !agencyName.isEmpty() && agencyName.matches("[a-zA-Z0-9\\s'-]+") && agencyName.length() >= 2;
    }

    static boolean isValidCity(String city) {
        return !city.isEmpty() && city.matches("[a-zA-Z\\s'-]+") && city.length() >= 2;
    }

    static boolean isValidFullAddress(String agencyFullAddress) {
        return !agencyFullAddress.isEmpty();
    }

    static boolean isValidPatentNumber(String patentNumber) {
        return !patentNumber.isEmpty() && patentNumber.matches("\\d{15}");
    }

    static String getEditTextValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    static String getEditTextValue(TextView textView) {
        return textView.getText().toString().trim();
    }
}
