package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class BookDetailPage extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back;
    TextView shop;

    TextView txtIdData,txtTitleData,txtAuthorData,txtPriceData,txtPagesData,txtStockData,txtTypeData,txtDescriptionData;
    ImageView picture;
    Button btnAddToCart;

    String bId = null;
    String sourceBack = null;
    Animation fade_in,fade_out;

    DBHelper dbHelper = new DBHelper(BookDetailPage.this);

    BookModel model = new BookModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_page);
        fade_in = AnimationUtils.loadAnimation(BookDetailPage.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(BookDetailPage.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        btnAddToCart = findViewById(R.id.btnAddToCard);
        picture = findViewById(R.id.picture);
        txtIdData = findViewById(R.id.txtIdData);
        txtTitleData = findViewById(R.id.txtTitleData);
        txtAuthorData = findViewById(R.id.txtAuthorData);
        txtPriceData = findViewById(R.id.txtPriceData);
        txtPagesData = findViewById(R.id.txtPagesData);
        txtStockData = findViewById(R.id.txtStockData);
        txtTypeData = findViewById(R.id.txtTypeData);
        txtDescriptionData = findViewById(R.id.txtDescriptionData);

        bId = getIntent().getStringExtra("bookId");
        sourceBack = getIntent().getStringExtra("source");

        model = dbHelper.getSingleBook(bId);
        bindingData(model);

        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);

        String isAdmin = Util.getData(this,"isAdmin");
        if (isAdmin.equals("true") || model.getStock()==0){
            btnAddToCart.setVisibility(View.GONE);
        }else{
            btnAddToCart.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
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
        private void bindingData(BookModel model){
            txtIdData.setText(String.valueOf(model.getBookId()));
            txtTitleData.setText(model.getTitle());
            txtAuthorData.setText(model.getAuthor());
            txtPriceData.setText(model.getPrice());
            txtPagesData.setText(model.getPages());
            txtStockData.setText(String.valueOf(model.getStock()));
            txtTypeData.setText(model.getType());
            txtDescriptionData.setText(model.getDescription());
            String realFilePath = model.getPicture();
            BitmapFactory.Options opt = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(realFilePath,opt);
            picture.setImageBitmap(bm);
        }

    @Override
    public void onClick(View v) {
        int touch = v.getId();

        if (touch == R.id.back){
            //finish();
            if (sourceBack.equals("home")){
                startActivity(new Intent(BookDetailPage.this, HomePage.class));
            }else{
                Intent i =new Intent(v.getContext(), List_By_Type.class);
                i.putExtra("bookType",model.getType());
                startActivity(i);
            }
        }else if (touch == R.id.btnAddToCard){
            boolean isAlreadyInsert = false;
            for (int type : Util.addToCardList) {
                if (type==model.getBookId()){
                    isAlreadyInsert = true;
                    break;
                }
            }
            if (isAlreadyInsert==true){
                Util.showToast(this,"Already Added to cart");
            }else{
                Util.showToast(this,"Added to cart");
                Util.addToCardList.add(model.getBookId());
               // Util.totalCost = Util.totalCost+Integer.parseInt(model.getPrice());
            }
        }
    }
}