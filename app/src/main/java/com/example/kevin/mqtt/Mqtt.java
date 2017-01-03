package com.example.kevin.mqtt;

/**
 * Created by kevin on 23/12/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.util.Strings;

public class Mqtt implements MqttCallback{
    private static MqttConnectOptions connOpts = new MqttConnectOptions();
    private static MqttMessage message;
    MqttAsyncClient sampleClient;
    Context mContext;

    public Mqtt(Context context) {
        this.mContext = context;
        this.setUp();
    }

    public static void main(Context context) {
        new Mqtt(context).setUp();
    }

    public void setUp() {
        String broker = "ws://test.mosquitto.org:8080";
        int rand = (int)(Math.random() * 255);
        String clientId = "JavaSample" + String.valueOf(rand);
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            Log.e("EEEEEE", "TRY 1");
            sampleClient = new MqttAsyncClient(broker, clientId, persistence);
            sampleClient.setCallback(this);
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts).waitForCompletion();
            Log.e("AAAAAAAA", "Connected" + String.valueOf(System.currentTimeMillis()));
        } catch(MqttException me) {
            Log.e("EEEEEE", "CLIENT ERROR");
            me.printStackTrace();
        }
    }

    public void publish(String content, String topic, int qos){
        message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        message.setRetained(true);
        try{
            Log.e("EEEEEE", "TRY 2");
            sampleClient.publish(topic, message);
        }catch(MqttException me){
            Log.e("EEEEEE", "CLIENT ERROR 2");
            me.printStackTrace();
        }
    }

    public void subscribe(String topic){
        try {
            Log.e("EEEEEE", "TRY 3");
            sampleClient.subscribe(topic, 2);
        } catch (MqttException e) {
            Log.e("EEEEEE", "CLIENT ERROR 3");
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        try {
            sampleClient.setCallback(this);
            connOpts.setCleanSession(false);
            sampleClient.connect().waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, final MqttMessage message) throws Exception {
        Log.e("AAAAAAA", "Message arrived" + message.toString());
        if (message.toString().matches("^(red|blue|black|white|orange|green|pink|yellow|brown|purple)$")){
            Log.e("AAAAAAA", "Matches");
            Utils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ColorChanger.changeBackgroundColor(MainActivity.layout, message.toString());
                }
            });
        } else {
            final int r,g,b;
            r = Integer.parseInt(message.toString().split(",")[0]);
            g = Integer.parseInt(message.toString().split(",")[1]);
            b = Integer.parseInt(message.toString().split(",")[2]);
            Utils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ColorChanger.changeBackgroundColor(MainActivity.layout, r, g, b);
                }
            });
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e("AAAAAAA", "Message delivered");
    }

}
