package com.example.sukriti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.sukriti.Adapter.MusicAdapter;
import com.example.sukriti.Models.MusicFile;
import com.example.sukriti.databinding.ActivityMusicListBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MusicListActivity extends AppCompatActivity {
    private ActivityMusicListBinding binding;
    private List<MusicFile> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.musicRv.setLayoutManager(new LinearLayoutManager(this));

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            List<File> musicFiles = getMusicFiles();

            for (File musicFile : musicFiles) {
                songs.add(new MusicFile(musicFile.getName(), musicFile.getPath()));
            }

            MusicAdapter adapter = new MusicAdapter(this,songs);

            binding.musicRv.setAdapter(adapter);
        }
    }

    private List<File> getMusicFiles() {
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

        File[] musicFiles = Objects.requireNonNull(musicDir.listFiles());

        List<File> filteredFiles = new ArrayList<>();
        for (File file : musicFiles) {
            if (file.getName().endsWith(".mp3")) {
                filteredFiles.add(file);
            }
        }

        return filteredFiles;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                List<File> musicFiles = getMusicFiles();

                for (File musicFile : musicFiles) {
                    songs.add(new MusicFile(musicFile.getName(), musicFile.getPath()));
                }

                MusicAdapter adapter = new MusicAdapter(this,songs);

                binding.musicRv.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

}