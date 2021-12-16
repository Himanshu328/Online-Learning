package com.sgnr.sgnrclasses.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sgnr.sgnrclasses.Adapter.CourseAdapter;
import com.sgnr.sgnrclasses.Adapter.MyListAdapter;
import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.CourseModelResponse;
import com.sgnr.sgnrclasses.Model.DemoModelResponse;
import com.sgnr.sgnrclasses.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private RecyclerView paidRecyclerView,demoRecyclerView;
    private TextView textName;
    private ApiInterface apiInterface;
    private MyListAdapter listAdapter;
    private CourseAdapter courseAdapter;

    private static final String PREF_NAME = "Shared pref";

    private static final String KEY_NAME = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        paidRecyclerView = view.findViewById(R.id.paidRecyclerView);
        demoRecyclerView = view.findViewById(R.id.demoRecyclerView);

        textName = view.findViewById(R.id.textName);

        SharedPreferences preferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String name = preferences.getString(KEY_NAME,null);
        textName.setText(name);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false);
        demoRecyclerView.setLayoutManager(layoutManager);

        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        paidRecyclerView.setLayoutManager(layout);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getResponse().enqueue(new Callback<DemoModelResponse>() {
            @Override
            public void onResponse(Call<DemoModelResponse> call, Response<DemoModelResponse> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){

                            listAdapter = new MyListAdapter(getActivity(), response.body().getData());
                            demoRecyclerView.setAdapter(listAdapter);

                        }else {
                            Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch(Exception e){
                    Log.d("failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DemoModelResponse> call, Throwable throwable) {
                Log.d("failure",throwable.getLocalizedMessage());
            }
        });

        apiInterface.getPaidCourses().enqueue(new Callback<CourseModelResponse>() {
            @Override
            public void onResponse(Call<CourseModelResponse> call, Response<CourseModelResponse> response) {

                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            courseAdapter = new CourseAdapter(getActivity(), response.body().getData());
                            paidRecyclerView.setAdapter(courseAdapter);

                        }else {
                            Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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