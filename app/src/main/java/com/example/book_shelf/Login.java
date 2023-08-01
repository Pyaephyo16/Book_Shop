package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.book_shelf.Helper.MyFragmentAdapter;
import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.db.DBHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements Animation.AnimationListener {

    ImageView imgLogin;

    Animation zoom_in,zoom_out;
    TabLayout tb;
    ViewPager vPager;

    String[] arr = {"Phone","Email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        zoom_in = AnimationUtils.loadAnimation(Login.this,R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(Login.this,R.anim.zoom_out);
        zoom_in.setAnimationListener(this);
        zoom_out.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){

        imgLogin = findViewById(R.id.imgLogin);
        tb = findViewById(R.id.tb);
        vPager = findViewById(R.id.vPager);

        imgLogin.startAnimation(zoom_in);

        FragmentPagerAdapter adapter =new MyFragmentAdapter(getSupportFragmentManager(),arr);
        vPager.setAdapter(adapter);

        tb.setupWithViewPager(vPager);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==zoom_in){
            imgLogin.startAnimation(zoom_out);
        }else{
            imgLogin.startAnimation(zoom_in);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}