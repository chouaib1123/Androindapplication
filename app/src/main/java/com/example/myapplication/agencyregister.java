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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Controller.AgencyController;
import com.example.myapplication.DAO.AgencyDaoImp;
import com.example.myapplication.Extra.Functions;
import com.example.myapplication.Util.DatabaseUtil;
import com.example.myapplication.View.UserViewImp;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import android.view.View.OnClickListener;

public class agencyregister extends AppCompatActivity implements OnClickListener {
    TextView selectedTextView;
    private String username, email, password, vPassword, managerFullName, agencyName, phoneNumber, city, agencyFullAddress, patentNumber;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencyregister);

        Button registerAgency = findViewById(R.id.registr);
        registerAgency.setOnClickListener(this);
    }

    private void getFieldsValues() {
        username = Functions.getEditTextValue((EditText)findViewById(R.id.username));
        email = Functions.getEditTextValue((EditText)findViewById(R.id.gmail));
        password = Functions.getEditTextValue((EditText)findViewById(R.id.password));
        vPassword = Functions.getEditTextValue((EditText)findViewById(R.id.vpassword));
        phoneNumber = Functions.getEditTextValue((EditText)findViewById(R.id.phone));
        managerFullName = ((EditText)findViewById(R.id.Managername)).getText().toString();
        agencyName = ((EditText)findViewById(R.id.agencyName)).getText().toString();
        city = Functions.getEditTextValue((EditText)findViewById(R.id.ville));
        agencyFullAddress = Functions.getEditTextValue((EditText)findViewById(R.id.adresse));
        patentNumber = Functions.getEditTextValue((EditText)findViewById(R.id.patent_number));
    }

    private boolean checkFields() {
        UserViewImp userViewImp = new UserViewImp();

        if(!Functions.isValidUsername(username)) {
            userViewImp.OnError(this, "Invalid Username!");
            return false;
        } else if (!Functions.isValidEmail(email)) {
            userViewImp.OnError(this, "Invalid Email Address!");
            return false;
        } else if (!Functions.isValidPassword(password)) {
            userViewImp.OnError(this, "Invalid Password. Must be at least 6 characters!");
            return false;
        } else if (!Functions.isValidVPassword(password, vPassword)) {
            userViewImp.OnError(this, "Passwords Don't Match up!");
            return false;
        } else if (!Functions.isValidPhoneNumber(phoneNumber)) {
            userViewImp.OnError(this, "Invalid Phone Number!");
            return false;
        }  else if (!Functions.isValidFullName(managerFullName)) {
            userViewImp.OnError(this, "Invalid Manager Name!");
            return false;
        } else if (!Functions.isValidAgencyName(agencyName)) {
            userViewImp.OnError(this, "Invalid Agency Name!");
            return false;
        }else if (!Functions.isValidCity(city)) {
            userViewImp.OnError(this, "Invalid City!");
            return false;
        } else if (!Functions.isValidFullAddress(agencyFullAddress)) {
            userViewImp.OnError(this, "Invalid Address!");
            return false;
        } else if (!Functions.isValidPatentNumber(patentNumber)) {
            userViewImp.OnError(this, "Invalid Patent Number!");
            return false;
        }

        return true;
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

    @Override
    public void onClick(View view) {
        int srcId = view.getId();

        if(srcId == R.id.registr) {
            getFieldsValues();

            if(checkFields()) {
                DatabaseUtil.connect().child("Agency").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(username.replaceAll("[\\[\\].#$/]", "_"))){
                            Toast.makeText(agencyregister.this, "Username already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            AgencyController agencyController = new AgencyController();

                            agencyController.registerAgency(username, agencyFullAddress, city, email, password, phoneNumber,
                                    patentNumber, managerFullName, agencyName);

                            Toast.makeText(agencyregister.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        }

        if(srcId == R.id.buttonModify){
            DatabaseUtil.connect().child("Agency").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}