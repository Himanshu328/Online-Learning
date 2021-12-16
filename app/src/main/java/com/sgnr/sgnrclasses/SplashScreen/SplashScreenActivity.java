package com.sgnr.sgnrclasses.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.sgnr.sgnrclasses.LoginDetails.RegisterActivity;
import com.sgnr.sgnrclasses.MainActivity;
import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.SharedPreferenceManager.SharedPrefManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPrefManager = new SharedPrefManager(this);

        checkConnection();
    }

    private void checkConnection(){
        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(null!=activeNetwork){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
                Log.d(TAG, "checkConnection: "+"Wifi enabled");
                start();

            }else if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d(TAG, "checkConnection: "+"Mobile Data enabled");
                start();
            }
        }else{
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    public void start(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resume();
            }
        },2000);
    }

    private void resume() {
        if (sharedPrefManager.isUserLoggedIn() == true) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else{
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkConnection();
    }
}