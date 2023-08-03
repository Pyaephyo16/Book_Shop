package com.example.book_shelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

public class ForgetPassword extends AppCompatActivity implements Animation.AnimationListener, View.OnClickListener {

    ImageView imgChangePassword;
    EditText edtConfirmPassword,edtPassword;
    Button btnLogin,btnSubmit;
    CheckBox confirmPasswordCheckbox,passwordCheckbox;
    Animation zoom_in,zoom_out;

    String inputPhone = "";

    DBHelper dbHelper = new DBHelper(ForgetPassword.this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        zoom_in = AnimationUtils.loadAnimation(ForgetPassword.this,R.anim.zoom_in);
        zoom_out = AnimationUtils.loadAnimation(ForgetPassword.this,R.anim.zoom_out);
        zoom_in.setAnimationListener(this);
        zoom_out.setAnimationListener(this);
        setupUI();
    }

    private void setupUI(){
        imgChangePassword = findViewById(R.id.imgChangePassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSubmit = findViewById(R.id.btnSubmit);
        confirmPasswordCheckbox = findViewById(R.id.confirmPasswordCheckbox);
        passwordCheckbox = findViewById(R.id.passwordCheckbox);

        inputPhone = getIntent().getStringExtra("inputPhone");
        System.out.println("change passowrd "+inputPhone);

        btnSubmit.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        imgChangePassword.startAnimation(zoom_in);

        passwordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordCheckbox.setText("Hide Password");
                }else{
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordCheckbox.setText("Show Password");
                }
            }
        });

        confirmPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    confirmPasswordCheckbox.setText("Hide Password");
                }else{
                    edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    confirmPasswordCheckbox.setText("Show Password");
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
            imgChangePassword.startAnimation(zoom_out);
        }else{
            imgChangePassword.startAnimation(zoom_in);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        int checkTouch = v.getId();
        if (checkTouch == R.id.btnLogin) {
            startActivity(new Intent(ForgetPassword.this,Login.class));
        }else if(checkTouch == R.id.btnSubmit){
            changePassword();
        }
    }

    private void changePassword(){
        UserModel model = dbHelper.getUserDetail(inputPhone);
        //System.out.println("forget id "+model.getId());
        System.out.println("forget name "+model.getName());
        System.out.println("forget email "+model.getEmail());
        System.out.println("forget phone "+model.getPhone());
        System.out.println("forget pswd "+model.getPassword());
        System.out.println("forget profile "+model.getProfile());
        String newPassword = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();

        if (edtPassword.getText().length() >= 6){
            if(newPassword.length()>0 && confirmPassword.length()>0){
                if (newPassword.equals(confirmPassword)){
                    dbHelper.updateUser(model.getName(),inputPhone,model.getEmail(),newPassword,model.getProfile(),model.getOwnBook());
                    startActivity(new Intent(ForgetPassword.this,Login.class));
                }else{
                    Util.showToast(ForgetPassword.this,"New Password and Confirm Password must same");
                }
            }else{
                Util.showToast(ForgetPassword.this,"You need to fill All Fields");
            }
        }else{
            Util.showToast(this,"Password lenght must have at least 6!");
        }
    }
}