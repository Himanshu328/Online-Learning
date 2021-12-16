package com.sgnr.sgnrclasses.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sgnr.sgnrclasses.Adapter.PaidLessonAdapter;
import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.PaidModel;
import com.sgnr.sgnrclasses.Model.PaidModelResponse;
import com.sgnr.sgnrclasses.R;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaidLessonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private ImageView imageView;
    private String notesUrl;
    private List<PaidModel> paidModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_lesson);

        recyclerView = findViewById(R.id.recyclerViewPaid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TextView textViewNotes = findViewById(R.id.notes);

        Intent intent = getIntent();
        String course_name = intent.getStringExtra("paid_course_name");
        String url = intent.getStringExtra("paid_image_url");

        imageView = findViewById(R.id.paidCourseImg);

        Glide.with(this)
                .load(url)
                .into(imageView);

        TextView textView = findViewById(R.id.paidCourseTitle);
        textView.setText(course_name);


        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);


        apiInterface.getPaidCoursesDetails(course_name).enqueue(new Callback<PaidModelResponse>() {
            @Override
            public void onResponse(Call<PaidModelResponse> call, Response<PaidModelResponse> response) {
                try {
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            paidModel = response.body().getData();
                            notesUrl = "https://sgnr.000webhostapp.com/"+ paidModel.get(0).getCourseNotes();
                            PaidLessonAdapter paidLessonAdapter = new PaidLessonAdapter(PaidLessonActivity.this,response.body().getData());
                            recyclerView.setAdapter(paidLessonAdapter);
                        }else {
                            Toast.makeText(PaidLessonActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    Log.d("failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<PaidModelResponse> call, Throwable throwable) {
                Log.d("failure",throwable.getLocalizedMessage());
            }
        });

        textViewNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse(notesUrl));
                startActivity(intent1);
            }
        });
    }
}