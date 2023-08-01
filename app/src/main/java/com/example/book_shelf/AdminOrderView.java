package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderView extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back;
    TextView shop;

    RecyclerView recyclerView;
    Animation fade_in,fade_out;

    OrderCardAdapter adapter;

    DBHelper dbHelper = new DBHelper(AdminOrderView.this);

    List<OrderModel> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_order_view);
        fade_in = AnimationUtils.loadAnimation(AdminOrderView.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(AdminOrderView.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        recyclerView = findViewById(R.id.recyclerView);

        orderList = dbHelper.getAllOrders();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OrderCardAdapter(this,orderList);
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

        if (touch == R.id.back) {
            finish();
        }
    }
}