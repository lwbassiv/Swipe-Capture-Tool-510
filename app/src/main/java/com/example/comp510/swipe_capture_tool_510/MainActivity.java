package com.example.comp510.swipe_capture_tool_510;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainActivity extends AppCompatActivity {
    private TextView out;
    private Button webBtn;
    private SwipeCapture swipe;
    private ViewGroup contentView;
    //private Layout l;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        out = (TextView) findViewById(R.id.hello);
        contentView = (ViewGroup) findViewById(R.id.my_relative_layout_id);

        webBtn = (Button) findViewById((R.id.webButton));
        webBtn.setOnClickListener(new WebButtonListener());

        swipe = new SwipeCapture();
        i = new Intent(this, SwipeCapture.class);
        startService(i);

    }
    //Spy on motion events
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        //Spy on motion events
        i = new Intent(this, SwipeCapture.class);
        i.putExtra("MotionEvent", event);
        startService(i);
        super.dispatchTouchEvent(event);
        return false;
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
        } else if (id == R.id.file) {
            out.setText("");
            out.append(swipe.swipeCollection.toText());
        }else if (id == R.id.license){
            try {
                InputStream input = getAssets().open("Game2048/LICENSE.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder buf=new StringBuilder();

                String licenseStr;

                while ((licenseStr=reader.readLine()) != null) {
                    licenseStr=reader.readLine();
                    buf.append(licenseStr);
                }

                reader.close();
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("2048 License");
                alertDialog.setMessage(licenseStr);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }catch (Exception e){
                Toast.makeText(this, "Fail "+e.toString() , Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event){
        // SwipeCapture swipe= new SwipeCapture();
         //swipe.captureSwipe(event);
         //out.setText(swipe.toString());
        buttonTouch(event);
        i= new Intent(this, SwipeCapture.class);
        i.putExtra("MotionEvent",event);
        startService(i);
        return true;
     }*/
    /*public boolean writeInternalFile () {

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
    }*/
    public void buttonTouch(MotionEvent event){
        WebView webview = new WebView(this);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        webview.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        //webview.loadUrl("http://help.websiteos.com/websiteos/example_of_a_simple_html_page.htm");
        webview.loadUrl("http://www.google.com");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startGame(View view) {
        WebView webview = new WebView(MainActivity.this);
        //MainActivity.this.getWindow().requestFeature(Window.FEATURE_PROGRESS);


        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDatabaseEnabled(true);
        final Activity activity = MainActivity.this;
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        //webview.loadUrl("http://help.websiteos.com/websiteos/example_of_a_simple_html_page.htm");
        //Uri uri = Uri.parse("file:///assets/2048-Master/index.html");
                webview.loadUrl("file:///android_asset/Game2048/index.html");
        setContentView(webview);
    }


    private class WebButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            WebView webview = new WebView(MainActivity.this);
            //MainActivity.this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
            

            webview.getSettings().setJavaScriptEnabled(true);

            final Activity activity = MainActivity.this;
            webview.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    activity.setProgress(progress * 1000);
                }
            });
            webview.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });

            //webview.loadUrl("http://help.websiteos.com/websiteos/example_of_a_simple_html_page.htm");
            webview.loadUrl("http://www.google.com");
            setContentView(webview);
        }
    }
}
