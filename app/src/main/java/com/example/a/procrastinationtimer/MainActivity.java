package com.example.a.procrastinationtimer;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private long sLastPause;
    private long pLastPause;


    private int working = 1;
    private boolean pausing = true;

    private Chronometer pChronometer;
    private Chronometer sChronometer;

    private ImageView playBtn;
    private ImageView pauseBtn;
    private ImageView stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RelativeLayout r1 = (RelativeLayout) findViewById(R.id.procrastinateView);
        final RelativeLayout r2 = (RelativeLayout) findViewById(R.id.studyView);

        playBtn = (ImageView) findViewById(R.id.overlapPlayImg);
        pauseBtn = (ImageView) findViewById(R.id.overlapPauseImg);
        stopBtn = (ImageView) findViewById(R.id.overlapStopImg);

        pChronometer = (Chronometer) findViewById(R.id.proTime);
        sChronometer = (Chronometer) findViewById(R.id.stdTime);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausing = false;

                switch (working){
                    case 1:
                        if (sLastPause!=0){
                            sChronometer.setBase(sChronometer.getBase() + SystemClock.elapsedRealtime()-sLastPause);
                        }else{
                            sChronometer.setBase(SystemClock.elapsedRealtime());
                        }
                        sChronometer.start();
                        break;

                    case 0:
                        if (pLastPause!=0){
                            pChronometer.setBase(pChronometer.getBase() + SystemClock.elapsedRealtime()-pLastPause);
                        }else{
                            pChronometer.setBase(SystemClock.elapsedRealtime());
                        }
                        pChronometer.start();
                        break;
                }
                pauseBtn.setEnabled(true);
                playBtn.setEnabled(false);
                stopBtn.setEnabled(true);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausing = true;

                switch (working){
                    case 1 :
                            sLastPause = SystemClock.elapsedRealtime();
                            sChronometer.stop();
                            break;

                    case 0:
                            pLastPause = SystemClock.elapsedRealtime();
                            pChronometer.stop();
                            break;
                }
                pauseBtn.setEnabled(false);
                playBtn.setEnabled(true);
                stopBtn.setEnabled(true);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausing = true;

                sChronometer.stop();
                sChronometer.setBase(SystemClock.elapsedRealtime());
                sLastPause =0;
                pChronometer.stop();
                pChronometer.setBase(SystemClock.elapsedRealtime());
                pLastPause =0;


                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
                stopBtn.setEnabled(false);
            }
        });


        r1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                r1.setBackgroundColor(Color.parseColor("#2E83E6"));
                r2.setBackgroundColor(Color.parseColor("#93C5FF"));
                working = 0;
            }
        });

        r2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                r1.setBackgroundColor(Color.parseColor("#93C5FF"));
                r2.setBackgroundColor(Color.parseColor("#2E83E6"));
                working = 1;
            }
        });
    }

//    public void playNow(){
//
//    }
//
//    public void pauseNow(){
//
//    }
}
