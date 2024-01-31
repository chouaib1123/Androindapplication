package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.*;

import com.google.android.material.navigation.NavigationView;

public class agencymain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agencymain);
        NavigationView nv = findViewById(R.id.navigation_view);

        ImageView menu = findViewById(R.id.menu_icon);
        View headerView = nv.getHeaderView(0);

        ImageView  backbtn = headerView.findViewById(R.id.backnav);

        menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                OpenMenu();
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                CloseMenu();
            }
        });
    }
 public  void OpenMenu(){
     NavigationView nv = findViewById(R.id.navigation_view);
     nv.setVisibility(View.VISIBLE);
 }
    public  void CloseMenu(){
        NavigationView nv = findViewById(R.id.navigation_view);
        nv.setVisibility(View.INVISIBLE);
    }
}