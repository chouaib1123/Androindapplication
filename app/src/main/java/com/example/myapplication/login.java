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

    Client client = new Client();

    private void checkClientLogin(DatabaseReference clientsRef) {
        clientsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot clientSnapshot) {
                if (clientSnapshot.exists()) {
                    String clientPassword = clientSnapshot.child("password").getValue(String.class);
                    if (clientPassword != null && clientPassword.equals(password)) {
                        // set client fields
                        // ...
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

    Agency agency = new Agency();

    private void checkAgencyLogin() {
        DatabaseReference agencyRef = databaseReference.child("Agency");
        agencyRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot agencySnapshot) {
                if (agencySnapshot.exists()) {
                    String agencyPassword = agencySnapshot.child("Password").getValue(String.class);
                    if (agencyPassword != null && agencyPassword.equals(password)) {
                        final String agencyName = agencySnapshot.child("Agency Name").getValue(String.class);
                        agency.setAgencyName(agencyName);
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
        startActivity(new Intent(login.this, clientmain.class));
        finish();
    }

    private void handleAgencyLoginSuccess() {
        Toast.makeText(login.this, "Agency Logged In Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(login.this, agencymain.class);
        intent.putExtra("Agency Name", agency.getAgencyName());
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