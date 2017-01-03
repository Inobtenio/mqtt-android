package com.example.kevin.mqtt;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

/**
 * Created by kevin on 26/12/16.
 */

public class ColorChanger {
    public static void changeBackgroundColor(View layout, int r, int g, int b){
        Log.e("Colors", "values: " + r + g + b);

        layout.setBackgroundColor(Color.rgb(r,g,b));
    }

    public static void changeBackgroundColor(View layout, String color){
        Log.e("Color", color);
        layout.setBackgroundColor(Color.parseColor(color));
    }
}
