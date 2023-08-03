package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Helper.PromoBookAdapter;
import com.example.book_shelf.Helper.PromoPriceAdapter;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PromotionPage extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back;
    TextView shop;
    String type;

    Button btnUpload;

    RecyclerView recyclerView;

    PromoPriceAdapter adapter;

    EditText edtPromoName;

    DBHelper dbHelper = new DBHelper(PromotionPage.this);

    List<BookModel> promotionList = new ArrayList<>();
    List<BookModel> allBookList = new ArrayList<>();
    List<BookModel> uploadList = new ArrayList<>();

    String currentDate = null;

    Animation fade_in,fade_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_page);
        fade_in = AnimationUtils.loadAnimation(PromotionPage.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(PromotionPage.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        edtPromoName = findViewById(R.id.edtPromoName);
        recyclerView = findViewById(R.id.recyclerView);
        btnUpload = findViewById(R.id.btnUpload);

        type = getIntent().getStringExtra("type");
        System.out.println("percent of promotion "+type);

        allBookList = dbHelper.getAllBooks();
        getSelectedBook();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PromoPriceAdapter(this,promotionList);
        recyclerView.setAdapter(adapter);

        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);

        ///Get Current Date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        currentDate = year + "-" + month + "-" + dayOfMonth;

        back.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
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

    private void getSelectedBook(){
        int i=0;
        int percent = Integer.parseInt(type);
        for (BookModel bm : allBookList) {
            boolean isIn = false;
            for (int id : Util.promoList) {
                if (id==bm.getBookId()){
                    isIn = true;
                    double step1Price = (Integer.parseInt(bm.getPrice()) * percent) / 100;
                    int step2Price = (int)Math.round(step1Price);
                    int finalPrice = Integer.parseInt(bm.getPrice())-step2Price;
                    bm.setPromoName(edtPromoName.getText().toString());
                    bm.setPromoPercent(type);
                    bm.setPromoPrice(String.valueOf(finalPrice));
                    promotionList.add(bm);
                    uploadList.add(bm);
                }

            }
            if (isIn!=true){
                uploadList.add(bm);
            }
            System.out.println("all book "+allBookList.get(i)+"   ");
            i++;
        }
    }

    @Override
    public void onClick(View v) {
        int touch = v.getId();

        if (touch==R.id.back){
            Util.promoList.clear();
            finish();
        }else if (touch == R.id.btnUpload){
            String promoName =edtPromoName.getText().toString();
            if (promoName.length()>0){
                for (int i=0;i<uploadList.size();i++){
                    for (int id : Util.promoList) {
                        if (id==uploadList.get(i).getBookId()){
                            uploadList.get(i).setPromoName(promoName);
                        }
                    }
                    System.out.println("upload data "+uploadList.get(i));
                    dbHelper.updateBook(
                            String.valueOf(uploadList.get(i).getBookId()),
                            uploadList.get(i).getTitle(),
                            uploadList.get(i).getAuthor(),
                            uploadList.get(i).getPrice(),
                            uploadList.get(i).getPicture(),
                            uploadList.get(i).getDescription(),
                            uploadList.get(i).getPages(),
                            uploadList.get(i).getType(),
                            uploadList.get(i).getStock(),
                            uploadList.get(i).getPromoPrice(),
                            uploadList.get(i).getPromoPercent(),
                            uploadList.get(i).getPromoName()
                    );
                }
                Util.saveData(this,"promotion",currentDate);
                Util.saveData(this,"promotionBook",String.valueOf(Util.promoList.size()));
                System.out.println("save promo date "+currentDate+"   "+Util.promoList.size());
                startActivity(new Intent(PromotionPage.this,HomePage.class));
            }else{
                Util.showToast(this,"You must fill name of Promotion!");
            }
        }
    }
}