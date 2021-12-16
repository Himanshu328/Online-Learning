package com.sgnr.sgnrclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sgnr.sgnrclasses.LoginDetails.LoginActivity;
import com.sgnr.sgnrclasses.Model.CourseModel;
import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.SharedPreferenceManager.SharedPrefManager;
import com.sgnr.sgnrclasses.ui.Quiz.Quiz2Activity;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder>{

    List<CourseModel> mcqModel;
    Context context;
    SharedPrefManager sharedPrefManager;

    public QuizAdapter(Context context, List<CourseModel> mcqModel) {
        this.mcqModel = mcqModel;
        this.context = context;
        sharedPrefManager = new SharedPrefManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listItem = inflater.inflate(R.layout.quiz_recycle_view,viewGroup,false);
        return new QuizAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseModel model = mcqModel.get(position);
        holder.courseName.setText(model.getCourseName());

        String url = "https://sgnr.000webhostapp.com/"+model.getCourseImage();

        Glide.with(context)
                .load(url)
                .into(holder.imageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.getCourse(model.getCourseName(), model.getCoursePrice());
                if (sharedPrefManager.isUserLoggedIn() == true) {
                    Intent intent = new Intent(context, Quiz2Activity.class);
                    intent.putExtra("quiz_course_name",model.getCourseName());
                    context.startActivity(intent);
                }else{
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mcqModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView courseName;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.quizCourseName);
            imageView = itemView.findViewById(R.id.quizCourse_img);
        }
    }
}
