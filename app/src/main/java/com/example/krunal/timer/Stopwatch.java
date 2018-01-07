package com.example.krunal.timer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class Stopwatch extends AppCompatActivity {
    ListView listView;
    Button button1, button2, timerButton;
    TextView textView, lapTimeView;
    int Seconds, Minutes, MilliSeconds ;
    boolean stopwatchOFFState = true;
    long StopWatchTime, MillisecondTime, StartTime, LapTime, UpdateTime = 0L ;
    Handler handler;
    ArrayList ListElementsArrayList;
    LapViewListAdapter adapter;
    int lapCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        textView =(TextView)findViewById(R.id.timeView);
        lapTimeView = (TextView)findViewById(R.id.lapTimeView);
        listView = (ListView)findViewById(R.id.list1);
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.main_relative_layout);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.bottom_menu, null);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
        relativeLayout.addView(view);


        timerButton = (Button)findViewById(R.id.left_button);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stopwatch.this, Timer.class);
                startActivity(intent);
            }
        });



        handler = new Handler();


        ListElementsArrayList = new ArrayList<Lap>();
        adapter = new LapViewListAdapter(this, R.layout.lap_time_list_view, ListElementsArrayList);
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
                    LapTime += UpdateTime;
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
                    LapTime = 0L;
                    button1.setText("Lap");
                    textView.setText("00:00.00");
                    lapTimeView.setText("00:00.00");
                    button1.setEnabled(false);
                    lapCount = 0;
                    //clear lap time info-- ListView
                    ListElementsArrayList.clear();
                    adapter.notifyDataSetChanged();

                }else{
                    //If Lap is clicked
                    String lapCountString = "Lap " + ++lapCount;
                    Lap lap = new Lap(lapCountString, getStringTime(UpdateTime + LapTime));
                    LapTime = 0;
                    ListElementsArrayList.add(0, lap);
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
            lapTimeView.setText(getStringTime(UpdateTime + LapTime));
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
