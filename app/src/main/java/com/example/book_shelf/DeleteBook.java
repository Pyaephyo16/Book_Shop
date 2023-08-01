package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.book_shelf.Frag.DashboardFrag;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.db.DBHelper;

public class DeleteBook extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    ImageView logo,back;
    TextView shop;

    TextView txtIdData,txtTitleData,txtAuthorData,txtPriceData,txtPagesData,txtStockData,txtTypeData,txtDescriptionData;
    ImageView picture;
    Button btnDelete;

    String bId = null;
    Animation fade_in,fade_out;

    DBHelper dbHelper = new DBHelper(DeleteBook.this);
    BookModel model = new BookModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_book);
        fade_in = AnimationUtils.loadAnimation(DeleteBook.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(DeleteBook.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        btnDelete = findViewById(R.id.btnDelete);
        picture = findViewById(R.id.picture);
        txtIdData = findViewById(R.id.txtIdData);
        txtTitleData = findViewById(R.id.txtTitleData);
        txtAuthorData = findViewById(R.id.txtAuthorData);
        txtPriceData = findViewById(R.id.txtPriceData);
        txtPagesData = findViewById(R.id.txtPagesData);
        txtStockData = findViewById(R.id.txtStockData);
        txtTypeData = findViewById(R.id.txtTypeData);
        txtDescriptionData = findViewById(R.id.txtDescriptionData);

        bId = getIntent().getStringExtra("bId");

        model = dbHelper.getSingleBook(bId);
        bindingData(model);

        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);

        back.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int touch = v.getId();

        if (touch == R.id.back){
            finish();
        }else if (touch == R.id.btnDelete){
            warningDialog(this);
        }
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

    private void warningDialog(Context context){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deletePost();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.setTitle("DELETE POST!");
            dialog.setMessage("Are you sure want to delete?");
            dialog.show();

    }

    private void deletePost(){
            dbHelper.deleteBook(bId);
        startActivity(new Intent(DeleteBook.this, HomePage.class));

    }
}