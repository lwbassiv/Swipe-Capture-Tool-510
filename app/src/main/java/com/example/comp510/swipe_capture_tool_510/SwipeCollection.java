package com.example.comp510.swipe_capture_tool_510;

import android.content.Context;

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

    public boolean exportToFile() {
        //context = this.getBaseContext();
        String date = new Date().toString();
        filename += date+".txt";
        File file = new File(context.getFilesDir(), filename);

        try {
            FileOutputStream fileIO = new FileOutputStream(file);
            String output = "Swipe Capture " + date+'\n';
            for (Swipe s: swipeList) {
                ArrayList<Double> x = s.getLoc().get(0);
                ArrayList<Double> y = s.getLoc().get(1);
                for (int i=0; i<x.size(); i++) {
                    output += x.get(i)+","+y.get(i)+" ";
                }
                output += '\n';
            }
            //for loop to save each swipe class

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
