package com.example.comp510.swipe_capture_tool_510;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Adrian Anyansi on 16/11/15.
 */
public class SwipeCollection {
    private File file;
    private String filename = "Swipe File-";
    private Context context;

    private List<Swipe> swipeList = new ArrayList<Swipe>();

    public SwipeCollection(Context context) {
        this.context = context;
    }

    public void add(Swipe s) {
        swipeList.add(s);
    }

    public Swipe getLast() {
        return swipeList.get(swipeList.size()-1);
    }


    public String toText() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
        String date = df.format(new Date());
        String output = "Swipe Capture " + date+'\n';
        //System.out.println("swipelist="+swipeList.size());
        for (Swipe s: swipeList) {
            ArrayList<Double> x = s.getLoc().get(0);
            ArrayList<Double> y = s.getLoc().get(1);

            int size = x.size();
            for (int i = 0; i < size; i++) {
                output += "(" + x.get(i) + "," + y.get(i) + ") ";
            }
            output += "~" + '\n';
        }
        System.out.println(output);
        return output;
    }

    public boolean exportToFile() {
        //context = this.getBaseContext();
        String date = new Date().toString();
        filename += date+".txt";
        File file = new File(context.getFilesDir(), filename);

        try {
            FileOutputStream fileIO = new FileOutputStream(file);
            String output = this.toText();

            fileIO.write(output.getBytes());
            fileIO.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //new BufferedWriter(new FileWriter(filename))

        System.out.print(file.getAbsolutePath());
        return true;
    }
}
