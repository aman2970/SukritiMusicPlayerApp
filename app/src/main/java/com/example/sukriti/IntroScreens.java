package com.example.sukriti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.sukriti.Adapter.IntroAdapter;
import com.example.sukriti.Models.Intro;
import com.example.sukriti.databinding.ActivityIntroBinding;

import java.util.ArrayList;
import java.util.List;

public class IntroScreens extends AppCompatActivity {
    private ActivityIntroBinding binding;
    private List<Intro> introList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        introList = new ArrayList<>();
        introList.add(new Intro(R.drawable.headphones,R.string.title_one,R.string.description_one));
        introList.add(new Intro(R.drawable.nowifi,R.string.title_two,R.string.description_two));
        introList.add(new Intro(R.drawable.noads,R.string.title_three,R.string.description_three));

        IntroAdapter introAdapter = new IntroAdapter(introList);
        binding.introPager.setAdapter(introAdapter);
        binding.introCirlceIndicator.setViewPager(binding.introPager);

    }
}