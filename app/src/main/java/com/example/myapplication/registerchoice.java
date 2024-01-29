package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
public class registerchoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerchoice);
        Button btn1 = findViewById(R.id.client);
        Button btn2 = findViewById(R.id.agency);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                clientregister();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                agencyregister();
            }
        });

        TextView txt =  findViewById(R.id.backtologin);
        txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view){
               backtoactivity1();

            }
        });
    }
    public void  backtoactivity1(){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
    public void  clientregister(){
        Intent intent = new Intent(this, clientregister.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
    public void  agencyregister(){
        Intent intent = new Intent(this, agencyregister.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}