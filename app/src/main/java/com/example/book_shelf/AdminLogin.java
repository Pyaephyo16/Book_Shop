package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminLogin extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView imgAdminLogin;
    CheckBox checkboxShowPswd;
    EditText edtPhone,edtEmail,edtPassword;
    Button btnBack,btnSubmit;
    Animation zoom_in,zoom_out;

    DBHelper dbHelper = new DBHelper(AdminLogin.this);

    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        zoom_in = AnimationUtils.loadAnimation(AdminLogin.this,R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(AdminLogin.this,R.anim.zoom_out);

        zoom_in.setAnimationListener(this);
        zoom_out.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        imgAdminLogin = findViewById(R.id.imgAdminLogin);
        checkboxShowPswd = findViewById(R.id.checkboxShowPswd);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnBack = findViewById(R.id.btnBack);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        imgAdminLogin.startAnimation(zoom_in);

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
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==zoom_in){
            imgAdminLogin.startAnimation(zoom_out);
        }else{
            imgAdminLogin.startAnimation(zoom_in);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        int touchCheck = v.getId();
        if(touchCheck == R.id.btnSubmit){
            adminLogin();
        }else if(touchCheck == R.id.btnBack){
            finish();
        }
    }

    private void adminLogin(){
        int one = edtPhone.getText().length();
        int two = edtEmail.getText().length();
        int three = edtPassword.getText().length();

        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (one >0 && two >0 && three > 0 ){
                if (isValidPhoneNumber(phone)){
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            if (phone.equals("09770939130") && email.equals("bookshelf@gmail.com")){

                                user = dbHelper.getUserDetail(phone);

                                Util.saveData(this,"phone",phone);
                                Util.saveData(this,"name",user.getName());
                                Util.saveData(this,"email",email);
                                Util.saveData(this,"profile",user.getProfile());
                                Util.saveData(this,"isAdmin","true");
                                startActivity(new Intent(AdminLogin.this, HomePage.class));
                            }else{
                                Util.showToast(this,"Something Went Wrong!");
                            }
                    }else{
                        Util.showToast(this,"Invalid Email Address!");
                    }
                }else{
                    Util.showToast(this,"Invalid Phone number!");
                }
        }else{
            Util.showToast(AdminLogin.this,"You must fill All fields!");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[+]?[0-9]{10,13}$";
        return Patterns.PHONE.matcher(phoneNumber).matches() && phoneNumber.matches(phoneRegex);
    }
}