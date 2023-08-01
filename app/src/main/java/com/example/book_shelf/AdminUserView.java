package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Helper.OrderCardAdapter;
import com.example.book_shelf.Helper.UserCardAdapter;
import com.example.book_shelf.Models.OrderModel;
import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminUserView extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back;
    TextView shop;

    RecyclerView recyclerView;

    UserCardAdapter adapter;

    DBHelper dbHelper = new DBHelper(AdminUserView.this);
    Animation fade_in,fade_out;

    List<UserModel> userList = new ArrayList<>();
    List<OrderModel> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_view);
        fade_in = AnimationUtils.loadAnimation(AdminUserView.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(AdminUserView.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        recyclerView = findViewById(R.id.recyclerView);

        userList = dbHelper.getAllUsers();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        adapter = new UserCardAdapter(this,userList);
        recyclerView.setAdapter(adapter);

        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);

        back.setOnClickListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == fade_in){
            logo.startAnimation(fade_out);
            shop.startAnimation(fade_out);
        }else if (animation == fade_out){
            logo.startAnimation(fade_in);
            shop.startAnimation(fade_in);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        int touch = v.getId();

        if (touch == R.id.back){
            finish();
        }
    }
}