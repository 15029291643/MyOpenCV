package com.dome.myopencv.base;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    public static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
