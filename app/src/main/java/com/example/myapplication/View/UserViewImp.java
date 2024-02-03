package com.example.myapplication.View;

import android.content.Context;
import android.widget.Toast;

public class UserViewImp implements UserView{
    @Override
    public void OnRegisterSuccess(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void OnRegisterError(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
