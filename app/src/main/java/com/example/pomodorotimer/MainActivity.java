package com.example.pomodorotimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;

public class MainActivity extends AppCompatActivity {

    TextView tv, tvSub;
    Button btnGetStarted, btnWhatIsPomodoro;
    Dialog dialog3;
    View lottie;
    Animation agt, btgOne, btgTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        agt = AnimationUtils.loadAnimation(this, R.anim.agt);
        btgOne = AnimationUtils.loadAnimation(this, R.anim.btg_one);
        btgTwo = AnimationUtils.loadAnimation(this, R.anim.btg_two);


        tv = findViewById(R.id.tv);
        tvSub = findViewById(R.id.tvSub);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        btnWhatIsPomodoro = findViewById(R.id.btnWhatIsPomodoro);

        //set animation
        lottie = findViewById(R.id.lottieAnimationView);
        lottie.startAnimation(agt);
        tv.startAnimation(btgOne);
        tvSub.startAnimation(btgOne);
        btnGetStarted.startAnimation(btgTwo);
        btnWhatIsPomodoro.startAnimation(btgTwo);

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/MLight.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(), "fonts/MRegular.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");

        tv.setTypeface(MRegular);
        tvSub.setTypeface(MLight);
        btnGetStarted.setTypeface(MMedium);
        btnWhatIsPomodoro.setTypeface(MMedium);

        dialog3 = new Dialog(this);
        dialog3.setContentView(R.layout.dialog_box_pomodoro);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog3.setCancelable(true);


        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Timer.class);
                startActivity(intent);
            }
        });

        btnWhatIsPomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.show();
            }
        });
    }
}