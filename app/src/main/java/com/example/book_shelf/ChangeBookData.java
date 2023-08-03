package com.example.book_shelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.book_shelf.Frag.DashboardFrag;
import com.example.book_shelf.Helper.SpinAdapter;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ChangeBookData extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back;
    TextView shop;

    EditText edtTitle,edtAuthor,edtPrice,edtPages,edtStock,edtDescription;

    Spinner typeSpinner;
    Button btnUpdate;
    ImageView picture;

    SpinAdapter typeSpinnerAdapter;

    List<String> typeList;

    boolean isEditModeOn = false;
    String type = null;

    int CODE  = 222;
    Uri filePath = null;
    String realFilePath = null;

    Animation fade_in,fade_out;

    DBHelper dbHelper = new DBHelper(ChangeBookData.this);
    BookModel model = new BookModel();

    String bId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_book_data);

        fade_in = AnimationUtils.loadAnimation(ChangeBookData.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(ChangeBookData.this,R.anim.fade_out);
        fade_out.setAnimationListener(this);
        fade_in.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        back = findViewById(R.id.back);
        shop = findViewById(R.id.shop);
        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtPrice = findViewById(R.id.edtPrice);
        edtPages = findViewById(R.id.edtPages);
        edtStock = findViewById(R.id.edtStock);
        edtDescription = findViewById(R.id.edtDescrption);
        typeSpinner = findViewById(R.id.typeSpinner);
        btnUpdate = findViewById(R.id.btnUpdate);
        picture = findViewById(R.id.picture);

        bId = getIntent().getStringExtra("bId");

        model = dbHelper.getSingleBook(bId);
        bindingData(model);
        editModeOn();


        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);

        back.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        picture.setOnClickListener(this);

        prepareForType();
        typeSpinner.setAdapter(typeSpinnerAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(isEditModeOn==false){
                        adapterView.setSelection(chooseType(model.getType()));
                    }
                    type = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public int chooseType(String data){
        if(data.equals("Novels")){
            return 1;
        }else if(data.equals("Picture Books")){
            return 2;
        }else if(data.equals("Comics")){
            return 3;
        }else if(data.equals("DC Comics")){
            return 4;
        } else if (data.equals("Marvel Comics")) {
            return 5;
        } else if (data.equals("Manga")) {
            return 6;
        } else if (data.equals("Horror")) {
            return 7;
        } else if (data.equals("Fantasy")) {
            return 8;
        } else if (data.equals("Advice for a better life")) {
            return 9;
        } else if (data.equals("Romance")) {
            return 10;
        } else if (data.equals("Stories to save the world")) {
            return 11;
        }else{
            return 12;
        }
    }

    private void bindingData(BookModel model){
        edtTitle.setText(model.getTitle());
        edtAuthor.setText(model.getAuthor());
        edtPrice.setText(model.getPrice());
        edtPages.setText(model.getPages());
        edtStock.setText(String.valueOf(model.getStock()));
        edtDescription.setText(model.getDescription());
        realFilePath = model.getPicture();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(realFilePath,opt);
        picture.setImageBitmap(bm);
    }

    private void editModeOn(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               isEditModeOn = true;
            }
        }, 2000);
    }

    public void prepareForType(){
        typeList = new ArrayList<>();
        typeList.add("Select Book Type");
        typeList.add("Novels");
        typeList.add("Picture Books");
        typeList.add("Comics");
        typeList.add("DC Comics");
        typeList.add("Marvel Comics");
        typeList.add("Manga");
        typeList.add("Horror");
        typeList.add("Fantasy");
        typeList.add("Advice for a better life");
        typeList.add("Romance");
        typeList.add("Stories to save the world");
        typeList.add("New Your Times bestsellers");
        typeSpinnerAdapter = new SpinAdapter(ChangeBookData.this, android.R.layout.simple_spinner_item,typeList);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation==fade_in){
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

        if (touch == R.id. back){
            finish();
        }else if (touch == R.id.btnUpdate){
            updateBook();
        }else if (touch == R.id.picture){
            requestForPermission();
        }
    }

    private void updateBook(){
        String title = edtTitle.getText().toString();
        String author = edtAuthor.getText().toString();
        String price = edtPrice.getText().toString();
        String pages = edtPages.getText().toString();
        String description = edtDescription.getText().toString();


        int iTitle = edtTitle.getText().length();
        int iAuthor = edtAuthor.getText().length();
        int iPrice = edtPrice.getText().length();
        int iPages = edtPages.getText().length();
        int iDescription = edtDescription.getText().length();

        if (iTitle >0 && iAuthor >0 && iPrice > 0 && iPages >0 && type!=null && iDescription > 0){
            if (edtStock.getText().length() > 0){
                int stock = Integer.parseInt(edtStock.getText().toString());
                if (realFilePath!=null){
                    System.out.println("update book ==========="+realFilePath+"   "+title+"  "+author+"  "+price+"  "+pages+"  "+stock+"  "+type+"  "+description);
                    dbHelper.updateBook(
                            String.valueOf(model.getBookId()),
                            title,
                            author,
                            price,
                            realFilePath,
                            description,
                            pages,
                            type,
                            stock,
                            model.getPromoPrice(),
                            model.getPromoPercent(),
                            model.getPromoName()
                    );
                    startActivity(new Intent(ChangeBookData.this, HomePage.class));
                }else{
                    Util.showToast(this,"You must add cover page of the book!");
                }
            }else{
                Util.showToast(this,"Number of stock must have at least 1!");
            }
        }else{
            Util.showToast(this,"You must fill all fields!");
        }
    }

    private void requestForPermission(){
        ActivityCompat.requestPermissions(ChangeBookData.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(Intent.createChooser(intent,"Pick an image"),CODE);
            }else{
                Util.showToast(ChangeBookData.this,"Need Permission to pick photo");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODE && resultCode == RESULT_OK && data!=null){

            filePath = data.getData();
            System.out.println("profile file path "+filePath.toString());
            System.out.println("real path "+getRealPathFromURI(ChangeBookData.this,filePath));

            realFilePath = getRealPathFromURI(ChangeBookData.this,filePath);

            BitmapFactory.Options opt = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(realFilePath,opt);

            picture.setImageBitmap(bm);



        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI(Context context, Uri contentUri){
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String realPath = cursor.getString(column_index);
        cursor.close();
        return realPath;
    }
}