package com.sgnr.sgnrclasses.LoginDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.MainActivity;
import com.sgnr.sgnrclasses.Model.LoginDetailsModel;
import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.SharedPreferenceManager.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUser,editTextPassword;
    private Button loginBtn;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;
    private ProgressBar progressBar;
    private TextView createNewAccount,forgetPassword;
    private static final String PREF_NAME = "Shared pref";
    private static final String KEY_NAME = "name";
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPass);
        progressBar = findViewById(R.id.loginProgressBar);
        loginBtn = findViewById(R.id.login);
        createNewAccount = findViewById(R.id.textCreateNewAccount);
        forgetPassword = findViewById(R.id.loginForget);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = preferences.getString(KEY_NAME,null);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        sharedPrefManager = new SharedPrefManager(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = String.valueOf(editTextUser.getText());
                String password = String.valueOf(editTextPassword.getText());

                progressBar.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.INVISIBLE);

                if(username.isEmpty()){
                    editTextUser.setError("Please enter username");
                    editTextUser.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                }else if(password.isEmpty()){
                    editTextPassword.setError("Please enter password");
                    editTextPassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                }else if(password.length()<8){
                    editTextPassword.setError("Password must contains at least 8 character");
                    editTextPassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                }else{
                    sharedPrefManager.getUserName(username);
                    apiInterface.loginUser(username,password).enqueue(new Callback<LoginDetailsModel>() {
                        @Override
                        public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                            try{
                                if(response != null){
                                    if(response.body().getStatus().equals("1")){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        loginBtn.setVisibility(View.VISIBLE);
                                        sharedPrefManager.createUserLoginSession(username,password);
                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    }else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        loginBtn.setVisibility(View.VISIBLE);
                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }catch(Exception e){
                                Log.d("failure",e.getLocalizedMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginDetailsModel> call, Throwable throwable) {
                            Log.d("failure",throwable.getLocalizedMessage());
                        }
                    });
                }

            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = name+" is requesting for password";
                sendForgetPasswordRequest(name,message);
            }
        });
    }

    public void sendForgetPasswordRequest(String name,String message){
        apiInterface.sendFeedback(name,message).enqueue(new Callback<LoginDetailsModel>() {
            @Override
            public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            Toast.makeText(LoginActivity.this, "Your password is sent on your Gmail account", Toast.LENGTH_LONG).show();
                            Log.d("Password","response:"+response.body().getMessage());
                        }else {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch(Exception e){
                    Log.d("failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginDetailsModel> call, Throwable throwable) {
                Log.d("failure",throwable.getLocalizedMessage());
            }
        });
    }
}