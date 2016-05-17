package com.example.leed3.courseregistersystem;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class OpeningScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent open = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(open);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}