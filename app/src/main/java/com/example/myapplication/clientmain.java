package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.Model.Client;
import com.example.myapplication.Util.DatabaseUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class clientmain extends AppCompatActivity {
//    private LinearLayout containerLayout;
//    TextView selectedTextView;
    private ScrollView scrollView;
    private TextView clientNameTextView;
    private Client loggedInClient;
    private LinearLayout containerLayout;
    TextView selectedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientmain);

        NavigationView nv = findViewById(R.id.navigation_view2);
        ImageView menu = findViewById(R.id.menu_icon);
        View headerView = nv.getHeaderView(0);
        ImageView  backbtn = headerView.findViewById(R.id.backnav);
        scrollView = findViewById(R.id.scrollview);
        clientNameTextView = headerView.findViewById(R.id.username);

        switchToLayout(R.layout.cars);

        Intent intent = getIntent();
        if(intent != null) loggedInClient = (Client) intent.getSerializableExtra("loggedInClient");

        menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                openMenu();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                closeMenu();
            }
        });

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.profil) {
                    switchToLayout(R.layout.clientprofil);
                } else if (itemId == R.id.cars) {
                    switchToLayout(R.layout.cars);
                } else if (itemId == R.id.pendreq) {
                    switchToLayout(R.layout.clientpendingrequest);
                }
                return false;
            }
        });
    }
    public void openMenu() {
        NavigationView nv = findViewById(R.id.navigation_view2);
        nv.setVisibility(View.VISIBLE);
        // Apply fade animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        nv.startAnimation(fadeIn);
        setLoggedInClientName();
    }

    public void setLoggedInClientName() {
        if(loggedInClient != null) {
            final String clientFirstName = loggedInClient.getFirstName();
            final String clientLastName = loggedInClient.getLastName();
            clientNameTextView.setText(String.format("%s %s", clientFirstName, clientLastName));
        }
    }

    public void closeMenu() {
        final NavigationView nv = findViewById(R.id.navigation_view2);
        // Apply fade animation
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                nv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        nv.startAnimation(fadeOut);
    }

    protected void switchToLayout(int layoutResId) {
        scrollView.removeAllViews();
        View newLayout = LayoutInflater.from(clientmain.this).inflate(layoutResId, scrollView, false);
        scrollView.addView(newLayout);

        if(layoutResId == R.layout.clientprofil) {
            TextView clientFullNameTV = (TextView) findViewById(R.id.clientFullName);
            TextView phoneNumberTV = (TextView) findViewById(R.id.phoneNumber);
            TextView usernameTV = (TextView) findViewById(R.id.clientUsername);
            TextView emailTV = (TextView) findViewById(R.id.email);
            TextView cinNumberTV = (TextView) findViewById(R.id.cinNumber);
            TextView drivingLicenseCategoryTV = (TextView) findViewById(R.id.drivingLicenseCategory);
            TextView drivingLicenseExpireDateTV = (TextView) findViewById(R.id.drivingLicenseExpireDate);
            TextView drivingLicenseObtainDateTV = (TextView) findViewById(R.id.drivingLicenseObtainDate);
            TextView addressTV = (TextView) findViewById(R.id.address);
            TextView cityTV = (TextView) findViewById(R.id.city);

            if(loggedInClient != null) {
                final String email = loggedInClient.getEmail();
                final String username = loggedInClient.getUsername();
                final String clientFullName = String.format("%s %s", loggedInClient.getFirstName(), loggedInClient.getLastName());
                final String phoneNumber = loggedInClient.getUserPhoneNumber();
                final String cinNumber = loggedInClient.getCin();
                final String drivingLicenseCategory = String.valueOf(loggedInClient.getLicenseCategory());
                final String drivingLicenseExpireDate = String.valueOf(loggedInClient.getLicenseExpireDate());
                final String drivingLicenseObtainDate = String.valueOf(loggedInClient.getLicenseObtainDate());
                final String address = loggedInClient.getAddress();
                final String city = loggedInClient.getCity();

                clientFullNameTV.setText(clientFullName);
                emailTV.setText(email);
                phoneNumberTV.setText(phoneNumber);
                usernameTV.setText(username);
                cinNumberTV.setText(cinNumber);
                addressTV.setText(address);
                cityTV.setText(city);
                drivingLicenseCategoryTV.setText(drivingLicenseCategory);
                drivingLicenseExpireDateTV.setText(drivingLicenseExpireDate);
                drivingLicenseObtainDateTV.setText(drivingLicenseObtainDate);
            }
        }

        if(layoutResId == R.layout.cars){
            retrievePostedCarsForAllAgencies();
        }
    }

    public void retrievePostedCarsForAllAgencies() {
        LinearLayout myLayout = findViewById(R.id.mylayoutcar);
        // Get a reference to the "Agency" node in the database
        DatabaseReference agenciesRef = DatabaseUtil.connect().child("Agency");
        agenciesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot agenciesSnapshot) {
                for (DataSnapshot agencySnapshot : agenciesSnapshot.getChildren()) {
                    String agencyUsername = agencySnapshot.getKey();
                    String agencyCity = agencySnapshot.child("city").getValue(String.class);
                    // Get a reference to the "Cars" node under each agency
                    DatabaseReference postedCarsRef = agencySnapshot.child("Cars").getRef();

                    postedCarsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot carsSnapshot) {
                            for (DataSnapshot carSnapshot : carsSnapshot.getChildren()) {
                                String carModel = carSnapshot.child("model").getValue(String.class);
                                String carPrice = carSnapshot.child("pricePerDay").getValue(String.class);
                                String carMatricule = carSnapshot.child("matricula").getValue(String.class);

                                if (carModel != null && carPrice != null && carMatricule != null) {
                                    // Create CardView
                                    CardView cardView = new CardView(clientmain.this);
                                    // Set layout parameters for the CardView
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    layoutParams.setMargins(
                                            40,
                                            20,
                                            40,
                                            50
                                    );
                                    cardView.setLayoutParams(layoutParams);

                                    // Inflate the layout
                                    View cardLayout = LayoutInflater.from(clientmain.this).inflate(R.layout.card_client_layout, null);

                                    // Find views in the cardLayout
                                    ImageView imageView = cardLayout.findViewById(R.id.imageView);
                                    TextView textViewCarModel = cardLayout.findViewById(R.id.textViewCarModel);
                                    TextView textViewPrice = cardLayout.findViewById(R.id.textViewPrice);
                                    TextView textViewLocation = cardLayout.findViewById(R.id.textViewLocation);

                                    // Set data to views
                                    textViewCarModel.setText(carModel);
                                    textViewPrice.setText(carPrice);
                                    textViewLocation.setText(agencyCity); // Display agency information

                                    // Retrieve and set the car image from Firebase Storage
                                    String imageName = carMatricule + ".png";
                                    StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("carImages/" + agencyUsername + "/" + imageName);

                                    final long ONE_MEGABYTE = 1024 * 1024; // Adjust as needed
                                    imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                                        // Convert byte array to Bitmap
                                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                                        // Set the Bitmap to the ImageView
                                        imageView.setImageBitmap(bitmap);
                                    }).addOnFailureListener(exception -> {
                                        // Handle any errors that occurred while fetching the image
                                        exception.printStackTrace();
                                    });

                                    cardView.addView(cardLayout);

                                    // Add the CardView to the LinearLayout
                                    myLayout.addView(cardView);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError carsError) {
                            // Handle errors if needed
                            carsError.toException().printStackTrace();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError agenciesError) {
                // Handle errors if needed
                agenciesError.toException().printStackTrace();
            }
        });
    }
}