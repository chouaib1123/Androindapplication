package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Controller.ClientController;
import com.example.myapplication.Controller.UserController;
import com.example.myapplication.DAO.ClientDaoImp;
import com.example.myapplication.DAO.UserDaoImp;
import com.example.myapplication.Extra.Functions;
import com.example.myapplication.Extra.LicenseCategory;
import com.example.myapplication.Extra.State;
import com.example.myapplication.Extra.UserType;
import com.example.myapplication.Model.Client;
import com.example.myapplication.Model.User;
import com.example.myapplication.View.UserView;
import com.example.myapplication.View.UserViewImp;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
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
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.view.View.OnClickListener;

public class clientregister extends AppCompatActivity implements OnClickListener {
    TextView selectedTextView;

    //----------------------------
    private String username, email, password, vPassword, phoneNumber, city, address, firstName, lastName, birthDate;
    private String cinNumber, drivingLicenceCategory, drivingLicenseExpireDate, drivingLicenseObtainDate;
    private TextView cinRecto, cinVerso, drivingLicenseRecto, drivingLicenseVerso;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://clientregister-c1856-default-rtdb.firebaseio.com/").getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    Map<TextView, Pair<Uri, BitmapDrawable>> textViewImages = new HashMap<>();

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
        Button registerClientButton = (Button) findViewById(R.id.registr);

        registerClientButton.setOnClickListener(this);
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
        drivingLicenceCategory = ((EditText)findViewById(R.id.categorieLicence)).getText().toString().trim().toUpperCase();
        drivingLicenseObtainDate = ((TextView)findViewById(R.id.DL_od)).getText().toString().trim();
        drivingLicenseExpireDate = ((TextView)findViewById(R.id.DL_ed)).getText().toString().trim();

