package com.example.krunal.timer;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;

public class Stopwatch extends AppCompatActivity {

    Button button1, button2;
    TextView textView;
    int Seconds, Minutes, MilliSeconds ;
    boolean stopwatchOFFState = true;
    long StopWatchTime, MillisecondTime, StartTime, UpdateTime = 0L ;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        textView =(TextView)findViewById(R.id.timeView);

        handler = new Handler();

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stopwatchOFFState){
                    StartTime = SystemClock.elapsedRealtime();
                    handler.postDelayed(runnable, 0);
                    button2.setText("Stop");
                    button1.setText("Lap");
                    button1.setEnabled(true);
                    stopwatchOFFState = false;
                }else {
                    MillisecondTime +=  UpdateTime;
                    handler.removeCallbacks(runnable);
                    button2.setText("Start");
                    button1.setText("Reset");
                    stopwatchOFFState = true;
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stopwatchOFFState){
                    //If reset is clicked
                    StopWatchTime =0L;
                    MillisecondTime = 0L;
                    StartTime = 0L;
                    UpdateTime = 0L ;
                    button1.setText("Lap");
                    textView.setText("00:00.00");
                    button1.setEnabled(false);
                }else{

                }
            }
        });


    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            UpdateTime = SystemClock.elapsedRealtime() - StartTime;
            StopWatchTime = MillisecondTime + UpdateTime;

            Seconds = (int) (StopWatchTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (StopWatchTime % 1000);
            String tmp = "" + String.format("%02d",Minutes) + ":"
                    + String.format("%02d", Seconds) + "."
                    + String.format("%02d", MilliSeconds/10);
            textView.setText(tmp);
            handler.postDelayed(this, 0);
        }
    };

}
