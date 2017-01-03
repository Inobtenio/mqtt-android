package com.example.kevin.mqtt;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    public static View layout;
    private Mqtt mqttP, mqttS;
    static int r,g,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mqttP = new Mqtt(getApplicationContext());
        mqttS = new Mqtt(getApplicationContext());
        mqttS.subscribe("colors");
        layout = findViewById(R.id.activity_main);
        button = (Button) findViewById(R.id.random_color);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomValues();
                ColorChanger.changeBackgroundColor(layout,r,g,b);
                mqttP.publish(String.valueOf(r + "," + g + "," + b), "colors", 2);
            }
        });
    }

    private void randomValues(){
        r = (int)(Math.random() * 255);
        g = (int)(Math.random() * 255);
        b = (int)(Math.random() * 255);
    }


}
