package com.example.book_shelf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Helper.AddToCartAdapter;
import com.example.book_shelf.Helper.BookAdapter;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Models.OrderModel;
import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddToCartPage extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back;
    TextView shop,totalCost,fillAddress;
    RecyclerView addToCartRecycler;
    AddToCartAdapter adapter;

    Button btnBuy;
    String currentDate = "";

    List<BookModel> allBookList = new ArrayList<>();
    List<BookModel> selectedBookList = new ArrayList<>();

    UserModel user;
    Animation fade_in,fade_out;

    String address = null;
    int totalQty = 0;
    int totalOwnByUser = 0;

    DBHelper dbHelper = new DBHelper(AddToCartPage.this);

    List<OrderModel> currentUserOrderList = new ArrayList<>();
    int currentUserLastOrderId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_cart_page);
        fade_in = AnimationUtils.loadAnimation(AddToCartPage.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(AddToCartPage.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        addToCartRecycler = findViewById(R.id.addToCartRecycler);
        totalCost = findViewById(R.id.totalCost);
        btnBuy = findViewById(R.id.btnBuy);
        fillAddress = findViewById(R.id.fillAddress);

        String phone = Util.getData(this,"phone");
        user = dbHelper.getUserDetail(phone);


        ///Get Current Date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        currentDate = year + "-" + month + "-" + dayOfMonth;



        allBookList = dbHelper.getAllBooks();
        getSelectedBook();


            addToCartRecycler.setHasFixedSize(true);
            addToCartRecycler.setLayoutManager(new LinearLayoutManager(this));

            adapter = new AddToCartAdapter(this,selectedBookList);
            addToCartRecycler.setAdapter(adapter);
        updatePrice("0",0);


        back.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        fillAddress.setOnClickListener(this);

        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);
    }

    ///0 remove
    ///1 increase
    ///2 decrease
    public void updatePrice(String current,int placec){
        String price = "0";
        if (placec == 0){
             price = adapter.bookPrice(current);
        }else if (placec == 1){
             price = adapter.increaseBookPrice(current);
        }else if (placec ==2){
            price = adapter.bookPrice(current);
        }
        totalCost.setText(price);
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
            //finish();
            Util.totalCost=0;
            startActivity(new Intent(AddToCartPage.this, HomePage.class));
        }else if (touch == R.id.btnBuy){
            if (address!=null && address.length()>0){
                System.out.println("order name "+user.getName());
                System.out.println("order phone "+user.getPhone());
                System.out.println("order total cost "+Util.totalCost);
                System.out.println("order date "+currentDate);
                System.out.println("order address "+address);
                for (int j=0;j<selectedBookList.size();j++){
                    System.out.println("order book "+j+ "   "+selectedBookList.get(j));
                    totalQty+=selectedBookList.get(j).getCurrentTake();
                }
                for (int j=0;j<Util.addToCardList.size();j++){
                    System.out.println("order add to cart "+j+ "   "+Util.addToCardList.get(j));
                }
                System.out.println("order total qty "+totalQty);

                dbHelper.addOrder(user.getName(),user.getPhone(),String.valueOf(Util.totalCost),totalQty,currentDate,address);

                currentUserOrderList = dbHelper.getOrderByUser(user.getPhone());
                currentUserLastOrderId = currentUserOrderList.get(currentUserOrderList.size()-1).getOrderId();

                totalOwnByUser = user.getOwnBook()+totalQty;
                dbHelper.updateUser(user.getName(),user.getPhone(),user.getEmail(),user.getPassword(),user.getProfile(),totalOwnByUser);

                for (int p=0;p<selectedBookList.size();p++){
                    dbHelper.addOrderDetail(currentUserLastOrderId,selectedBookList.get(p).getBookId(),selectedBookList.get(p).getCurrentTake(),selectedBookList.get(p).getTitle());
                    dbHelper.addUserShelf(user.getPhone(),user.getName(),selectedBookList.get(p).getBookId(),selectedBookList.get(p).getTitle(),selectedBookList.get(p).getAuthor(),selectedBookList.get(p).getPrice(),selectedBookList.get(p).getPicture(),selectedBookList.get(p).getDescription(),selectedBookList.get(p).getPages(),selectedBookList.get(p).getType(),currentDate);
                    int updateStock = selectedBookList.get(p).getStock() - selectedBookList.get(p).getCurrentTake();
                    dbHelper.updateBook(String.valueOf(selectedBookList.get(p).getBookId()),selectedBookList.get(p).getTitle(),selectedBookList.get(p).getAuthor(),selectedBookList.get(p).getPrice(),selectedBookList.get(p).getPicture(),selectedBookList.get(p).getDescription(),selectedBookList.get(p).getPages(),selectedBookList.get(p).getType(),updateStock,selectedBookList.get(p).getPromoPrice(),selectedBookList.get(p).getPromoPercent(),selectedBookList.get(p).getPromoName());
                }

                Intent intent = new Intent(this, VoucherPage.class);
                intent.putExtra("phone",user.getPhone());
                startActivity(intent);
            }else{
                addressDialog();
            }
        } else if (touch == R.id.fillAddress) {
            addressDialog();
        }
    }

    private void addressDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.address_dialog,null);
        builder.setView(view);

        EditText edtAddress;
        TextView btnConfirm;

      edtAddress = view.findViewById(R.id.edtAddress);
      btnConfirm = view.findViewById(R.id.btnConfirm);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = edtAddress.getText().toString();
                dialog.cancel();
            }
        });

        builder.setPositiveButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


    }



    private void getSelectedBook(){
        for (BookModel bm : allBookList) {
            for (int id : Util.addToCardList) {
                if (id==bm.getBookId()){
                    selectedBookList.add(bm);
                }
            }
        }
    }

    }