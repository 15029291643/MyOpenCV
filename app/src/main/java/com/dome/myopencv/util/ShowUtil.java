package com.dome.myopencv.util;

import android.util.Log;
import android.widget.Toast;

import com.dome.myopencv.base.MyApp;

public interface ShowUtil {
    static void showToast(Object obj) {
        Toast.makeText(MyApp.getContext(), obj.toString(), Toast.LENGTH_SHORT).show();
    }

    static void showLog(Object obj) {
        Log.e("haojinhui", obj.toString());
    }
}
