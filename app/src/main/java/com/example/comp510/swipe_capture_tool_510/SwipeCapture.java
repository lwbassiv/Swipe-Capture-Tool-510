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
import android.app.IntentService;


import java.util.logging.Logger;

public class SwipeCapture extends IntentService {
    private MotionEvent touch;
    private static final String TAG = "SwipeCapture";
    //private static Handler handler;
    private Swipe temp; // = new Swipe();
    private Context context;
    public static SwipeCollection swipeCollection; // = new SwipeCollection(this);

    public SwipeCapture() {
        super("SwipeCapture");
        //context = getApplicationContext();
        //swipeCollection = new SwipeCollection(context);
    }


    @Override
    public void onHandleIntent(Intent intent) {
        context = getApplicationContext();
        if (swipeCollection == null) {
            swipeCollection = new SwipeCollection(context);
        }
        touch = intent.getParcelableExtra("MotionEvent");
        //Log.i(TAG, "******************Started********************");
        if (touch != null)
            synchronized (this) {
                captureSwipes(touch);
            }
        //return super.onStartCommand(intent, flags, startId);
    }

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        touch = intent.getParcelableExtra("MotionEvent"); //dunno what this function does
        //Log.i(TAG, "******************Started********************");
        if (touch != null)
            captureSwipes(touch);
        return super.onStartCommand(intent, flags, startId);
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public void captureSwipes(MotionEvent event) {
        touch = event;

        switch (touch.getAction()) {
            case MotionEvent.ACTION_DOWN: //Capture on DOWN PRESS
                temp = new Swipe(); //create new swipe on down press
                swipeCollection.add(temp); //add new swipe (needed to move this)
                temp.captureSwipe(touch); //capture swipe info
                //Context context = getApplicationContext(); //made as a private variable
                //int duration = Toast.LENGTH_SHORT; //doesnt need to be its own variable

                //cant send toast from intentservice, sorry
                //Toast.makeText(getApplicationContext(), "New Swipe", Toast.LENGTH_SHORT).show();//feedback
                Log.i(TAG, "New swipe");
                //temp.captureSwipe(touch); //why do u capture it twice here
                break;
            default:
                //Capture ACTION_MOVE and ACTION_UP
                swipeCollection.getLast().captureSwipe(touch);//capture location data
                //class is not persistent though each swipe
                //Log.i(TAG, "other"); //feedback
                /*if(touch.getAction() == MotionEvent.ACTION_UP)
                    Log.i(TAG, "endSwipe");
                else
                    Log.i(TAG, "contSwipe");*/
                break;
        }
    }
}
