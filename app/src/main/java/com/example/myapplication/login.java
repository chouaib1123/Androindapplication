package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import android.widget.*;


public class login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button btn = findViewById(R.id.loginbtn);
        TextView txt =  findViewById(R.id.Register);
        txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                OpenActivity2();
            }
        });
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                OpenActivity3();
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
}