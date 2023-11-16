package com.dome.myopencv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dome.myopencv.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain2Binding binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());;
    }
}