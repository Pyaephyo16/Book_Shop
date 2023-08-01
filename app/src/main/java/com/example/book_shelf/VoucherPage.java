package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Models.OrderDetailModel;
import com.example.book_shelf.Models.OrderModel;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class VoucherPage extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView close;
    TextView here;

    TextView txtOrderId,txtOrderName,txtOrderPhone,txtOrderAddress,txtOrderPrice,txtOrderQty,txtOrderBooks,txtOrderDate;

    Animation fade_in_text,fade_out_text;

    String phone = null;

    DBHelper dbHelper = new DBHelper(VoucherPage.this);

    List<OrderDetailModel> boughtBookList = new ArrayList<>();
    List<OrderModel> orderList = new ArrayList<>();
    OrderModel lastOrderByUser = new OrderModel();
    int lastOrderId = 0;

    String books = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_page);
        fade_in_text = AnimationUtils.loadAnimation(VoucherPage.this,R.anim.fade_in_text);
        fade_out_text = AnimationUtils.loadAnimation(VoucherPage.this,R.anim.fade_out_text);
        fade_in_text.setAnimationListener(this);
        fade_out_text.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        close = findViewById(R.id.close);
        here = findViewById(R.id.here);
        txtOrderId = findViewById(R.id.txtOrderId);
        txtOrderName = findViewById(R.id.txtOrderName);
        txtOrderPhone = findViewById(R.id.txtOrderPhone);
        txtOrderAddress = findViewById(R.id.txtOrderAddress);
        txtOrderPrice = findViewById(R.id.txtOrderPrice);
        txtOrderQty = findViewById(R.id.txtOrderQty);
        txtOrderBooks = findViewById(R.id.txtOrderBooks);
        txtOrderDate = findViewById(R.id.txtOrderDate);

        phone = getIntent().getStringExtra("phone");

            if (phone.length()<5){
                lastOrderByUser = dbHelper.getOrderById(phone);
            }else{
                orderList = dbHelper.getOrderByUser(phone);
                lastOrderByUser = orderList.get(orderList.size()-1);
            }

        lastOrderId = lastOrderByUser.getOrderId();
        boughtBookList = dbHelper.getOrderDetailByUser(lastOrderId);
        books = getAllBooks(boughtBookList);
        bindingData();

        here.startAnimation(fade_in_text);

            close.setOnClickListener(this);
    }

    private void bindingData(){
        txtOrderId.setText(String.valueOf(lastOrderByUser.getOrderId()));
        txtOrderName.setText(lastOrderByUser.getOrderUserName());
        txtOrderPhone.setText(lastOrderByUser.getOderUserPhone());
        txtOrderAddress.setText(lastOrderByUser.getOrderAddress());
        txtOrderPrice.setText(lastOrderByUser.getTotalPrice());
        txtOrderQty.setText(String.valueOf(lastOrderByUser.getTotalQuantity()));
        txtOrderBooks.setText(books);
        txtOrderDate.setText(lastOrderByUser.getOrderDate());
    }

    public static String getAllBooks(List<OrderDetailModel> modelList) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = modelList.size();

        for (int i = 0; i < size; i++) {
            stringBuilder.append(modelList.get(i).getOrderBookNTitle());
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == fade_in_text){
            here.startAnimation(fade_out_text);
        }else if (animation == fade_out_text){
            here.startAnimation(fade_in_text);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        int touch = v.getId();

        if (touch == R.id.close){
               startActivity(new Intent(VoucherPage.this, HomePage.class));

        }
    }
}