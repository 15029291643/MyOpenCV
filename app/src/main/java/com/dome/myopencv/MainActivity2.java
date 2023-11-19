package com.dome.myopencv;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dome.myopencv.databinding.ActivityMain2Binding;
import com.dome.myopencv.util.ShowUtil;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        permission();
        binding.button.setOnClickListener(v -> preview());
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    preview();
                } else {
                    ShowUtil.showLog("请求权限失败");
                }
            });


    void permission() {
        String permission = android.Manifest.permission.CAMERA;

        if (ContextCompat.checkSelfPermission(
                this, permission) ==
                PackageManager.PERMISSION_GRANTED) {
            preview();

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle("请求相机权限")
                    .setMessage("请授予相机权限，否则无法工作")
                    .setPositiveButton("好的，我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create();
        } else {
            requestPermissionLauncher.launch(
                    permission);
        }
    }

    void preview() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }, ContextCompat.getMainExecutor(this));
    }

    final private ExecutorService executorService = Executors.newFixedThreadPool(1);


    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(executorService, imageProxy -> {
            int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
            ShowUtil.showLog(rotationDegrees);
            Matrix matrix = new Matrix();
            matrix.setRotate(rotationDegrees);
            Bitmap copy = imageProxy.toBitmap().copy(Bitmap.Config.ARGB_8888, false);
            Bitmap bitmap = Bitmap.createBitmap(copy, 0, 0, copy.getWidth(), copy.getHeight(), matrix, false);
            binding.imageView.post(() -> {
                binding.imageView.setImageBitmap(bitmap);
            });
            imageProxy.close();
        });

        cameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis, preview);
    }
}

