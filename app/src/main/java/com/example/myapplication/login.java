package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.*;

import com.example.myapplication.Extra.Functions;
import com.example.myapplication.Model.Agency;
import com.example.myapplication.Model.Client;
import com.example.myapplication.View.UserViewImp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.view.View.OnClickListener;

import java.sql.Date;


public class login extends AppCompatActivity implements OnClickListener {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clientregister-c1856-default-rtdb.firebaseio.com/");
    private String username , password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView txt =  findViewById(R.id.Register);
        txt.setOnClickListener(this);

        Button login = findViewById(R.id.loginbtn);
        login.setOnClickListener(this);
    }

    private void getFieldsValues() {
        username = Functions.getEditTextValue((EditText)findViewById(R.id.username));
        password = Functions.getEditTextValue((EditText)findViewById(R.id.password));
    }

    private boolean checkFields() {
        UserViewImp userViewImp = new UserViewImp();

        if(!Functions.isValidUsername(username)) {
            userViewImp.OnRegisterError(this, "Invalid Username!");
            return false;
        }  else if (!Functions.isValidPassword(password)) {
            userViewImp.OnRegisterError(this, "Invalid Password!");
            return false;
        }

        return true;
    }

    public void  OpenActivity2(){
        Intent intent = new Intent(this, registerchoice.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
//    public void  OpenActivity3(){
//        Intent intent = new Intent(this, agencymain.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    }

    Client loggedInClient = new Client();

    private void checkClientLogin(DatabaseReference clientsRef) {
        clientsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot clientSnapshot) {
                if (clientSnapshot.exists()) {
                    String clientPassword = clientSnapshot.child("password").getValue(String.class);
                    if (clientPassword != null && clientPassword.equals(password)) {
                        final String firstName = clientSnapshot.child("firstName").getValue(String.class);
                        final String lastName = clientSnapshot.child("lastName").getValue(String.class);
                        final String email = clientSnapshot.child("email").getValue(String.class);
                        final String phoneNumber = clientSnapshot.child("phoneNumber").getValue(String.class);
                        final String address = clientSnapshot.child("address").getValue(String.class);
                        final String city = clientSnapshot.child("city").getValue(String.class);
                        final String cinNumber = clientSnapshot.child("cinNumber").getValue(String.class);
                        final String drivingLicenseExpireDate = clientSnapshot.child("drivingLicenseExpireDate").getValue(String.class);
                        // other fields ...

                        loggedInClient.setUsername(username);
                        loggedInClient.setFirstName(firstName);
                        loggedInClient.setLastName(lastName);
                        loggedInClient.setEmail(email);
                        loggedInClient.setUserPhoneNumber(phoneNumber);
                        loggedInClient.setAddress(address);
                        loggedInClient.setCity(city);
                        loggedInClient.setCin(cinNumber);
                        loggedInClient.setLicenseExpireDate(Date.valueOf(drivingLicenseExpireDate));

                        handleClientLoginSuccess();
                    } else {
                        Toast.makeText(login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    checkAgencyLogin();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "Error Checking Client: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    Agency loggedInAgency = new Agency();

    private void checkAgencyLogin() {
        DatabaseReference agencyRef = databaseReference.child("Agency");
        agencyRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot agencySnapshot) {
                if (agencySnapshot.exists()) {
                    String agencyPassword = agencySnapshot.child("password").getValue(String.class);
                    if (agencyPassword != null && agencyPassword.equals(password)) {
                        final String agencyName = agencySnapshot.child("agencyName").getValue(String.class);
                        final String email = agencySnapshot.child("email").getValue(String.class);
                        final String managerFullName = agencySnapshot.child("managerFullName").getValue(String.class);
                        final String city = agencySnapshot.child("city").getValue(String.class);
                        final String address = agencySnapshot.child("address").getValue(String.class);
                        final String phoneNumber = agencySnapshot.child("phoneNumber").getValue(String.class);
                        final String patentNumber = agencySnapshot.child("patentNumber").getValue(String.class);

                        loggedInAgency.setAgencyName(agencyName);
                        loggedInAgency.setUsername(username);
                        loggedInAgency.setEmail(email);
                        loggedInAgency.setManagerFullName(managerFullName);
                        loggedInAgency.setCity(city);
                        loggedInAgency.setAddress(address);
                        loggedInAgency.setUserPhoneNumber(phoneNumber);
                        loggedInAgency.setPatentNumber(Integer.parseInt(patentNumber));

                        handleAgencyLoginSuccess();

                    } else {
                        Toast.makeText(login.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(login.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "Error Checking Agency: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleClientLoginSuccess() {
        Toast.makeText(login.this, "Client Logged In Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(login.this, clientmain.class);
        intent.putExtra("loggedInClient", loggedInClient);
        startActivity(intent);
        finish();
    }

    private void handleAgencyLoginSuccess() {
        Toast.makeText(login.this, "Agency Logged In Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(login.this, agencymain.class);
        intent.putExtra("loggedInAgency", loggedInAgency);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int srcId = view.getId();

        if(srcId == R.id.Register) {
            OpenActivity2();
        }

        if(srcId == R.id.loginbtn) {
            getFieldsValues();

            if (checkFields()) {
                DatabaseReference clientsRef = databaseReference.child("Client");
                checkClientLogin(clientsRef);
                checkAgencyLogin();
            }
        }
    }
}