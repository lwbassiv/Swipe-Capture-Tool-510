package com.example.comp510.swipe_capture_tool_510;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import java.util.logging.Logger;

public class SwipeCapture extends Service implements Runnable {
    private MotionEvent touch;
    private static final String TAG = "SwipeCapture";
    private static Handler handler;
    MotionEvent ev;

    // SwipeCollection swipeCollection;
    public SwipeCapture() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread t = new Thread(this);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                // Gets the image task from the incoming Message object.
                ev = (MotionEvent) inputMessage.obj;
                super.handleMessage(inputMessage);
            }
        };
        t.start();
        Log.i(TAG, "******************Started********************");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void run() {

        while (true) {

            touch = ev;
            Swipe temp=null;
            switch (touch.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (temp != null) {
                        temp.captureSwipe(touch);
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, "lifted finger", duration).show();
                        Log.i(TAG, "Lifted Finger");

                    }
                    temp = new Swipe();
                    temp.captureSwipe(touch);
                    break;
                default:
                    temp.captureSwipe(touch);
                    Log.i(TAG, "other");
                    break;

            }

            ;

        }
    }
}
