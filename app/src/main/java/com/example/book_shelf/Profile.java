package com.example.book_shelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

public class Profile extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView logo,back,profile;
    TextView shop,edit;

    EditText edtName,edtPhone,edtEmail;

    Button btnChangePassword,btnSubmit;

    Animation fade_in,fade_out;

    DBHelper dbHelper = new DBHelper(Profile.this);
    UserModel user;

    int CODE = 111;
    Drawable custom_textfield,disable_textfield;

    boolean isEditModeOn = false;
    String loginPhone = null;
    Uri filePath = null;
    String realFilePath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        fade_in = AnimationUtils.loadAnimation(Profile.this,R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(Profile.this,R.anim.fade_out);
        fade_in.setAnimationListener(this);
        fade_out.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        logo = findViewById(R.id.logo);
        shop = findViewById(R.id.shop);
        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        profile = findViewById(R.id.profile);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnSubmit = findViewById(R.id.btnSubmit);

        custom_textfield = ResourcesCompat.getDrawable(getResources(),
                R.drawable.custom_textfield, null);
        disable_textfield = ResourcesCompat.getDrawable(getResources(),
                R.drawable.disable_textfield, null);


        logo.startAnimation(fade_in);
        shop.startAnimation(fade_in);

        loginPhone = getIntent().getStringExtra("phone");
        realFilePath = Util.getData(Profile.this,"profile");

        user = dbHelper.getUserDetail(loginPhone);

        bindingLoginData();

        back.setOnClickListener(this);
        edit.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        profile.setOnClickListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == fade_in){
            logo.startAnimation(fade_out);
            shop.startAnimation(fade_out);
        }else if(animation == fade_out){
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
        }else if (touch == R.id.edit){
            isEditModeOn = true;
            activeEditMode();
        }else if (touch == R.id.btnChangePassword){
            Intent i = new Intent(Profile.this,Otp.class);
            i.putExtra("inputPhone",user.getPhone());
            startActivity(i);
        }else if (touch == R.id.btnSubmit){
            updateProfile();
        }else if (touch == R.id.profile){
            if (isEditModeOn == true) {
                requestForPermission();
            }
        }
    }

    private void bindingLoginData(){
        edtName.setText(user.getName());
        edtPhone.setText(user.getPhone());
        edtEmail.setText(user.getEmail());
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(user.getProfile(),opt);
        profile.setImageBitmap(bm);
    }

    private void activeEditMode(){
        edtName.setEnabled(true);
        edtName.setBackground(custom_textfield);
        edtPhone.setEnabled(false);
        edtPhone.setBackground(disable_textfield);
        edtEmail.setEnabled(false);
        edtEmail.setBackground(disable_textfield);
    }

    private void updateProfile(){
        int one = edtName.getText().length();

        String name = edtName.getText().toString();

        if (one > 0){

            dbHelper.updateUser(
                name,
                user.getPhone(),
                user.getEmail(),
                user.getPassword(),
                realFilePath,
                    user.getOwnBook()
            );
            Util.saveData(this,"phone",user.getPhone());
            Util.saveData(this,"name",name);
            Util.saveData(this,"email",user.getEmail());
            Util.saveData(this,"profile",realFilePath);
            Util.saveData(this,"isAdmin","false");
            startActivity(new Intent(Profile.this,HomePage.class));
        }else{
            Util.showToast(this,"User name must fill!");
        }
    }

    private void requestForPermission(){
        ActivityCompat.requestPermissions(Profile.this,
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
                Util.showToast(Profile.this,"Need Permission to pick photo");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODE && resultCode == RESULT_OK && data!=null){

            filePath = data.getData();
            System.out.println("profile file path "+filePath.toString());
            System.out.println("real path "+getRealPathFromURI(Profile.this,filePath));

            realFilePath = getRealPathFromURI(Profile.this,filePath);

            BitmapFactory.Options opt = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(realFilePath,opt);

            profile.setImageBitmap(bm);

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