package com.example.kevin.mqtt;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by kevin on 28/12/16.
 */

public class Utils {

    public static void runOnUiThread(Runnable runnable){
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler .post(runnable);
    }
}