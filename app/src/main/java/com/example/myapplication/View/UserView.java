package com.example.myapplication.View;

import android.content.Context;

public interface UserView {

    public void OnSuccess(Context context, String message);

    public void OnError(Context context, String message);
}
