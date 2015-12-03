package com.example.comp510.swipe_capture_tool_510;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
    private DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
    private DateFormat tf = DateFormat.getTimeInstance(DateFormat.LONG);

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
        String date = df.format(new Date());
        date +=" " + tf.format(new Date());
        String output = '\n'+"Swipe Capture " + date+'\n';
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
        //System.out.println(output); //speeds up file save slightly
        return output;
    }

    public boolean exportToFile() {
        //context = this.getBaseContext();
        /*Redundant
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
        }*/
        try {
            File fileExt = new File(context.getExternalFilesDir(null), filename + df.format(new Date())+".txt");
            file = fileExt;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                FileOutputStream fileIOExt = new FileOutputStream(fileExt, true);
                String output = this.toText();

                fileIOExt.write(output.getBytes());
                fileIOExt.close();
            }
        }catch (IOException e){
            Toast.makeText(context, "Failed to save to external", Toast.LENGTH_SHORT).show();
            return false;
        }

        //new BufferedWriter(new FileWriter(filename))

        Toast.makeText(context, "Saved to "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        System.out.print(file.getAbsolutePath());
        return true;
    }
}
