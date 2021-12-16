package com.sgnr.sgnrclasses.ui.Quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sgnr.sgnrclasses.Adapter.QuizAdapter;
import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.CourseModel;
import com.sgnr.sgnrclasses.Model.CourseModelResponse;
import com.sgnr.sgnrclasses.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuizFragment extends Fragment {

    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private QuizAdapter quizAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);


        recyclerView = view.findViewById(R.id.recyclerViewQuiz);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.getPaidCourses().enqueue(new Callback<CourseModelResponse>() {
            @Override
            public void onResponse(Call<CourseModelResponse> call, Response<CourseModelResponse> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            quizAdapter = new QuizAdapter(getActivity(),response.body().getData());
                            recyclerView.setAdapter(quizAdapter);
                        }else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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