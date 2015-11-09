package com.example.comp510.swipe_capture_tool_510;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    TextView out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        out= (TextView) findViewById(R.id.hello);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.web) {
            WebView webview = new WebView(this);
            setContentView(webview);
            webview.loadUrl("www.msn.com");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        Swipe swipe= new Swipe();
        swipe.captureSwipe(event);
        out.setText(swipe.toString());
        return true;
    }
    public boolean writeInternalFile () {

        Context context = this.getBaseContext();
        String filename = "test-test";
        File file = new File(context.getFilesDir(), filename);
        FileInputStream fileI;
        try {
            int length = (int)file.length();
            byte[] bytes = new byte[length];
            fileI = new FileInputStream(file);
            fileI.read(bytes);
            fileI.close();
            out.append(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }


        FileOutputStream fileIO;
        try {
            fileIO = new FileOutputStream(file);
            String writeFile = String.valueOf(Math.random());
            fileIO.write(writeFile.getBytes());
            fileIO.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.append(file.getAbsolutePath());
        return true;
    }


}
