package com.example.comp510.swipe_capture_tool_510;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;

public class SwipeCapture extends Service implements Runnable {
    View.OnTouchListener touchListener;
   // SwipeCollection swipeCollection;
    public SwipeCapture() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void run() {

        while(true){
            touchListener = new View.OnTouchListener() {
                Swipe temp;
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            temp = new Swipe();
                            temp.captureSwipe(event);
                            break;
                        case MotionEvent.ACTION_UP:
                            temp.captureSwipe(event);
                            //swipeCollection.add();
                            break;
                        default:
                            temp.captureSwipe(event);
                            break;

                    }
                    return true;
                }
            };
        }
    }
}
