package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Util.Util;

import java.util.Random;

public class Otp extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView imgOtp;
    TextView txtTime,txtResend;
    EditText one,two,three,four,five,six;
    Button btnSubmit;

    String otpValue = null;

    boolean isCount = true;
    boolean notActiveTryCount = true;

    long[] ptn = {100,2000};

    public static final long START_TIME_IN_MILLIS = 60000;
    public static final long START_TIME_IN_MILLIS_ABANDON = 300000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long abandom = START_TIME_IN_MILLIS_ABANDON;
    private CountDownTimer mCountDownTimer;
    Animation zoom_in,zoom_out;

    String inputPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        zoom_in = AnimationUtils.loadAnimation(Otp.this,R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(Otp.this,R.anim.zoom_out);
        zoom_in.setAnimationListener(this);
        zoom_out.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        imgOtp = findViewById(R.id.imgOtp);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        txtResend = findViewById(R.id.txtResend);
        txtTime = findViewById(R.id.txtTime);
        btnSubmit = findViewById(R.id.btnSubmit);

        inputPhone = getIntent().getStringExtra("inputPhone");
        System.out.println("otp phone "+inputPhone);

        txtResend.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        imgOtp.startAnimation(zoom_in);

        startTimer();
        otpValue = generateOTP();
        sendNoti();
        System.out.println("OTP value "+otpValue);

        one.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_DEL || i == KeyEvent.KEYCODE_BACK){
                    one.requestFocus();
                }else if(one.getText().length()==1){
                    two.requestFocus();
                }
                return false;
            }
        });
        two.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_DEL || i == KeyEvent.KEYCODE_BACK){
                    two.requestFocus();
                }else if(two.getText().length()==1){
                    three.requestFocus();
                }
                return false;
            }
        });
        three.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_DEL || i == KeyEvent.KEYCODE_BACK){
                    three.requestFocus();
                }else if(three.getText().length()==1){
                    four.requestFocus();
                }
                return false;
            }
        });
        four.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_DEL || i == KeyEvent.KEYCODE_BACK){
                    four.requestFocus();
                }else if (four.getText().length()==1){
                    five.requestFocus();
                }
                return false;
            }
        });
        five.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_DEL || i == KeyEvent.KEYCODE_BACK){
                    five.requestFocus();
                }else if (five.getText().length()==1){
                    six.requestFocus();
                }
                return false;
            }
        });
        six.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_DEL || i == KeyEvent.KEYCODE_BACK){
                    six.requestFocus();
                }else if(six.getText().length()==1){
                    btnSubmit.requestFocus();
                    six.clearFocus();
                }
                return false;
            }
        });

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==zoom_in){
            imgOtp.startAnimation(zoom_out);
        }else{
            imgOtp.startAnimation(zoom_in);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        int checkTouch = v.getId();
        if(checkTouch == R.id.txtResend){
            if(Util.tryCount < 5){
                txtTime.setTextColor(Color.parseColor("#7B8CAE"));
                reset();
            }else{
                resetAbandon();
            }
        }else if(checkTouch == R.id.btnSubmit){
            validate();
        }
    }

    public void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void abandomTime(){
        mCountDownTimer = new CountDownTimer(abandom,1000) {
            @Override
            public void onTick(long l) {
                abandom = l;
                abandonCountdown();
            }

            @Override
            public void onFinish() {
                    Util.tryCount = 0;
            }
        }.start();
    }
    private void abandonCountdown(){
        int minute = (int) ( abandom / 1000 ) / 60;
        int second = (int) ( abandom / 1000 ) % 60;

        String timeLeftFormatted = String.format("%2d",minute)+" : "+String.format("%2d",second);
        txtTime.setTextColor(Color.RED);
        txtTime.setText(timeLeftFormatted);
        if(second == 0){
            isCount = false;
            otpValue = null;
        }

    }
    private void resetAbandon(){
        if (isCount == false){
            mTimeLeftInMillis = START_TIME_IN_MILLIS_ABANDON;
            //updateCountDownText();
            abandomTime();
            isCount = true;
            //otpValue = generateOTP();
            //sendNoti();
            //System.out.println("resend otp value "+otpValue);
        }
    }
    private void updateCountDownText(){
        int minute = (int) ( mTimeLeftInMillis / 1000 ) / 60;
        int second = (int) ( mTimeLeftInMillis / 1000 ) % 60;

        String timeLeftFormatted = String.format("%2d",minute)+" : "+String.format("%2d",second);
        txtTime.setText(timeLeftFormatted);
        if(second == 0){
            isCount = false;
            otpValue = null;
        }

    }


    private void validate(){
        if(checkFields()){
            String data = one.getText().toString()+two.getText().toString()+three.getText().toString()+four.getText().toString()+five.getText().toString()+six.getText().toString();
            System.out.println("input otp "+data);
            if(isCount == true && otpValue != null){

                if(otpValue.equals(data)){

                    Intent i = new Intent(Otp.this,ForgetPassword.class);
                    i.putExtra("inputPhone",inputPhone);
                    startActivity(i);

                }else{
                    alert("Invalid Code","You OTP code is wrong");
                    one.setText("");
                    two.setText("");
                    three.setText("");
                    four.setText("");
                    five.setText("");
                    six.setText("");
                    one.requestFocus();
                }

            }else{
                alert("Expire Code","Your OTP code is expired");
            }

        }else{
            alert("Required","You need to fill All digits");
        }
    }

    private void sendNoti(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("otp","OTP Verification",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(Otp.this,"otp")
                    .setSmallIcon(R.drawable.l2)
                    .setContentTitle("OTP Verification")
                    .setContentText("Your OTP code is "+otpValue)
                    .setChannelId("otp");

            channel.enableVibration(true);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(true);
            channel.setVibrationPattern(ptn);

            manager.createNotificationChannel(channel);
            Notification noti = builder.build();
            manager.notify(1,noti);
        }else{
            NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(Otp.this)
                    .setContentTitle("OTP Verification")
                    .setContentText("Your OTP code is "+otpValue)
                    .setSmallIcon(R.drawable.l2);

            builder.setVibrate(ptn);
            manager.notify(1,builder.build());
        }
    }

    private void reset(){
        if (isCount == false){
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            //updateCountDownText();
            startTimer();
            Util.tryCount++;
            isCount = true;
            otpValue = generateOTP();
            sendNoti();
            System.out.println("resend otp value "+otpValue);
        }
    }

    private boolean checkFields(){
        boolean allFill = false;

        int t1 = one.getText().length();
        int t2 = two.getText().length();
        int t3 = three.getText().length();
        int t4 = four.getText().length();
        int t5 = five.getText().length();
        int t6 = six.getText().length();

        if (t1>0 && t2>0 && t3>0 && t4>0 && t5>0 && t6>0){
            allFill = true;
        }
        return allFill;
    }

    public static String generateOTP() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }

    private void alert(String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(Otp.this);
        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dia = builder.create();
        dia.setTitle(title);
        dia.setMessage(msg);
        dia.show();

    }

}