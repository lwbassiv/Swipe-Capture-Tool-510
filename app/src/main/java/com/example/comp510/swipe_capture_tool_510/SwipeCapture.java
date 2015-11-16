package com.example.comp510.swipe_capture_tool_510;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.logging.Logger;

public class SwipeCapture extends Service implements Runnable {
    View.OnTouchListener touchListener;
    private static final String TAG = "SwipeCapture";
   // SwipeCollection swipeCollection;
    public SwipeCapture() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread t = new Thread(this);
        t.start();
        Log.i(TAG, "******************Started********************");
        return super.onStartCommand(intent,flags,startId);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  null;
    }

    @Override
    public void run() {

        while(true){
            touchListener = new View.OnTouchListener() {
                Swipe temp;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i(TAG,"******************IN********************");
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            if(temp != null) {
                                temp.captureSwipe(event);
                                Context context = getApplicationContext();
                                int duration = Toast.LENGTH_SHORT;
                                Toast.makeText(context, "lifted finger", duration).show();
                                Log.i(TAG,"Lifted Finger");
                            }
                            temp = new Swipe();
                            temp.captureSwipe(event);
                            break;
                        default:
                            temp.captureSwipe(event);
                            Log.i(TAG, "other");
                            break;

                    }
                    return true;
                }
            };
        }
    }
}
