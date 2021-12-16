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
import com.sgnr.sgnrclasses.ui.home.PaidLessonActivity;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    List<CourseModel> courseModel;
    Context context;
    SharedPrefManager sharedPrefManager;

    public CourseAdapter(Context context,List<CourseModel> courseModel) {
        this.courseModel = courseModel;
        this.context = context;
        sharedPrefManager = new SharedPrefManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listItem = inflater.inflate(R.layout.courses_recycle_view,viewGroup,false);
        return new CourseAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseModel model = courseModel.get(position);
        holder.coursename.setText(model.getCourseName());
        holder.coursePrice.setText("\u20B9 "+model.getCoursePrice());

        String url = "https://sgnr.000webhostapp.com/"+model.getCourseImage();

        Glide.with(context)
                .load(url)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.getCourse(model.getCourseName(), model.getCoursePrice());
                if (sharedPrefManager.isUserLoggedIn() == true) {
                    Intent intent = new Intent(context, PaidLessonActivity.class);
                    intent.putExtra("paid_course_name",model.getCourseName());
                    intent.putExtra("paid_course_price",model.getCoursePrice());
                    intent.putExtra("paid_image_url",url);
                    context.startActivity(intent);
                }else{
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView coursename,coursePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.course_img);
            coursename = itemView.findViewById(R.id.textCourseName);
            coursePrice = itemView.findViewById(R.id.textCoursePrice);
        }
    }
}
