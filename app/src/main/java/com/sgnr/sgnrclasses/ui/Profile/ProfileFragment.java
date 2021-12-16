package com.sgnr.sgnrclasses.ui.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.LoginDetailsModel;
import com.sgnr.sgnrclasses.Model.ProfileModel;
import com.sgnr.sgnrclasses.Model.ProfileResponseModel;
import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.SharedPreferenceManager.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {

    private ApiInterface apiInterface;
    private String name;
    private List<ProfileModel> profileModel;
    private TextView username,email,mobile_no;
    private SharedPrefManager sharedPrefManager;
    private static final String PREF_NAME = "Shared pref";

    private static final String KEY_NAME = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME,null);

        sharedPrefManager = new SharedPrefManager(getActivity());

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        username = view.findViewById(R.id.textProfileName);
        email = view.findViewById(R.id.textProfileEmail);
        mobile_no = view.findViewById(R.id.textProfileMobile);

        apiInterface.getUserDetails(name).enqueue(new Callback<ProfileResponseModel>() {
            @Override
            public void onResponse(Call<ProfileResponseModel> call, Response<ProfileResponseModel> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            profileModel = response.body().getData();
                            username.setText(profileModel.get(0).getUsername());
                            email.setText(profileModel.get(0).getEmail());
                            mobile_no.setText(profileModel.get(0).getMobile_no());
                            sharedPrefManager.getUserDetails(profileModel.get(0).getMobile_no(),profileModel.get(0).getEmail());
                        }else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_profile,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.editProfile:
                startActivity(new Intent(getActivity(),EditProfileActivity.class));
                break;
            case R.id.forget_password:
                String message = name+" is requesting for password";
                sendForgetPasswordRequest(name,message);
                break;

            case R.id.logout:
                sharedPrefManager.logoutUser();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void sendForgetPasswordRequest(String name,String message){
        apiInterface.sendFeedback(name,message).enqueue(new Callback<LoginDetailsModel>() {
            @Override
            public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            Toast.makeText(getActivity(), "Your password is sent on your Gmail account", Toast.LENGTH_LONG).show();
                            Log.d("Password","response:"+response.body().getMessage());
                        }else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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