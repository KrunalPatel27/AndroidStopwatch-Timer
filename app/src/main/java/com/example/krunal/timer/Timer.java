package com.example.krunal.timer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Timer extends AppCompatActivity {

    Button stopwatchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.timer_relative_layout);

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.bottom_menu, null);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        view.setLayoutParams(params);
        relativeLayout.addView(view);


        stopwatchButton = (Button)findViewById(R.id.right_button);
        stopwatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Timer.this, Stopwatch.class);
                startActivity(intent);
            }
        });


    }
}
