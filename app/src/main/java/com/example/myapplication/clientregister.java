package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controller.IRegisterClientController;
import com.example.myapplication.Controller.RegisterClientController;
import com.example.myapplication.Model.Extra.LicenseCategory;
import com.example.myapplication.View.IRegisterView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class clientregister extends AppCompatActivity implements IRegisterView {

    EditText email, password, vpassword, phoneNumber, licenseCategory;
    TextView licenseIssueDate, licenseExpireDate;

    Button registerClient;

    TextView selectedTextView;
    IRegisterClientController registerClientController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientregister);
        registerClientController = new RegisterClientController(this);

        email  = (EditText) findViewById(R.id.gmail);
        password = (EditText) findViewById(R.id.password);
        vpassword = (EditText) findViewById(R.id.vpassword);
        phoneNumber = (EditText) findViewById(R.id.phone);
        licenseCategory = (EditText) findViewById(R.id.categorieLicence);
        licenseExpireDate = (TextView)findViewById(R.id.DL_ed);
        licenseIssueDate = (TextView)findViewById(R.id.DL_od);
        registerClient = (Button) findViewById(R.id.register);

        registerClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerClientController.onClientRegister(
                        email.getText().toString().trim(),
                        password.getText().toString().trim(),
                        vpassword.getText().toString().trim(),
                        phoneNumber.getText().toString().trim(),
                        licenseCategory.getText().toString().trim(),
                        licenseExpireDate.getText().toString().trim(),
                        licenseIssueDate.getText().toString().trim()

                );
            }
        });




        TextView mDisplayDate = findViewById(R.id.birthdate);

        TextView recto = findViewById(R.id.Cinreco);
        TextView verso = findViewById(R.id.Cinverso);
        TextView dlrecto = findViewById(R.id.dlverso);
        TextView dlverso = findViewById(R.id.dlreco);
        setOnClickListenerForTextView(recto);
        setOnClickListenerForTextView(verso);
        setOnClickListenerForTextView(dlrecto);
        setOnClickListenerForTextView(dlverso);



        setDatePickerDialog(mDisplayDate);
        setDatePickerDialog(licenseExpireDate);
        setDatePickerDialog(licenseIssueDate);


    }

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

                dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    @Override
    public void OnRegisterSuccess(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRegisterError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}