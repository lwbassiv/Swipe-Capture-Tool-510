package com.example.comp510.swipe_capture_tool_510;

import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Rider X2 on 10/21/2015.
 */
public class Swipe {
    private ArrayList<Double> x;
    private ArrayList<Double> y;
    private ArrayList<ArrayList<Double>> loc;
    Swipe(){
        loc = new ArrayList<ArrayList<Double>>();
        x =new ArrayList<Double>();
        y =new ArrayList<Double>();
        loc.add(y);
        loc.add(x);
    }
    public void captureSwipe(MotionEvent event){
        loc.get(0).add((double) event.getX());
        loc.get(1).add((double) event.getY());

    }
    @Override
    public String toString(){
        String xString ="";
        String yString="";
        for (double x: loc.get(0)){
            xString= xString +x+"";
        }
        for(double y: loc.get(0)){
            yString= yString +y+ "";
        }
        return "X: "+xString+"\n"+"Y:"+yString+"\n";
    }
}
