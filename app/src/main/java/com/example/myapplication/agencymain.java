package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.example.myapplication.Model.Agency;
import com.example.myapplication.Model.Client;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class agencymain extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://clientregister-c1856-default-rtdb.firebaseio.com/").getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    Map<TextView, Pair<Uri, BitmapDrawable>> textViewImages = new HashMap<>();
    private TextView agencyNameTextView;
    private LinearLayout containerLayout;
    TextView selectedTextView;
    private ScrollView scrollView;
    private Agency loggedInAgency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencymain);

        NavigationView nv = findViewById(R.id.navigation_view);
        ImageView menu = findViewById(R.id.menu_icon);
        View headerView = nv.getHeaderView(0);
        ImageView  backbtn = headerView.findViewById(R.id.backnav);
        agencyNameTextView = headerView.findViewById(R.id.username);
        scrollView = findViewById(R.id.scrollview);

        switchToLayout(R.layout.postedcars);

        Intent intent = getIntent();
        if(intent != null) loggedInAgency = (Agency) intent.getSerializableExtra("loggedInAgency");

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
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
                    switchToLayout(R.layout.agencyprofil);
                } else if (itemId == R.id.postedcards) {
                    switchToLayout(R.layout.postedcars);
                } else if (itemId == R.id.pendreq) {
                    switchToLayout(R.layout.pendingrequest);
                }
                return false;
            }
        });
    }
    public void openMenu() {
        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setVisibility(View.VISIBLE);
        // Apply fade animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        nv.startAnimation(fadeIn);
        setUpAgencyName();
    }

    public void setUpAgencyName(){
        if(loggedInAgency != null) {
            final String agencyName = loggedInAgency.getAgencyName();
            agencyNameTextView.setText(String.format("%s", agencyName));
        }
    }

    public void closeMenu() {
        final NavigationView nv = findViewById(R.id.navigation_view);
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
        View newLayout = LayoutInflater.from(agencymain.this).inflate(layoutResId, scrollView, false);
        scrollView.addView(newLayout);

        if(layoutResId == R.layout.postedcars){
            Button loginButton = findViewById(R.id.ADDcars);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchToLayout(R.layout.carinsert);
                }
            });
        }

        if(layoutResId == R.layout.carinsert){
            TextView Carpic = findViewById(R.id.carpicture);
            setOnClickListenerForTextView( Carpic);
            EditText matricule ,carColor , fuelType ;
            Button addCar = findViewById(R.id.button_add_car);

            addCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        if(layoutResId == R.layout.agencyprofil) {
            TextView agencyNameTV = (TextView) findViewById(R.id.agencyName);
            TextView phoneNumberTV = (TextView) findViewById(R.id.phoneNumber);
            TextView usernameTV = (TextView) findViewById(R.id.agencyUsername);
            TextView emailTV = (TextView) findViewById(R.id.email);
            TextView patentNumberTV = (TextView) findViewById(R.id.patentNumber);
            TextView managerFullNameTV = (TextView) findViewById(R.id.managerFullName);
            TextView addressTV = (TextView) findViewById(R.id.address);
            TextView cityTV = (TextView) findViewById(R.id.city);

            if(loggedInAgency != null) {
                final String email = loggedInAgency.getEmail();
                final String username = loggedInAgency.getUsername();
                final String agencyName = loggedInAgency.getAgencyName();
                final String phoneNumber = loggedInAgency.getUserPhoneNumber();
                final String patentNumber = String.valueOf(loggedInAgency.getPatentNumber());
                final String managerFullName = loggedInAgency.getManagerFullName();
                final String address = loggedInAgency.getAddress();
                final String city = loggedInAgency.getCity();

                agencyNameTV.setText(agencyName);
                emailTV.setText(email);
                phoneNumberTV.setText(phoneNumber);
                usernameTV.setText(username);
                patentNumberTV.setText(patentNumber);
                managerFullNameTV.setText(managerFullName);
                addressTV.setText(address);
                cityTV.setText(city);
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
        ImagePicker.with(agencymain.this)
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
}
