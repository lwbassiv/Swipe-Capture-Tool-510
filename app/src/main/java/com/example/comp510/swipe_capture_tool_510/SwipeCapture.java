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

public class SwipeCapture extends Service {
    private MotionEvent touch;
    private static final String TAG = "SwipeCapture";
    //private static Handler handler;
    private Swipe temp = new Swipe();

    ;
    //MotionEvent ev;
    private SwipeCollection swipeCollection = new SwipeCollection(this);
    public SwipeCapture() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        touch = intent.getParcelableExtra("MotionEvent");
        //Log.i(TAG, "******************Started********************");
        if (touch != null)
            captureSwipes(touch);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    public void captureSwipes(MotionEvent event) {


        touch = event;

        switch (touch.getAction()) {
            case MotionEvent.ACTION_DOWN:
                swipeCollection.add(temp);
                temp = new Swipe();
                temp.captureSwipe(touch);
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, "New Swipe", duration).show();
                Log.i(TAG, "New swipe");
                temp.captureSwipe(touch);
                break;
            default:

                temp.captureSwipe(touch);
                Log.i(TAG, "other");
                break;


        }
    }
}
