package com.sgnr.sgnrclasses.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.LoginDetailsModel;
import com.sgnr.sgnrclasses.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FeedbackFragment extends Fragment {

    EditText editTextFeedback;
    private ApiInterface apiInterface;

    private static final String PREF_NAME = "Shared pref";
    private static final String KEY_NAME = "name";
    private Button submit;
    private String message,name;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        editTextFeedback = view.findViewById(R.id.editTextFeedbackMessage);

        message = editTextFeedback.getText().toString();

        submit = view.findViewById(R.id.buttonFeedbackSubmit);

        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = preferences.getString(KEY_NAME,null);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                apiInterface.sendFeedback(name,message).enqueue(new Callback<LoginDetailsModel>() {
                    @Override
                    public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                        try{
                            if(response != null){
                                if(response.body().getStatus().equals("1")){
                                    editTextFeedback.setText("");
                                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                                }else {
                                    editTextFeedback.setText("");
                                    Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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

        return view;
    }

}
