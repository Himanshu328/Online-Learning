package com.sgnr.sgnrclasses.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sgnr.sgnrclasses.Model.DemoModel;
import com.sgnr.sgnrclasses.R;
import com.sgnr.sgnrclasses.Vedio.VideoActivity;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    List<DemoModel> list;
    Context context;

    public MyListAdapter(Context context, List<DemoModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View listItem = inflater.inflate(R.layout.demo_recycle_veiw,parent,false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DemoModel model = list.get(position);
        holder.vedioTitle.setText(model.getDemo_Name());
        Log.d("image_url",model.getDemo_Img());
        Log.d("vedio_url",model.getDemo_Vedios());

        String url = "https://sgnr.000webhostapp.com/"+model.getDemo_Img();

        Glide.with(context)
                .load(url)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("video_url",model.getDemo_Vedios());
                intent.putExtra("video_title",model.getDemo_Name());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView vedioTitle;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
                super(itemView);

            vedioTitle = itemView.findViewById(R.id.vedio_title);
            imageView = itemView.findViewById(R.id.demo_img);
        }
    }
}

