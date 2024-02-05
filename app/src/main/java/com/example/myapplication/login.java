package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clientregister-c1856-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    EditText username , password , agencyName;
    Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = findViewById(R.id.username);
        agencyName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginbtn);
        TextView txt =  findViewById(R.id.Register);
        txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                OpenActivity2();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = getTrimmedText(username);
                String enteredPassword = getTrimmedText(password);

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    Toast.makeText(login.this, "Please enter your username and password", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference clientsRef = databaseReference.child("Client");
                    checkClientLogin(clientsRef, enteredUsername, enteredPassword);
                    checkAgencyLogin(enteredUsername , enteredPassword);
                }
            }
        });
    }
    public void  OpenActivity2(){
        Intent intent = new Intent(this, registerchoice.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void  OpenActivity3(){


        Intent intent = new Intent(this, agencymain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public String getTrimmedText(EditText editText) {
        return editText.getText().toString().trim();
    }

    private void checkClientLogin(DatabaseReference clientsRef, String username, String password) {
        clientsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot clientSnapshot) {
                if (clientSnapshot.exists()) {
                    String clientPassword = clientSnapshot.child("password").getValue(String.class);
                    if (clientPassword != null && clientPassword.equals(password)) {
                        handleClientLoginSuccess();
                    } else {
                        Toast.makeText(login.this, "Wrong Password for Client", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    checkAgencyLogin(username, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "Error checking Clients: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAgencyLogin(String username, String password) {
        DatabaseReference agencyRef = databaseReference.child("Agency");
        agencyRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot agencySnapshot) {
                if (agencySnapshot.exists()) {
                    String agencyPassword = agencySnapshot.child("Password").getValue(String.class);
                    if (agencyPassword != null && agencyPassword.equals(password)) {
                        handleAgencyLoginSuccess();

                    } else {
                        Toast.makeText(login.this, "Wrong Password for Agency", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(login.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "Error checking Agency: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleClientLoginSuccess() {
        Toast.makeText(login.this, "Client Login successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(login.this, clientmain.class));
        finish();
    }

    private void handleAgencyLoginSuccess() {
        Toast.makeText(login.this, "Agency Login successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(login.this, agencymain.class);
        intent.putExtra("Agency Name", getTrimmedText(agencyName));
        startActivity(intent);
        finish();
    }
}