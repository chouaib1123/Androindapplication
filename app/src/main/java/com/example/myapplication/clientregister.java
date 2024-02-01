package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.view.View.OnClickListener;

public class clientregister extends AppCompatActivity implements OnClickListener {
    TextView selectedTextView;

    //----------------------------
    private String username, email, password, vPassword, phoneNumber, city, address, firstName, lastName, birthDate;
    private String cinNumber, drivingLicenceCategory, drivingLicenseExpireDate, drivingLicenseObtainDate;

    // images afterwards**

    //----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientregister);

        TextView mDisplayDate = findViewById(R.id.birthdate);
        TextView mDisplayDate2 = findViewById(R.id.DL_ed);
        TextView mDisplayDate3 = findViewById(R.id.DL_od);
        TextView recto = findViewById(R.id.Cinreco);
        TextView verso = findViewById(R.id.Cinverso);
        TextView dlrecto = findViewById(R.id.dlverso);
        TextView dlverso = findViewById(R.id.dlreco);
        setOnClickListenerForTextView(recto);
        setOnClickListenerForTextView(verso);
        setOnClickListenerForTextView(dlrecto);
        setOnClickListenerForTextView(dlverso);

        setDatePickerDialog(mDisplayDate);
        setDatePickerDialog(mDisplayDate2);
        setDatePickerDialog(mDisplayDate3);

        //----------------------------


        //----------------------------

    }

    private void getFieldsValues() {
        username = ((EditText)findViewById(R.id.username)).getText().toString().trim();
        email = ((EditText)findViewById(R.id.gmail)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        vPassword = ((EditText)findViewById(R.id.vpassword)).getText().toString().trim();
        phoneNumber = ((EditText)findViewById(R.id.phone)).getText().toString().trim();
        city = ((EditText)findViewById(R.id.ville)).getText().toString().trim();
        address = ((EditText)findViewById(R.id.adresse)).getText().toString().trim();
        firstName = ((EditText)findViewById(R.id.firstname)).getText().toString().trim();
        lastName = ((EditText)findViewById(R.id.lastname)).getText().toString().trim();
        birthDate = ((TextView)findViewById(R.id.birthdate)).getText().toString().trim();
        cinNumber = ((EditText)findViewById(R.id.Cinnumber)).getText().toString().trim();
        drivingLicenceCategory = ((EditText)findViewById(R.id.categorieLicence)).getText().toString().trim();
        drivingLicenseObtainDate = ((TextView)findViewById(R.id.DL_od)).getText().toString().trim();
        drivingLicenseExpireDate = ((TextView)findViewById(R.id.DL_ed)).getText().toString().trim();

        // images
    }

    //----------------------------
    @Override
    public void onClick(View view) {

    }

    //----------------------------

    private Map<Integer, TextView> clickedTextViewMap = new HashMap<>();

    private void setOnClickListenerForTextView(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTextView = textView;
                startImagePickerForTextView();
            }
        });
    }

    private void startImagePickerForTextView() {
        ImagePicker.with(clientregister.this)
                .crop()                     // Crop image (Optional)
                .compress(1024)             // Final image size will be less than 1 MB (Optional)
                .maxResultSize(1080, 1080)  // Final image resolution will be less than 1080 x 1080 (Optional)
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
                        selectedTextView.setBackground(drawable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
    private void setDatePickerDialog(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        clientregister.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                                String date = month + "/" + day + "/" + year;
                                textView.setText(date);
                            }
                        },
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }
}
