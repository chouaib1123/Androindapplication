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
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                if(getTrimmedText(username).isEmpty() || getTrimmedText(password).isEmpty()){
                    Toast.makeText(login.this, "Please enter your username and password", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference clientsRef = databaseReference.child("Clients");
                    DatabaseReference agencyRef = databaseReference.child("Agency");

                    clientsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot clientSnapshot) {
                            if (clientSnapshot.hasChild(getTrimmedText(username))) {
                                // Handle client login
                                final String getClientPassword = clientSnapshot.child(getTrimmedText(username)).child("Password").getValue(String.class);

                                if (getClientPassword != null && getClientPassword.equals(getTrimmedText(password))) {
                                    Toast.makeText(login.this, "Client Login successfully", Toast.LENGTH_SHORT).show();
                                    // Start the client activity
                                    //startActivity(new Intent(login.this, ClientMainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(login.this, "Wrong Password for Client", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // If not a client, check agency login
                                agencyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot agencySnapshot) {
                                        if (agencySnapshot.hasChild(getTrimmedText(agencyName))) {
                                            // Handle agency login
                                            final String getAgencyPassword = agencySnapshot.child(getTrimmedText(username)).child("Password").getValue(String.class);

                                            if (getAgencyPassword != null && getAgencyPassword.equals(getTrimmedText(password))) {
                                                Toast.makeText(login.this, "Agency Login successfully", Toast.LENGTH_SHORT).show();
                                                // Start the agency activity
                                                startActivity(new Intent(login.this, agencymain.class));
                                                Intent intent = new Intent(login.this, agencymain.class);
                                                intent.putExtra("Agency Name", getTrimmedText(agencyName)); // Pass the username to AgencMainActivity
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(login.this, "Wrong Password for Agency", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(login.this, "Invalid username", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError agencyError) {
                                        Toast.makeText(login.this, "Error checking Agency: " + agencyError.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError clientError) {
                            Toast.makeText(login.this, "Error checking Clients: " + clientError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
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
}