package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
import android.util.Pair;
import android.view.View;
import android.widget.*;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class clientregister extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://clientregister-c1856-default-rtdb.firebaseio.com/");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();



    EditText username, email, password, vPassword, phoneNumber, city, fullAddress,
            firstName, lastName, cinNumber, licenseCategory;

    TextView birthDate, cinRecto, cinVerso, licenseExpireDate, licenseIssueDate,
            drivingLicenseRecto, drivingLicenseVerso;
    Button registerClient;
    Uri uri;
    TextView selectedTextView;
    Map<TextView, Pair<Uri, BitmapDrawable>> textViewImages = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientregister);

        username = findViewById(R.id.username);
        email = findViewById(R.id.gmail);
        password = findViewById(R.id.password);
        vPassword = findViewById(R.id.vpassword);
        phoneNumber = findViewById(R.id.phone);
        city = findViewById(R.id.ville);
        fullAddress = findViewById(R.id.adresse);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        birthDate = findViewById(R.id.birthdate);
        cinNumber = findViewById(R.id.Cinnumber);
        cinRecto = findViewById(R.id.Cinreco);
        cinVerso = findViewById(R.id.Cinverso);
        licenseCategory = findViewById(R.id.categorieLicence);
        licenseExpireDate = findViewById(R.id.DL_ed);
        licenseIssueDate = findViewById(R.id.DL_od);
        drivingLicenseRecto = findViewById(R.id.dlreco);
        drivingLicenseVerso = findViewById(R.id.dlverso);
        registerClient = findViewById(R.id.registr);

        setOnClickListenerForTextView(cinRecto);
        setOnClickListenerForTextView(cinVerso);
        setOnClickListenerForTextView(drivingLicenseRecto);
        setOnClickListenerForTextView(drivingLicenseVerso);




        setDatePickerDialog(birthDate);
        setDatePickerDialog(licenseExpireDate);
        setDatePickerDialog(licenseIssueDate);

        registerClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImagesToFirebaseStorage();
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
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(getTrimmedText(username))){
                                Toast.makeText(clientregister.this, "username already registered", Toast.LENGTH_SHORT).show();
                            }else {
                                databaseReference.child("Client").child(getTrimmedText(username)).child("Email").setValue(getTrimmedText(email));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("Password").setValue(getTrimmedText(password));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("City").setValue(getTrimmedText(city));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("Full Address").setValue(getTrimmedText(fullAddress));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("First Name").setValue(getTrimmedText(firstName));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("Last Name").setValue(getTrimmedText(lastName));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("Birth Date").setValue(getTrimmedText(birthDate));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("Cin Number").setValue(getTrimmedText(cinNumber));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("License Driving Category").setValue(getTrimmedText(licenseCategory));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("License Driving Category Obtain Date").setValue(getTrimmedText(licenseIssueDate));
                                databaseReference.child("Client").child(getTrimmedText(username)).child("License Driving Category Expire Date").setValue(getTrimmedText(licenseExpireDate));

                                Toast.makeText(clientregister.this, "registration successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                }
        }
        });

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
        ImagePicker.with(clientregister.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
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
                    textViewImages.put(selectedTextView, new Pair<>(uri, drawable));
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




    public String getTrimmedText(EditText editText) {
        return editText.getText().toString().trim();
    }
    public String getTrimmedText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public String getVPassword()
    {
        return getTrimmedText(vPassword);
    }


    private void uploadImagesToFirebaseStorage() {

        String cinRectoFilename = "cin_recto.png";
        String cinVersoFilename = "cin_verso.png";
        String drivingLicenseRectoFilename = "driving_license_recto.png";
        String drivingLicenseVersoFilename = "driving_license_verso.png";

        // Iterate through the textViewImages map
        for (Map.Entry<TextView, Pair<Uri, BitmapDrawable>> entry : textViewImages.entrySet()) {
            final TextView textView = entry.getKey();
            Pair<Uri, BitmapDrawable> imageData = entry.getValue();

            final Uri uri = imageData.first;
            final BitmapDrawable drawable = imageData.second;

            try {
                // Convert the BitmapDrawable to a Bitmap
                Bitmap bitmap = drawable.getBitmap();

                // Convert the Bitmap to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] byteArray = stream.toByteArray();
                String filename = getFilenameForTextView(textView);

                // Get a reference to the Firebase Storage location
                final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + getTrimmedText(username) + '/' + filename);
                //final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString() + ".png");
                // Upload the byte array to Firebase Storage
                storageRef.putBytes(byteArray)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d("Firebase", "Image uploaded to Firebase Storage for TextView: " + textView.getId());
                                // You can add any further processing here, such as getting download URLs
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private void storeImageUrlInDatabase(String key, String imageUrl) {
//        // Store the imageUrl in your database (Firebase Realtime Database)
//        DatabaseReference userRef = databaseReference.child(getTrimmedText(username));
//        userRef.child(key).setValue(imageUrl);
//    }

    private String getFilenameForTextView(TextView textView) {
        // Map TextView types to filenames
        if (textView == cinRecto) {
            return "cin_recto.png";
        } else if (textView == cinVerso) {
            return "cin_verso.png";
        } else if (textView == drivingLicenseRecto) {
            return "driving_license_recto.png";
        } else if (textView == drivingLicenseVerso) {
            return "driving_license_verso.png";
        }

        // Default filename
        return "default.png";
    }
}
