package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class RequestPhoneForForgetPassword extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView imgRequestPhone;
    Animation zoom_in,zoom_out;
    EditText edtPhone;
    Button btnConfirm;

    DBHelper dbHelper = new DBHelper(RequestPhoneForForgetPassword.this);

    List<UserModel> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_phone_for_forget_password);

        zoom_in = AnimationUtils.loadAnimation(RequestPhoneForForgetPassword.this,R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(RequestPhoneForForgetPassword.this,R.anim.zoom_out);
        zoom_in.setAnimationListener(this);
        zoom_out.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        imgRequestPhone = findViewById(R.id.imgRequestPhone);
        edtPhone = findViewById(R.id.edtPhone);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(this);

        imgRequestPhone.startAnimation(zoom_in);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation==zoom_in){
            imgRequestPhone.startAnimation(zoom_out);
        }else{
            imgRequestPhone.startAnimation(zoom_in);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        int checkTouch = v.getId();
        if(checkTouch == R.id.btnConfirm){
            requestPhone();
        }
    }

    private void requestPhone(){
        boolean isUser = false;
        int one = edtPhone.getText().length();

        String phone = edtPhone.getText().toString();

        if (one > 0) {
            if (isValidPhoneNumber(phone)){

                userList = dbHelper.getAllUsers();

                for (int i=0;i<userList.size();i++){
                    UserModel model = userList.get(i);
                    if(model.getPhone().equals(phone) && !(phone.equals("09770939130"))){
                        isUser = true;

                        System.out.println("request phone ==> "+phone);
                        Intent intent = new Intent(this,Otp.class);
                        intent.putExtra("inputPhone",phone);
                        startActivity(intent);
                        break;
                    }
                }
                if(isUser==true){

                }else{
                    Util.showToast(this,"This Phone has not an account. You can Register!");
                }

            }else{
                Util.showToast(this,"Invalid phone number!");
            }
        }else{
            Util.showToast(this,"You must fill phone number!");
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[+]?[0-9]{10,13}$";
        return Patterns.PHONE.matcher(phoneNumber).matches() && phoneNumber.matches(phoneRegex);
    }
}