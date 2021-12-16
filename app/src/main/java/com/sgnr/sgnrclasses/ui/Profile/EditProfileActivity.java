package com.sgnr.sgnrclasses.ui.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.LoginDetailsModel;
import com.sgnr.sgnrclasses.Model.ProfileModel;
import com.sgnr.sgnrclasses.Model.ProfileResponseModel;
import com.sgnr.sgnrclasses.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextUsername,editTextEmail,editTextMobile_no;
    private ApiInterface apiInterface;
    private List<ProfileModel> profileModel;
    private int id;
    private String email,mobile,name;
    private static final String PREF_NAME = "Shared pref";

    private static final String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextUsername = findViewById(R.id.editTextProfileName);
        editTextEmail = findViewById(R.id.editTextProfileEmail);
        editTextMobile_no = findViewById(R.id.editTextProfileMobile);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME,null);

        apiInterface.getUserDetails(name).enqueue(new Callback<ProfileResponseModel>() {
            @Override
            public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            profileModel = response.body().getData();
                            editTextUsername.setText(profileModel.get(0).getUsername());
                            editTextEmail.setText(profileModel.get(0).getEmail());
                            editTextMobile_no.setText(profileModel.get(0).getMobile_no());
                            id = profileModel.get(0).getID();
                        }else {
                            Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch(Exception e){
                    Log.d("failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponseModel> call, Throwable throwable) {
                Log.d("failure",throwable.getLocalizedMessage());
            }
        });

        findViewById(R.id.editProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = String.valueOf(editTextEmail.getText());
                mobile = String.valueOf(editTextMobile_no.getText());
                apiInterface.editUserProfile(email,mobile,id).enqueue(new Callback<LoginDetailsModel>() {
                    @Override
                    public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                        try{
                            if(response != null){
                                if(response.body().getStatus().equals("1")){
                                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(EditProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        });
    }
}