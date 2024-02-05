package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Extra.FieldsValidation;
import com.example.myapplication.View.UserViewImp;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.view.View.OnClickListener;

public class agencyregister extends AppCompatActivity implements OnClickListener {
    TextView selectedTextView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clientregister-c1856-default-rtdb.firebaseio.com/");
    private String agencyName, email, password, vPassword, managerFullName, phoneNumber, City, agencyFullAddress, patentNumber;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencyregister);

        Button registerAgency = findViewById(R.id.registr);
        registerAgency.setOnClickListener(this);
    }

    private void getFieldsValues() {
        agencyName = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.username));
        email = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.gmail));
        password = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.password));
        vPassword = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.vpassword));
        phoneNumber = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.phone));
        managerFullName = ((EditText)findViewById(R.id.Managername)).getText().toString();
        City = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.ville));
        agencyFullAddress = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.adresse));
        patentNumber = FieldsValidation.getEditTextValue((EditText)findViewById(R.id.patent_number));
    }

    private boolean checkFields() {
        UserViewImp userViewImp = new UserViewImp();

        if(!FieldsValidation.isValidUsername(agencyName)) {
            userViewImp.OnRegisterError(this, "Invalid Username!");
            return false;
        } else if (!FieldsValidation.isValidEmail(email)) {
            userViewImp.OnRegisterError(this, "Invalid Email Address!");
            return false;
        } else if (!FieldsValidation.isValidPassword(password)) {
            userViewImp.OnRegisterError(this, "Invalid Password. Must be at least 6 characters!");
            return false;
        } else if (!FieldsValidation.isValidVPassword(password, vPassword)) {
            userViewImp.OnRegisterError(this, "Passwords Don't Match up!");
            return false;
        } else if (!FieldsValidation.isValidPhoneNumber(phoneNumber)) {
            userViewImp.OnRegisterError(this, "Invalid Phone Number!");
            return false;
        }  else if (!FieldsValidation.isValidFullName(managerFullName)) {
            userViewImp.OnRegisterError(this, "Invalid Manager Name!");
            return false;
        } else if (!FieldsValidation.isValidCity(City)) {
            userViewImp.OnRegisterError(this, "Invalid City!");
            return false;
        } else if (!FieldsValidation.isValidFullAddress(agencyFullAddress)) {
            userViewImp.OnRegisterError(this, "Invalid Address!");
            return false;
        } else if (!FieldsValidation.isValidPatentNumber(patentNumber)) {
            userViewImp.OnRegisterError(this, "Invalid Patent Number!");
            return false;
        }

        return true;
    }

//    private void setOnClickListenerForTextView(final TextView textView) {
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                selectedTextView = textView;
//                startImagePickerForTextView();
//            }
//        });
//    }
//
//    private void startImagePickerForTextView() {
//        ImagePicker.with(agencyregister.this)
//                .crop()                     // Crop image (Optional)
//                .compress(1024)             // Final image size will be less than 1 MB (Optional)
//                .maxResultSize(1080, 1080)  // Final image resolution will be less than 1080 x 1080 (Optional)
//                .start();
//    }

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

    @Override
    public void onClick(View view) {
        int srcId = view.getId();

        if(srcId == R.id.registr) {
            getFieldsValues();

            if(checkFields()) {
                databaseReference.child("Agency").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(agencyName.replaceAll("[\\[\\].#$/]", "_"))){
                            Toast.makeText(agencyregister.this, "Agency name already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference identifier = databaseReference.child("Agency").child(agencyName);

                            identifier.child("Email").setValue(email);
                            identifier.child("Password").setValue(password);
                            identifier.child("Agency manager Full Name").setValue(managerFullName);
                            identifier.child("City").setValue(City);
                            identifier.child("Agency Address").setValue(agencyFullAddress);
                            identifier.child("Phone Number").setValue(phoneNumber);
                            identifier.child("Patent Number").setValue(patentNumber);

                            Toast.makeText(agencyregister.this, "registration successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        }
    }
}