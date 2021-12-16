package com.sgnr.sgnrclasses.ui.mycourses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sgnr.sgnrclasses.Adapter.MyCoursesAdapter;
import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.CourseModelResponse;
import com.sgnr.sgnrclasses.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyCoursesFragment extends Fragment {

    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private MyCoursesAdapter myCoursesAdapter;
    private String name;
    private TextView textView;
    private static final String PREF_NAME = "Shared pref";

    private static final String KEY_NAME = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_courses, container, false);

        recyclerView = view.findViewById(R.id.myCourseRecyclerView);
        textView = view.findViewById(R.id.courseNotAvilable);

        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString(KEY_NAME,null);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getMyCourses(name).enqueue(new Callback<CourseModelResponse>() {
            @Override
            public void onResponse(Call<CourseModelResponse> call, Response<CourseModelResponse> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            myCoursesAdapter = new MyCoursesAdapter(getActivity(), response.body().getData());
                            recyclerView.setAdapter(myCoursesAdapter);
                            textView.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else {
                            Log.d("MyCourses",response.body().getMessage());
                            Toast.makeText(getActivity(), "Buy at least on course to watch", Toast.LENGTH_SHORT).show();
                            textView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                        }
                    }

                }catch(Exception e){
                    Log.d("failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<CourseModelResponse> call, Throwable throwable) {
                Log.d("failure",throwable.getLocalizedMessage());
            }
        });

        return view;
    }
}