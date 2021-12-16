package com.sgnr.sgnrclasses.ui.Store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.identity.intents.AddressConstants;
import com.sgnr.sgnrclasses.Adapter.CourseAdapter;
import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.CourseModel;
import com.sgnr.sgnrclasses.Model.CourseModelResponse;
import com.sgnr.sgnrclasses.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StoreFragment extends Fragment {

    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private CourseAdapter courseAdapter;
    private EditText editText;
    ImageView search;
    List<CourseModel> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        recyclerView = view.findViewById(R.id.storeRecylerView);

        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);
        editText = view.findViewById(R.id.storeSearch_box);

        search = view.findViewById(R.id.store_search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCHR = editText.getText().toString();
                if (editText.getText().toString().length() > 0) {
                    ArrayList<CourseModel> listNew = new ArrayList<>();
                    for (int l = 0; l < arrayList.size(); l++) {
                        String data = arrayList.get(l).getCourseName().toLowerCase();
                        if (data.contains(strCHR.toLowerCase())) {
                            listNew.add(arrayList.get(l));
                        }
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    courseAdapter = new CourseAdapter(getActivity(), listNew);
                    recyclerView.setAdapter(courseAdapter);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    courseAdapter = new CourseAdapter(getActivity(), arrayList);
                    recyclerView.setAdapter(courseAdapter);
                }
            }
        });

        apiInterface.getPaidCourses().enqueue(new Callback<CourseModelResponse>() {
            @Override
            public void onResponse(Call<CourseModelResponse> call, Response<CourseModelResponse> response) {

                try {
                    if (response != null) {
                        if (response.body().getStatus().equals("1")) {
                            arrayList = response.body().getData();
                            courseAdapter = new CourseAdapter(getActivity(), response.body().getData());
                            recyclerView.setAdapter(courseAdapter);

                        } else {
                            Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    Log.d("failure", e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<CourseModelResponse> call, Throwable throwable) {
                Log.d("failure", throwable.getLocalizedMessage());
            }
        });
        return view;
    }
}