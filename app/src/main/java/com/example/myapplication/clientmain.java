package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.android.material.navigation.NavigationView;

public class clientmain extends AppCompatActivity {
//    private LinearLayout containerLayout;
//    TextView selectedTextView;
    private ScrollView scrollView;
    private TextView clientNameTextView;
    private Client loggedInClient;

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
        Intent intent = getIntent();

        if(intent != null) {
            loggedInClient = (Client) intent.getSerializableExtra("loggedInClient");

            if(loggedInClient != null) {
                final String clientFirstName = loggedInClient.getFirstName();
                final String clientLastName = loggedInClient.getLastName();
                clientNameTextView.setText(String.format("%s %s", clientFirstName, clientLastName));
            }
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
    }
}