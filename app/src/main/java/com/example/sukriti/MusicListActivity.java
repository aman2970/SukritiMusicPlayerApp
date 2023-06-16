package com.example.sukriti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
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
    private static final int PERMISSION_REQUEST_CODE = 0;
    private boolean isMusicFilesLoaded = false;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.musicRv.setLayoutManager(new LinearLayoutManager(this));

        checkPermissionAndShowDialog();

    }

    private void checkPermissionAndShowDialog() {
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            showPermissionDialog();
        } else {
            if (!isMusicFilesLoaded) {
                loadMusicFiles();
            }
        }
    }


    private void loadMusicFiles() {
        if (!songs.isEmpty()) {
            return;
        }

        List<File> musicFiles = getMusicFiles();

        if (musicFiles.size() > 0) {
            for (File musicFile : musicFiles) {
                songs.add(new MusicFile(musicFile.getName(), musicFile.getPath()));
            }

            MusicAdapter adapter = new MusicAdapter(this, songs);
            binding.emptyIv.setVisibility(View.INVISIBLE);
            binding.emptyTv.setVisibility(View.INVISIBLE);
            binding.musicRv.setAdapter(adapter);
            isMusicFilesLoaded = true;
        } else {
            binding.emptyIv.setVisibility(View.VISIBLE);
            binding.emptyTv.setVisibility(View.VISIBLE);
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

    private void showPermissionDialog() {
        builder  = new AlertDialog.Builder(this);
        builder.setTitle("Permission Required")
                .setMessage("This app requires permission to access storage. Grant permission?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showPermissionDialog();
                    }
                })
                .setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            showPermissionDialog();
        } else {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            if (!isMusicFilesLoaded) {
                loadMusicFiles();
            }
        }
    }
}