package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Helper.BookAdapter;
import com.example.book_shelf.Helper.BookRowAdapter;
import com.example.book_shelf.Helper.TypeBookAdapter;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class List_By_Type extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    TextView typeName;
    ImageView logo,back;
    TextView shop;
    RecyclerView typeRecycler;

    TypeBookAdapter typeBookAdapter;
    public String bookType = null;

    Animation fade_in,fade_out;

    String promoDate = "";

    DBHelper dbHelper = new DBHelper(List_By_Type.this);
    List<BookModel> allBookList = new ArrayList<>();
    List<BookModel> bookByTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_by_type);
        fade_in = AnimationUtils.loadAnimation(List_By_Type.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(List_By_Type.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        typeName = findViewById(R.id.typeName);
        typeRecycler = findViewById(R.id.typeRecycler);

        bookType = getIntent().getStringExtra("bookType");
        typeName.setText(bookType);

        promoDate = Util.getData(this,"promotion");

        allBookList = dbHelper.getAllBooks();
        System.out.println("list by type "+promoDate);
        if (bookType.equals("Novels") || bookType.equals("Picture Books") || bookType.equals("Comics") || bookType.equals("DC Comics") || bookType.equals("Marvel Comics") || bookType.equals("Manga") || bookType.equals("Horror") || bookType.equals("Fantasy") || bookType.equals("Advice for a better life") || bookType.equals("Romance") || bookType.equals("Stories to save the world") || bookType.equals("New Your Times bestsellers")) {
            divdeBranch(allBookList);
        }else{
            getPromoBookList(allBookList);
        }


        typeRecycler.setHasFixedSize(true);
        typeRecycler.setLayoutManager(new GridLayoutManager(this,2));

        typeBookAdapter = new TypeBookAdapter(this,bookByTypeList);
        typeRecycler.setAdapter(typeBookAdapter);

        back.setOnClickListener(this);

        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);
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
           // finish();
            startActivity(new Intent(List_By_Type.this, HomePage.class));
        }
    }

    private void divdeBranch(List<BookModel> allBooks){
        for (BookModel bm : allBooks) {
            if (bm.getType().equals(bookType)){
                bookByTypeList.add(bm);
            }
        }
    }

    private void getPromoBookList(List<BookModel> allBooks){
        for (BookModel bm : allBooks) {
            if (!bm.getPromoPrice().equals("0")){
                bookByTypeList.add(bm);
            }
        }
    }

}