package com.example.myapplication.View;

public class UserView implements IUserView{
    @Override
    public void OnRegisterSuccess(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void OnRegisterError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
