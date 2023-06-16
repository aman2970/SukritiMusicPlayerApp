package com.example.sukriti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.sukriti.Adapter.IntroAdapter;
import com.example.sukriti.Models.Intro;
import com.example.sukriti.databinding.ActivityIntroBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntroScreens extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private List<Intro> introList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        introList = new ArrayList<>();
        introList.add(new Intro(R.drawable.headphones, R.string.title_one, R.string.description_one));
        introList.add(new Intro(R.drawable.nowifi, R.string.title_two, R.string.description_two));
        introList.add(new Intro(R.drawable.noads, R.string.title_three, R.string.description_three));

        IntroAdapter introAdapter = new IntroAdapter(introList);
        binding.introPager.setAdapter(introAdapter);
        binding.introCirlceIndicator.setViewPager(binding.introPager);

        binding.backIv.setVisibility(View.INVISIBLE);

        binding.nextIv.setOnClickListener(v -> {
            int nextPage = binding.introPager.getCurrentItem() + 1;
            binding.introPager.setCurrentItem(nextPage, true);

            binding.nextIv.setVisibility(binding.introPager.getCurrentItem() == Objects.requireNonNull(binding.introPager.getAdapter()).getItemCount() - 1 ? View.INVISIBLE : View.VISIBLE);
            binding.doneButton.setVisibility(binding.introPager.getCurrentItem() == Objects.requireNonNull(binding.introPager.getAdapter()).getItemCount() - 1 ? View.VISIBLE : View.INVISIBLE);
            binding.backIv.setVisibility(nextPage == 0 ? View.INVISIBLE : View.VISIBLE);

        });

        binding.backIv.setOnClickListener(v -> {
            int previousPage = binding.introPager.getCurrentItem() - 1;
            binding.introPager.setCurrentItem(previousPage, true);
            binding.backIv.setVisibility(previousPage == 0 ? View.INVISIBLE : View.VISIBLE);
            binding.nextIv.setVisibility(View.VISIBLE);
            binding.doneButton.setVisibility(View.INVISIBLE);
        });

        binding.doneButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isOnboard", true);
            editor.apply();

            Intent intent = new Intent(this, MusicListActivity.class);
            startActivity(intent);
        });

    }
}