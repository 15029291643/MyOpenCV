
package com.dome.myopencv;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dome.myopencv.databinding.ActivityMain2Binding;

class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain2Binding binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}