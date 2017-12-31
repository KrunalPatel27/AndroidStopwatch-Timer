package com.example.krunal.timer;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class Stopwatch extends AppCompatActivity {
    ListView listView;
    Button button1, button2;
    TextView textView, lapTimeView;
    int Seconds, Minutes, MilliSeconds ;
    boolean stopwatchOFFState = true;
    long StopWatchTime, MillisecondTime, StartTime, UpdateTime = 0L ;
    Handler handler;
    List ListElementsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        textView =(TextView)findViewById(R.id.timeView);
        lapTimeView = (TextView)findViewById(R.id.lapTimeView);
        listView = (ListView)findViewById(R.id.list1);

        handler = new Handler();

        ListElementsArrayList = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, ListElementsArrayList );
        listView.setAdapter(adapter);

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
                    lapTimeView.setText("00:00.00");
                    button1.setEnabled(false);
                    //clear lap time info-- ListView
                    ListElementsArrayList.clear();
                    adapter.notifyDataSetChanged();

                }else{
                    //If Lap is clicked
                    ListElementsArrayList.add(0, getStringTime(UpdateTime));
                    MillisecondTime +=  UpdateTime;
                    StartTime = SystemClock.elapsedRealtime();
                    listView.smoothScrollToPosition(0);
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            UpdateTime = SystemClock.elapsedRealtime() - StartTime;
            StopWatchTime = MillisecondTime + UpdateTime;

            textView.setText(getStringTime(StopWatchTime));
            lapTimeView.setText(getStringTime(UpdateTime));
            handler.postDelayed(this, 0);
        }
    };

    public String getStringTime (long Time){
        Seconds = (int) (Time / 1000);
        Minutes = Seconds / 60;
        Seconds = Seconds % 60;
        MilliSeconds = (int) (Time % 1000);

        return "" + String.format("%02d",Minutes) + ":"
                + String.format("%02d", Seconds) + "."
                + String.format("%02d", MilliSeconds/10);
    }

}
