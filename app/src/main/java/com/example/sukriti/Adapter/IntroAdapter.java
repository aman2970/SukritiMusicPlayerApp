package com.example.sukriti.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sukriti.Models.Intro;
import com.example.sukriti.R;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.MyViewHolder> {
    private List<Intro> introList;

    public IntroAdapter(List<Intro> introList) {
        this.introList = introList;
    }

    @NonNull
    @Override
    public IntroAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.intro_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroAdapter.MyViewHolder holder, int position) {
        holder.introIv.setImageResource(introList.get(position).getImage());
        holder.titleTv.setText(introList.get(position).getTitle());
        holder.descriptionTv.setText(introList.get(position).getDescription());


    }

    @Override
    public int getItemCount() {
        return introList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView introIv;
        public TextView titleTv;
        public TextView descriptionTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            introIv = itemView.findViewById(R.id.introIv);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
        }
    }
}