        cinRecto = (TextView) findViewById(R.id.Cinreco);
        cinVerso = (TextView) findViewById(R.id.Cinverso);
        drivingLicenseRecto = (TextView) findViewById(R.id.dlreco);
        drivingLicenseVerso = (TextView) findViewById(R.id.dlverso);
    }

    private boolean checkFields() {
        UserViewImp userViewImp = new UserViewImp();

        if(!isValidUsername(username)) {
            userViewImp.OnRegisterError(this, "Invalid Username!");
            return false;
        } else if (!isValidEmail(email)) {
            userViewImp.OnRegisterError(this, "Invalid Email Address!");
            return false;
        } else if (!isValidPassword(password)) {
            userViewImp.OnRegisterError(this, "Invalid Password. Must be at least 6 characters!");
            return false;
        } else if (!isValidVPassword(password, vPassword)) {
            userViewImp.OnRegisterError(this, "Passwords Don't Match up!");
            return false;
        } else if (!isValidPhoneNumber(phoneNumber)) {
            userViewImp.OnRegisterError(this, "Invalid Phone Number!");
            return false;
        }  else if (!isValidFirstName(firstName)) {
            userViewImp.OnRegisterError(this, "Invalid First Name!");
            return false;
        } else if (!isValidLastName(lastName)) {
            userViewImp.OnRegisterError(this, "Invalid Last Name!");
            return false;
        } else if (!isValidBirthDate(birthDate)) {
            userViewImp.OnRegisterError(this, "Invalid Birth Date!");
            return false;
        } else if (!isValidCinNumber(cinNumber)) {
            userViewImp.OnRegisterError(this, "Invalid CIN!");
            return false;
        } else if (!isValidDrivingLicenseCategory(drivingLicenceCategory)) {
            userViewImp.OnRegisterError(this, "Invalid Driving License Category!");
            return false;
        } else if (!isValidDrivingLicenseObtainDate(drivingLicenseObtainDate)) {
            userViewImp.OnRegisterError(this, "Invalid Driving License Obtain Date!");
            return false;
        } else if (!isValidDrivingLicenseExpireDate(drivingLicenseExpireDate)) {
            userViewImp.OnRegisterError(this, "Invalid Driving License Expire Date!");
            return false;
        }

        return true;
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty() && username.length() >= 4;
    }

    private boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return !password.isEmpty() && password.length() >= 6;
    }

    private boolean isValidVPassword(String password, String vPassword) {
        return !vPassword.isEmpty() && password.equals(vPassword);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        boolean startsWith0AndIs10 = phoneNumber.length() == 10 && phoneNumber.startsWith("0");
        boolean startsWithPlusAndIs13 = phoneNumber.length() == 13 && phoneNumber.startsWith("+");
        return !phoneNumber.isEmpty() && (startsWith0AndIs10 || startsWithPlusAndIs13);
    }

    public static boolean isValidBirthDate(String birthDate) {
        // Assuming birthDate is in "yyyy-MM-dd" format
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            java.util.Date date = sdf.parse(birthDate);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -18);  // Minimum age required, you can adjust this
            return date != null && date.before(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;  // Invalid date format
        }
    }

    private boolean isValidDrivingLicenseCategory(String category) {
        if(!category.isEmpty()) {
            try {
                LicenseCategory.valueOf(category);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    private boolean isValidDrivingLicenseObtainDate(String obtainDate) {
        // Check if the obtain date is not empty
        if (obtainDate.isEmpty()) {
            return false;
        }

        // Parse obtain date and current date
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            java.util.Date currentDate = new java.util.Date();
            java.util.Date parsedObtainDate = sdf.parse(obtainDate);

            // Check if obtain date is at most today
            if(parsedObtainDate == null) return false;
            return !parsedObtainDate.after(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidDrivingLicenseExpireDate(String expireDate) {
        // Check if the expire date is not empty
        if (expireDate.isEmpty()) {
            return false;
        }

        // Parse expire date and current date
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            java.util.Date currentDate = new java.util.Date();
            java.util.Date parsedExpireDate = sdf.parse(expireDate);

            // Check if expire date is at least today
            if(parsedExpireDate == null) return false;
            return !parsedExpireDate.before(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidFirstName(String firstName) {
        return !firstName.isEmpty();
    }

    private boolean isValidLastName(String lastName) {
        return !lastName.isEmpty();
    }

    private boolean isValidCinNumber(String cinNumber) {
        return !cinNumber.isEmpty();
    }

    //----------------------------
    @Override
    public void onClick(View view) {
        int srcId = view.getId();

        if(srcId == R.id.registr) {
            getFieldsValues();

            if(checkFields()) {
//                String accountCreationDate = LocalDate.now().toString();
                String currentDate = new java.sql.Date(System.currentTimeMillis()).toString();

                databaseReference.child("Client").addListenerForSingleValueEvent(new ValueEventListener() {
                    final UserViewImp userViewImp = new UserViewImp();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(username.replaceAll("[\\[\\].#$/]", "_"))) {
                            userViewImp.OnRegisterError(clientregister.this, "Username already exists!");
//                        } else if(snapshot.hasChild(email.replaceAll("[\\[\\].#$/]", "_"))) {             // can't force the uniqueness of a field except the identifier
//                            userViewImp.OnRegisterError(clientregister.this, "Email already exists!");
//                        } else if(snapshot.hasChild(cinNumber.replaceAll("[\\[\\].#$/]", "_"))) {
//                            userViewImp.OnRegisterError(clientregister.this, "CIN already exists!");
                        } else {
                            databaseReference.child("Client").child(username).child("accountCreationDate").setValue(currentDate);
                            databaseReference.child("Client").child(username).child("address").setValue(address);
                            databaseReference.child("Client").child(username).child("city").setValue(city);
                            databaseReference.child("Client").child(username).child("email").setValue(email);
                            databaseReference.child("Client").child(username).child("password").setValue(password);
                            databaseReference.child("Client").child(username).child("phoneNumber").setValue(phoneNumber);
                            databaseReference.child("Client").child(username).child("profileState").setValue(State.PENDING.toString());
                            databaseReference.child("Client").child(username).child("userType").setValue(UserType.CLIENT.toString());
                            databaseReference.child("Client").child(username).child("firstName").setValue(firstName);
                            databaseReference.child("Client").child(username).child("lastName").setValue(lastName);
                            databaseReference.child("Client").child(username).child("birthDate").setValue(birthDate);
                            databaseReference.child("Client").child(username).child("cinNumber").setValue(cinNumber);
                            databaseReference.child("Client").child(username).child("drivingLicenseCategory").setValue(drivingLicenceCategory);
                            databaseReference.child("Client").child(username).child("drivingLicenseObtainDate").setValue(drivingLicenseObtainDate);
                            databaseReference.child("Client").child(username).child("drivingLicenseExpireDate").setValue(drivingLicenseExpireDate);

                            uploadImagesToFirebaseStorage();

                            userViewImp.OnRegisterSuccess(clientregister.this, "Registered successfully");
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
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
                    textViewImages.put(selectedTextView, new Pair<>(uri, drawable));
                    selectedTextView.setBackground(drawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
                final StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + username + '/' + filename);
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

    //----------------------------

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
//                                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);
                                Log.d(TAG, "onDateSet: yyyy-mm-dd: " + year + "-" + month + "-" + day);

//                                String date = month + "/" + day + "/" + year;
                                String date = year + "-" + month + "-" + day;
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
