package com.sgnr.sgnrclasses.LoginDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUserName,editTextEmail,editTextMobile,editTextPassword;
    private Button register;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    private TextView alreadyAccount,skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        progressBar = findViewById(R.id.registerProgressBar);

        alreadyAccount = findViewById(R.id.textViewAlreadyAccount);
        skip = findViewById(R.id.textViewSkip);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(editTextUserName.getText());
                String email = String.valueOf(editTextEmail.getText());
                String mobile = String.valueOf(editTextMobile.getText());
                String pass = String.valueOf(editTextPassword.getText());

                progressBar.setVisibility(View.VISIBLE);
                register.setVisibility(View.INVISIBLE);

                if(name.isEmpty()){
                    editTextUserName.setError("Please enter username");
                    editTextUserName.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                }else if(email.isEmpty()){
                    editTextEmail.setError("Please enter Email Address");
                    editTextEmail.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Please enter valid email");
                    editTextEmail.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                }else if(mobile.isEmpty()){
                    editTextMobile.setError("Please enter Mobile Number");
                    editTextMobile.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                }else if(!Patterns.PHONE.matcher(mobile).matches()){
                    editTextMobile.setError("Please enter valid Mobile Number");
                    editTextMobile.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                }else if(pass.isEmpty()){
                    editTextPassword.setError("Please enter password");
                    editTextPassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                }else if(pass.length()<8){
                    editTextPassword.setError("Password must contains at least 8 character");
                    editTextPassword.requestFocus();
                    progressBar.setVisibility(View.INVISIBLE);
                    register.setVisibility(View.VISIBLE);
                }else {
                    apiInterface.registerUser(name,email,mobile,pass).enqueue(new Callback<LoginDetailsModel>() {
                        @Override
                        public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                            try{
                                if(response != null){
                                    if(response.body().getStatus().equals("1")){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        register.setVisibility(View.VISIBLE);
                                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        register.setVisibility(View.VISIBLE);
                                        Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
    }
}