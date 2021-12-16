package com.sgnr.sgnrclasses.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.LoginDetailsModel;
import com.sgnr.sgnrclasses.Model.PaidModel;
import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.Vedio.VideoActivity;
import com.sgnr.sgnrclasses.ui.home.PaymentActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaidLessonAdapter extends RecyclerView.Adapter<PaidLessonAdapter.ViewHolder> {

    List<PaidModel> paidModel;
    Context context;
    private String course_name,name;

    private static final String PREF_NAME = "Shared pref";
    private static final String KEY_NAME = "name";
    private SharedPreferences sharedPreferences;


    public PaidLessonAdapter (Context context,List<PaidModel> paidModel) {
        this.paidModel = paidModel;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public PaidLessonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listItem = inflater.inflate(R.layout.paid_recycler_view,viewGroup,false);
        return new PaidLessonAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaidModel model = paidModel.get(position);
        holder.textPaidCourseName.setText(model.getLesson_Name());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = sharedPreferences.getString(KEY_NAME,null);
                Intent intent = ((Activity)context).getIntent();
                course_name = intent.getStringExtra("paid_course_name");
                holder.checkUser(name,course_name,context,model.getLesson_Name(), model.getLesson_VedioLink());
            }
        });
    }

    @Override
    public int getItemCount() {
        return paidModel.size() ;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textPaidId,textPaidCourseName;

        private ApiInterface apiInterface;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textPaidId = itemView.findViewById(R.id.textPaidId);
            textPaidCourseName = itemView.findViewById(R.id.textPaidName);
        }

       private void checkUser(String userName,String course,Context context,String videoTitle,String videoUrl){
           Retrofit retrofit = ApiClient.getClient();
           apiInterface = retrofit.create(ApiInterface.class);

           apiInterface.checkPaymentStatus(userName,course).enqueue(new Callback<LoginDetailsModel>() {
               @Override
               public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                   try{
                       if(response != null){
                           if(response.body().getStatus().equals("1")) {
                                Intent intent = new Intent(context, VideoActivity.class);
                                intent.putExtra("video_url",videoUrl);
                                intent.putExtra("video_title",videoTitle);
                                context.startActivity(intent);
                           }else {
                               context.startActivity(new Intent(context,PaymentActivity.class));
                           }

                       }else {
                           Log.d("Payment",response.body().getMessage());
                       }
                       }catch (Exception e){
                       Log.d("Payment Status",e.getLocalizedMessage());
                   }
               }

               @Override
               public void onFailure(Call<LoginDetailsModel> call, Throwable throwable) {
                   Log.d("Payment Status",throwable.getLocalizedMessage());
               }
           });
       }

    }
}
