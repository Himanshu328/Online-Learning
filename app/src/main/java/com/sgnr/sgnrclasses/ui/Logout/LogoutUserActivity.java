package com.sgnr.sgnrclasses.ui.Logout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.SharedPreferenceManager.SharedPrefManager;

public class LogoutUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(this);
        sharedPrefManager.logoutUser();
    }
}