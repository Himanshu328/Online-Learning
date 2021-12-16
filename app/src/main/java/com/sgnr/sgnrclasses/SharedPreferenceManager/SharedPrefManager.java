package com.sgnr.sgnrclasses.SharedPreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sgnr.sgnrclasses.LoginDetails.LoginActivity;

public class SharedPrefManager {

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String PREF_NAME = "Shared pref";

    private static final String KEY_NAME = "name";
    private static final String COURSE_NAME = "course_name";
    private static final String COURSE_PRICE = "course_price";
    private static final String KEY_PASS = "pass";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";



    public SharedPrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    // for user activities

    public void createUserLoginSession(String name,String pass){
        editor.putBoolean(IS_USER_LOGIN,true);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_PASS,pass);
        editor.commit();
    }

    public void getUserName(String name){
        editor.putString(KEY_NAME,name);
        editor.commit();
    }

    public void getMyCourse(String course){
        editor.putString(COURSE_NAME,course);
        editor.commit();
    }

    public void getCourse(String course,String amount){
        editor.putString(COURSE_NAME,course);
        editor.putString(COURSE_PRICE,amount);
        editor.commit();
    }
    public void getUserDetails(String mobile,String email){
        editor.putString(KEY_MOBILE,mobile);
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }


    public boolean checkLogin(){
        if(!this.isUserLoggedIn()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public boolean isUserLoggedIn(){
        return preferences.getBoolean(IS_USER_LOGIN,false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
