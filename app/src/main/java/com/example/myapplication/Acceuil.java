package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Acceuil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil);

        Button btn = findViewById(R.id.welcomebtn);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
                OpenLogin();
            }
        });
    }

    public void  OpenLogin(){

        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}