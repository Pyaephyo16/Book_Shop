package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.Intent;
import android.os.Bundle;

import com.example.book_shelf.Util.Util;

public class MainActivity extends AppCompatActivity implements MotionLayout.TransitionListener {

    MotionLayout motionLayout;

    String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI(){
        motionLayout = findViewById(R.id.motionLayout);
        motionLayout.setTransitionListener(this);

        phone = Util.getData(MainActivity.this,"phone");
    }

    @Override
    public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

    }

    @Override
    public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

    }

    @Override
    public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {
        if(phone.equals("null") || phone.equals(null) || phone==null){
            startActivity(new Intent(MainActivity.this, Login.class));
        }else{
            startActivity(new Intent(MainActivity.this, HomePage.class));
        }
    }

    @Override
    public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

    }
}