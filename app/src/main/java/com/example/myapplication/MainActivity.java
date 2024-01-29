package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import android.widget.*;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
    public void  OpenActivity3(){
        Intent intent = new Intent(this,Accueil.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}