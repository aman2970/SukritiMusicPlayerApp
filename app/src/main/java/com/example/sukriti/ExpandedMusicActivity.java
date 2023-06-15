package com.example.sukriti;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.SeekBar;

import com.example.sukriti.Models.MusicFile;
import com.example.sukriti.databinding.ActivityExpandedMusicBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ExpandedMusicActivity extends AppCompatActivity {
    private ActivityExpandedMusicBinding binding;
    MediaPlayer mediaPlayer = new MediaPlayer();
    private Runnable runnable;
    private int currentSongIndex = 0;
    private int currentPosition;
    private List<MusicFile> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpandedMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        songList = (List<MusicFile>) getIntent().getSerializableExtra("songList");
        currentPosition = getIntent().getIntExtra("position",0);


        setUpMusicPlayer(songList.get(currentPosition).getTitle(),songList.get(currentPosition).getPath());

        binding.nextButton.setOnClickListener(v -> {
            if (currentPosition < songList.size() - 1) {
                currentPosition++;
            } else {
                currentPosition = 0;
            }
            playSong();
        });

        binding.previousButton.setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
            } else {
                currentPosition = songList.size() - 1;
            }
            playSong();
        });

        binding.musicControl.setOnClickListener(v -> {
            controlMusic();
        });

        binding.musicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    int startTime = mediaPlayer.getCurrentPosition();
                    binding.startDuration.setText(getFormattedTime(startTime));
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void setUpMusicPlayer(String title, String filePath){
        binding.musicName.setText(title);
        binding.musicName.setSelected(true);
        startAnimation();

        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        binding.musicSeekbar.setMax(mediaPlayer.getDuration());

        setupRunnable();
        mediaPlayer.start();
        binding.startDuration.postDelayed(runnable, 1000);
        binding.musicControl.setImageResource(R.drawable.musicpause);
    }

    private void playSong(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        try {
            mediaPlayer.setDataSource(songList.get(currentPosition).getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupRunnable() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer.isPlaying()) {
                    binding.musicSeekbar.setProgress(mediaPlayer.getCurrentPosition());
                    int startTime = mediaPlayer.getCurrentPosition();
                    int totalDuration = mediaPlayer.getDuration();

                    binding.startDuration.setText(getFormattedTime(startTime));
                    binding.endDuration.setText(getFormattedTime(totalDuration));
                }
                binding.startDuration.postDelayed(this, 1000);
            }
        };
    }

    private void controlMusic() {
        if (mediaPlayer.isPlaying()) {
            binding.musicControl.setImageResource(R.drawable.musicplay);
            mediaPlayer.pause();
            stopAnimation();
        } else {
            binding.musicControl.setImageResource(R.drawable.musicpause);
            mediaPlayer.start();
            startAnimation();
        }
    }

    private String getFormattedTime(int timeInMillis) {
        int seconds = (timeInMillis / 1000) % 60;
        int minutes = (timeInMillis / (1000 * 60)) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    private void startAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(2000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        binding.circleIv.startAnimation(rotateAnimation);
    }

    private void stopAnimation() {
        binding.circleIv.clearAnimation();
    }

}