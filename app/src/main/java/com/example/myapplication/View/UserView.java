package com.example.myapplication.View;

import android.content.Context;

public interface UserView {

    public void OnRegisterSuccess(Context context, String message);

    public void OnRegisterError(Context context, String message);
}
