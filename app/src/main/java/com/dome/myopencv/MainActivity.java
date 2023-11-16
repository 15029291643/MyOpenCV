package com.dome.myopencv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,
                OpenCVLoader.initDebug() ? "OpenCV加载成功" : "OpenCV加载失败"
                , Toast.LENGTH_SHORT).show();
    }
}