package com.example.pomodorotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.icu.number.NumberFormatter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Timer extends AppCompatActivity {

    TextView timerText;
    Button btnStart, btnQuit;
    Dialog dialog1, dialog2, dialogSuccess, dialogLost;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface MLight = Typeface.createFromAsset(getAssets(),"fonts/MLight.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(),"fonts/MRegular.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(),"fonts/MMedium.ttf");

        timerText = findViewById(R.id.timerText);
        btnStart = findViewById(R.id.btnStart);
        btnQuit = findViewById(R.id.btnQuit);

        btnStart.setTypeface(MMedium);
        btnQuit.setTypeface(MMedium);
        timerText.setTypeface(MRegular);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        dialog1 = new Dialog(this);
        dialog1.setContentView(R.layout.dialog_box_done);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setCancelable(false);
        Button takeBreak = dialog1.findViewById(R.id.takeBreak);
        TextView tvDone = dialog1.findViewById(R.id.tvDone);
        takeBreak.setTypeface(MMedium);
        tvDone.setTypeface(MRegular);

        dialog2 = new Dialog(this);
        dialog2.setContentView(R.layout.dialog_box_done_break);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setCancelable(false);
        Button focus = dialog2.findViewById(R.id.focus);
        TextView tvDoneBreak = dialog2.findViewById(R.id.tvDoneBreak);
        focus.setTypeface(MMedium);
        tvDoneBreak.setTypeface(MRegular);

        dialogSuccess = new Dialog(this);
        dialogSuccess.setContentView(R.layout.dialog_box_success);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        TextView tvSuccess = dialogSuccess.findViewById(R.id.tvSuccess);
        tvSuccess.setTypeface(MRegular);

        dialogLost = new Dialog(this);
        dialogLost.setContentView(R.layout.dialog_box_lost);
        dialogLost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogLost.setCancelable(true);
        TextView tvLost = dialogLost.findViewById(R.id.tvLost);
        tvLost.setTypeface(MRegular);



        final CountDownTimer pomodoro = new CountDownTimer(1500000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                timerText.setText(f.format(min) + ':' + f.format(sec));
            }

            @Override
            public void onFinish() {
                checkCount();

            }
        };

        final CountDownTimer breakPomodoro = new CountDownTimer(300000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                timerText.setText(f.format(min) + ':' + f.format(sec));
            }

            @Override
            public void onFinish() {
                timerText.setText("25:00");
                dialog2.show();
                long[] pattern = {0, 1000, 300};
                vibrator.vibrate(pattern,0);
                palyRingtone();
            }
        };

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomodoro.start();
                btnStart.setVisibility(View.INVISIBLE);
                btnQuit.setVisibility(View.VISIBLE);
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pomodoro.cancel();
                breakPomodoro.cancel();
                timerText.setText("25:00");
                btnQuit.setVisibility(View.INVISIBLE);
                btnStart.setVisibility(View.VISIBLE);
                dialogLost.show();
            }
        });

        takeBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                startBreak();
            }

            private void startBreak() {
                vibrator.cancel();
                breakPomodoro.start();
                ringtone.stop();
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                pomodoro.start();
                vibrator.cancel();
                ringtone.stop();
            }
        });
    }
    Integer count = 0;
    private void checkCount() {
        count += 1;
        if(count == 4){
            dialogSuccess.show();
            timerText.setText("00:00");
            btnQuit.setVisibility(View.INVISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            count = 0;
        }else {
            dialog1.show();
            long[] pattern = {0, 1000, 300};
            vibrator.vibrate(pattern,0);
            palyRingtone();
        }

    }
    Ringtone ringtone;
    public void palyRingtone() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(),uri);
        ringtone.play();
    }

}