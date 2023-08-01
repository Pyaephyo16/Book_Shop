package com.example.book_shelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView imgRegister,profile;
    EditText edtName,edtPhone,edtEmail,edtPassword,edtConfirmPassword;
    CheckBox checkboxShowPswd,checkboxShowConfirmPswd;
    Button btnLogin,btnRegister,btnBack;

    Animation zoom_in,zoom_out;

   private static final int CODE = 1;
    Uri filePath = null;
    String realFilePath = null;

    DBHelper dbHelper = new DBHelper(Register.this);

    List<UserModel> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        zoom_in = AnimationUtils.loadAnimation(Register.this,R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(Register.this,R.anim.zoom_out);
        zoom_in.setAnimationListener(this);
        zoom_out.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        imgRegister = findViewById(R.id.imgRegister);
        profile = findViewById(R.id.profile);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        checkboxShowPswd = findViewById(R.id.checkboxShowPswd);
        checkboxShowConfirmPswd = findViewById(R.id.checkboxShowConfirmPswd);
        btnBack = findViewById(R.id.btnBack);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        imgRegister.startAnimation(zoom_in);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        profile.setOnClickListener(this);

        checkboxShowPswd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    checkboxShowPswd.setText("Hide Password");
                }else{
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    checkboxShowPswd.setText("Show Password");
                }
            }
        });

        checkboxShowConfirmPswd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    checkboxShowConfirmPswd.setText("Hide Password");
                }else{
                    edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    checkboxShowConfirmPswd.setText("Show Password");
                }
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==zoom_in){
            imgRegister.startAnimation(zoom_out);
        }else{
            imgRegister.startAnimation(zoom_in);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
      int checkTouch = v.getId();
      if (checkTouch == R.id.btnLogin){
          login();
      }else if(checkTouch == R.id.btnRegister){
          register();
      }else if (checkTouch == R.id.btnBack){
          finish();
      }else if(checkTouch == R.id.profile){
        requestForPermission();
      }
    }

    private void login(){
        startActivity(new Intent(Register.this,Login.class));
    }

    private void register(){
        int one = edtName.getText().length();
        int two = edtPhone.getText().length();
        int three = edtEmail.getText().length();
        int four = edtPassword.getText().length();
        int five = edtConfirmPassword.getText().length();

        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();


        if (one >0 && two >0 && three > 0 && four >0 && five > 0){
            if (realFilePath != null){
                if (isValidPhoneNumber(phone)){
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        if(four >=6){
                            if (password.equals(confirmPassword)){

                                userList = dbHelper.getAllUsers();
                                boolean isAlreadyHaveAcc = false;

                                for (int i=0;i<userList.size();i++){
                                    if (email.equals(userList.get(i).toString()) || phone.equals(userList.get(i).toString())){
                                        isAlreadyHaveAcc = true;
                                        break;
                                    }
                                }

                                if (isAlreadyHaveAcc != true){
                                    dbHelper.registerUser(
                                            name,
                                            phone,
                                            email,
                                            password,
                                            realFilePath
                                    );
                                    Util.saveData(this,"phone",phone);
                                    Util.saveData(this,"name",name);
                                    Util.saveData(this,"email",email);
                                    Util.saveData(this,"profile",realFilePath);
                                    Util.saveData(this,"isAdmin","false");
                                    startActivity(new Intent(Register.this, HomePage.class));
                                }else{
                                    Util.showToast(this,"Your input email or phone number already has an account!");
                                }

                            }else{
                                Util.showToast(this,"Password and Confirm Password must same!");
                            }
                        }else{
                            Util.showToast(this,"Password length must have at least 6");
                        }
                    }else{
                        Util.showToast(this,"Invalid Email Address!");
                    }
                }else{
                    Util.showToast(this,"Invalid Phone number!");
                }
            }else{
                Util.showToast(this,"You need to add Profile Picture!");
            }
        }else{
            Util.showToast(Register.this,"You must fill All fields!");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[+]?[0-9]{10,13}$";
        return Patterns.PHONE.matcher(phoneNumber).matches() && phoneNumber.matches(phoneRegex);
    }

    private void requestForPermission(){
            ActivityCompat.requestPermissions(Register.this,
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
                Util.showToast(Register.this,"Need Permission to pick photo");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CODE && resultCode == RESULT_OK && data!=null){

            filePath = data.getData();
            System.out.println("profile file path "+filePath.toString());
            System.out.println("real path "+getRealPathFromURI(Register.this,filePath));

            realFilePath = getRealPathFromURI(Register.this,filePath);

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