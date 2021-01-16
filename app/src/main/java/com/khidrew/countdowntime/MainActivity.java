package com.khidrew.countdowntime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 100000;
    private Button mButtonStartPause;
    private Button mButtonReset ;
    private TextView mTextViewCountDown ;

    private CountDownTimer mCountDownTime ;
    private boolean mTimeRunning ;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonReset = (Button) findViewById(R.id.btn_reset);
        mButtonStartPause = (Button) findViewById(R.id.btn_start_pause);
        mTextViewCountDown = (TextView) findViewById(R.id.tv_timer_text);

        setupTimer(START_TIME_IN_MILLIS);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTimeRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

    }

    // Initial state
    private void setupTimer(long duration) {
        updateCountDownText();
        mCountDownTime = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished ;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
               resetTimer();
            }
        };
    }

    private void startTimer(){
        setupTimer(mTimeLeftInMillis);
        mCountDownTime.start();
        mTimeRunning = true ;
        updateCountDownText();
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        mCountDownTime.cancel();
        mTimeRunning = false ;
        updateCountDownText();
        mButtonStartPause.setText("Resume");
        mButtonReset.setVisibility(View.VISIBLE);
    }


    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mTimeRunning = false;
        updateCountDownText();
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60 ;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);

    }
}