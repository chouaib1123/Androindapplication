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
import android.widget.TextView;

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

public class agencyregister extends AppCompatActivity {
<<<<<<< Updated upstream
    TextView selectedTextView;
=======

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clientregister-c1856-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    EditText agencyName , email, password, vPassword , managerFullName , phoneNumber , City , agencyFullAddress ,patentNumber ;

    Button registerAgency;




    TextView selectedTextView;

    @SuppressLint("MissingInflatedId")
>>>>>>> Stashed changes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencyregister);
<<<<<<< Updated upstream
        TextView cnss = findViewById(R.id.cnssreg);
        setOnClickListenerForTextView(cnss);
=======

        agencyName = findViewById(R.id.username);
        email  = findViewById(R.id.gmail);
        password = findViewById(R.id.password);
        vPassword = findViewById(R.id.vpassword);
        managerFullName = findViewById(R.id.fullname);
        City = findViewById(R.id.ville);
        agencyFullAddress = findViewById(R.id.adresse);
        patentNumber = findViewById(R.id.patent_number);
        phoneNumber = findViewById(R.id.phone);
        registerAgency = findViewById(R.id.registr);

        registerAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                if(TextUtils.isEmpty(getTrimmedText(username)) ||
//                        getTrimmedText(email).isEmpty() ||
//                        getTrimmedText(password).isEmpty()
//                ){
//                    Toast.makeText(clientregister.this, "please fill up the mandatory fields ", Toast.LENGTH_SHORT).show();
//                } else if (!(getTrimmedText(password).equals(getTrimmedText(vPassword)))) {
//                    Toast.makeText(clientregister.this, "the password does not match up with verify password", Toast.LENGTH_SHORT).show();
//                } else if (!(getTrimmedText(phoneNumber).length() == 13 && getTrimmedText(phoneNumber).startsWith("+"))) {
//                    Toast.makeText(clientregister.this, "Invalid value of the Phone number", Toast.LENGTH_SHORT).show();
//                } else if (!(Patterns.EMAIL_ADDRESS.matcher(getTrimmedText(email)).matches())) {
//                    Toast.makeText(clientregister.this, "Invalid email", Toast.LENGTH_SHORT).show();
//
//                } else if (getTrimmedText(password).length() <= 8 ) {
//                    Toast.makeText(clientregister.this, "The password should greater than 8 characters", Toast.LENGTH_SHORT).show();
//
//                } else {
                databaseReference.child("Agency").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(getTrimmedText(agencyName))){
                            Toast.makeText(agencyregister.this, "Agency name already registered", Toast.LENGTH_SHORT).show();
                        }else {
                            databaseReference.child("Agency").child(getTrimmedText(agencyName)).child("Email").setValue(getTrimmedText(email));
                            databaseReference.child("Agency").child(getTrimmedText(agencyName)).child("Password").setValue(getTrimmedText(password));
                            databaseReference.child("Agency").child(getTrimmedText(agencyName)).child("Agency manager Full Name").setValue(getTrimmedText(managerFullName));
                            databaseReference.child("Agency").child(getTrimmedText(agencyName)).child("City").setValue(getTrimmedText(City));
                            databaseReference.child("Agency").child(getTrimmedText(agencyName)).child("Agency Address").setValue(getTrimmedText(agencyFullAddress));
                            databaseReference.child("Agency").child(getTrimmedText(agencyName)).child("Phone Number").setValue(getTrimmedText(phoneNumber));
                            databaseReference.child("Agency").child(getTrimmedText(agencyName)).child("Patent Number").setValue(getTrimmedText(patentNumber));

                            Toast.makeText(agencyregister.this, "registration successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                }}

            }
        });
>>>>>>> Stashed changes


    }


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
        ImagePicker.with(agencyregister.this)
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
<<<<<<< Updated upstream
=======

    private String getTrimmedText(EditText editText) {
        return editText.getText().toString().trim();
    }
    public String getVPassword()
    {
        return getTrimmedText(vPassword);
    }
>>>>>>> Stashed changes
}