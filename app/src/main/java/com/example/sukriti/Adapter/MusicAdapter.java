package com.example.sukriti.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sukriti.ExpandedMusicActivity;
import com.example.sukriti.Models.MusicFile;
import com.example.sukriti.R;

import java.io.Serializable;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private Activity context;
    private List<MusicFile> musicFiles;

    public MusicAdapter(Activity context, List<MusicFile> musicFiles) {
        this.context = context;
        this.musicFiles = musicFiles;
    }

    @NonNull
    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MyViewHolder holder, int position) {
        holder.title.setText(musicFiles.get(position).getTitle());
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExpandedMusicActivity.class);
            intent.putExtra("songList", (Serializable) musicFiles);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return musicFiles.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.nameTv);
            cardView = itemView.findViewById(R.id.mainCv);
        }
    }
}
