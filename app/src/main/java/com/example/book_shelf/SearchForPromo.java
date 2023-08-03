package com.example.book_shelf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.book_shelf.Helper.BookAdapter;
import com.example.book_shelf.Helper.PromoBookAdapter;
import com.example.book_shelf.Helper.SpinAdapter;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchForPromo extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back;
    TextView shop;

    SearchView searchView;
    RecyclerView recyclerView;
    PromoBookAdapter adapter;

    Button btnNext;
    Spinner typeSpinner;
    SpinAdapter typeSpinnerAdapter;
    List<String> typeList;
    String type = null;
    String promoDate = "";
    List<BookModel> bookList = new ArrayList<>();
    Animation fade_in,fade_out;

    DBHelper dbHelper = new DBHelper(SearchForPromo.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_promo);
        fade_in = AnimationUtils.loadAnimation(SearchForPromo.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(SearchForPromo.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        btnNext = findViewById(R.id.btnNext);
        typeSpinner = findViewById(R.id.typeSpinner);

        bookList = dbHelper.getAllBooks();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PromoBookAdapter(this,bookList);
        recyclerView.setAdapter(adapter);

        prepareForType();
        typeSpinner.setAdapter(typeSpinnerAdapter);

        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);

        promoDate = Util.getData(this,"promotion");
        System.out.println("search for promo promodate"+promoDate);
        if (promoDate.equals("") || promoDate.equals("null")){
            btnNext.setText("Next");
        }else{
            btnNext.setText("END PROMO");
            btnNext.setTextSize(10);
            btnNext.setBackgroundColor(Color.RED);
        }

        back.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    type = adapterView.getSelectedItem().toString();
                }else{
                    type = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void prepareForType(){
        typeList = new ArrayList<>();
        typeList.add("Select Percent %");
        typeList.add("20");
        typeList.add("30");
        typeList.add("40");
        typeList.add("50");
        typeList.add("60");
        typeList.add("70");
        typeList.add("80");
        typeSpinnerAdapter = new SpinAdapter(SearchForPromo.this, android.R.layout.simple_spinner_item,typeList);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == fade_in){
            logo.startAnimation(fade_out);
            shop.startAnimation(fade_out);
        }else if (animation == fade_out){
            logo.startAnimation(fade_in);
            shop.startAnimation(fade_in);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        int touch = v.getId();
        if (touch == R.id.back){
            finish();
        }else if (touch == R.id.btnNext){
            if (promoDate==null  || promoDate.equals("null")) {
                if (Util.promoList.size() > 0) {
                    if (type != null) {
                        Intent intent = new Intent(this, PromotionPage.class);
                        intent.putExtra("type", type);
                        startActivity(intent);
                    } else {
                        Util.showToast(this, "You must select Percent of promotion for books!");
                    }
                } else {
                    Util.showToast(this, "There is no promotion list selection!");
                }
            }else{
                promoEndDialog();
            }
        }
    }

    private void promoEndDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.promotion_delete_dialog,null);
        builder.setView(view);

        TextView promoStartDate,promoBook,btnCancel,btnEndPromo;

        promoStartDate = view.findViewById(R.id.promoStartDate);
        promoBook = view.findViewById(R.id.promoBook);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnEndPromo = view.findViewById(R.id.btnEndPromo);


        promoStartDate.setText(promoDate);
        String count = Util.getData(this,"promotionBook");
        promoBook.setText(count);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnEndPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endPromotion();
                dialog.cancel();
                startActivity(new Intent(SearchForPromo.this, HomePage.class));
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

    private void endPromotion(){
        int i = 0;
        for (BookModel bm : bookList) {
            bm.setPromoName("");
            bm.setPromoPrice("0");
            bm.setPromoPercent("0");
            System.out.println("end promo ==== "+bookList.get(i));
            i++;
        }

        for (int p=0;p<bookList.size();p++){
            dbHelper.updateBook(
                    String.valueOf(bookList.get(p).getBookId()),
                    bookList.get(p).getTitle(),
                    bookList.get(p).getAuthor(),
                    bookList.get(p).getPrice(),
                    bookList.get(p).getPicture(),
                    bookList.get(p).getDescription(),
                    bookList.get(p).getPages(),
                    bookList.get(p).getType(),
                    bookList.get(p).getStock(),
                    "0",
                    "0",
                    ""

            );
        }
        Util.saveData(this,"promotion",null);
        Util.saveData(this,"promotionBook",null);
        promoDate = null;

    }
}