package com.example.myapplication.View;

import android.content.Context;
import android.widget.Toast;

public class UserViewImp implements UserView{
    @Override
    public void OnSuccess(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void OnError(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
