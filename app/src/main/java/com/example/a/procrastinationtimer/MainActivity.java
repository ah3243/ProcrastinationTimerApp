package com.example.a.procrastinationtimer;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private long sLastPause;
    private long pLastPause;

    private int working = 1;
    private int counting = 0;

    private Chronometer pChronometer;
    private Chronometer sChronometer;

    private ImageView playBtn;
    private ImageView pauseBtn;
    private ImageView stopBtn;


//  Colors for when the tab is selected vs unselected
    private int selectedInt;
    private int notSelectedInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        Get the background colours
        {
            selectedInt = getResources().getColor(R.color.selected);
            notSelectedInt = getResources().getColor(R.color.notSelected);
        }

        final RelativeLayout r1 = (RelativeLayout) findViewById(R.id.procrastinateView);
        final RelativeLayout r2 = (RelativeLayout) findViewById(R.id.studyView);

        playBtn = (ImageView) findViewById(R.id.overlapPlayImg);
        pauseBtn = (ImageView) findViewById(R.id.overlapPauseImg);
        stopBtn = (ImageView) findViewById(R.id.overlapStopImg);

        pChronometer = (Chronometer) findViewById(R.id.proTime);
        sChronometer = (Chronometer) findViewById(R.id.stdTime);

//      If play button is pressed then click
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseBtn.setEnabled(true);
                playBtn.setEnabled(false);
                stopBtn.setEnabled(true);

                playNow();
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseBtn.setEnabled(false);
                playBtn.setEnabled(true);
                stopBtn.setEnabled(true);

                pauseNow();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtn.setEnabled(true);
                pauseBtn.setEnabled(false);
                stopBtn.setEnabled(false);

                resetNow();

            }
        });

//      The procrastinate view
        r1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//              If studying and counting then change side and continue counting
                if(working==1 && counting ==1){
                    pauseNow();
                    working =0;
                    playNow();
                    setColor(r1, r2);
//              If studying and not counting change side
                }else if(working ==1 && counting ==0){
                    working =0;
                    setColor(r1, r2);
                } else{
                    printVal(working, "procrastination working");
                }
            }
        });

//      The study view
        r2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(working==0 && counting ==1){
                    pauseNow();
                    working = 1;
                    playNow();
                    setColor(r1, r2);
                }else if (working ==0 && counting ==0){
                    working =1;
                    setColor(r1, r2);
                } else{
                    printVal(working, "Study working");
                }
            }
        });
    }

    //  Change mode on click
    private void changeMode(){
        if(counting ==1){

        }else if (counting ==0){

        }
    }

    //    Prints the value and message given to it.
    private void printVal(int val, String msg){
        String tmpMsg = msg + " " + val;
        Log.i("MyDebugMsg", tmpMsg);
    }


    //  Set the background color for the selected/nonselected sides
    private void setColor(RelativeLayout r1, RelativeLayout r2){
        if (working == 0){
            r1.setBackgroundColor(selectedInt);
            r2.setBackgroundColor(notSelectedInt);
        } else if(working == 1) {
            r1.setBackgroundColor(notSelectedInt);
            r2.setBackgroundColor(selectedInt);
        }
    }

    //  Reset the timers for both sides deactivate pause and activate play
    private void resetNow(){
                sChronometer.stop();
                sChronometer.setBase(SystemClock.elapsedRealtime());
                sLastPause =0;
                pChronometer.stop();
                pChronometer.setBase(SystemClock.elapsedRealtime());
                pLastPause =0;
                counting = 0;
    }

    //  Pause the timer for the non-selectedInt side
    private void pauseNow(){
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
        counting = 0;
    }

    //  Start the timer for the selectedInt side
    private void playNow(){
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
        counting = 1;
    }
}
