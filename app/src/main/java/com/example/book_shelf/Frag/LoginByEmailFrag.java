package com.example.book_shelf.Frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.book_shelf.AdminLogin;
import com.example.book_shelf.HomePage;
import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.R;
import com.example.book_shelf.Register;
import com.example.book_shelf.RequestPhoneForForgetPassword;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginByEmailFrag extends Fragment implements View.OnClickListener {

    View v;
    EditText edtEmail,edtPassword;
    CheckBox checkboxShowPswd;
    TextView txtForget;
    Button btnLogin,btnRegister,btnBack;
    TextView adminLoginText;
    DBHelper dbHelper;

    List<UserModel> userList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.login_by_email_frag,container,false);
        Context context = getContext();
        dbHelper = new DBHelper(context);
        setupUI();
        return v;
    }

    private void setupUI(){
        edtEmail = v.findViewById(R.id.edtEmail);
        edtPassword = v.findViewById(R.id.edtPassword);
        checkboxShowPswd = v.findViewById(R.id.checkboxShowPswd);
        txtForget = v.findViewById(R.id.txtForget);
        btnLogin = v.findViewById(R.id.btnLogin);
        btnRegister = v.findViewById(R.id.btnRegister);
        btnBack = v.findViewById(R.id.btnBack);
        adminLoginText = v.findViewById(R.id.adminLoginText);


        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        txtForget.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        adminLoginText.setOnClickListener(this);

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
    public void onClick(View view) {
        int clickCheck = view.getId();
        if (clickCheck == R.id.btnLogin){
            login();
        }else if(clickCheck == R.id.btnRegister){
            register();
        }else if(clickCheck == R.id.txtForget){
            forgetPswd();
        }else if(clickCheck == R.id.btnBack){
            getActivity().finish();
        }else if (clickCheck == R.id.adminLoginText){
            adminLogin();
        }
    }

    private void login(){
        boolean isUser = false;
        boolean isWrongPassword = false;

        int one = edtEmail.getText().length();
        int two = edtPassword.getText().length();

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (one > 0 && two > 0){
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !(email.equals("bookshelf@gmail.com"))){
                userList = dbHelper.getAllUsers();
                for (int i=0;i<userList.size();i++){
                    UserModel model = userList.get(i);
                    System.out.println("data ==================="+ userList.get(0).getEmail());
                    if(model.getEmail().equals(email) && !(model.getPassword().equals(password))){
                        isWrongPassword = true;
                    }
                    if(model.getEmail().equals(email)) {
                        isUser = true;
                    }
                    if(model.getEmail().equals(email) && model.getPassword().equals(password)){
                        isUser = true;
                        System.out.println("login data name ========================="+model.getName());
                        System.out.println("login data phone ========================="+model.getPhone());
                        System.out.println("login data pswd ========================="+model.getPassword());
                        System.out.println("login data profile ======================="+model.getProfile());
                        System.out.println("login data email =========================="+model.getEmail());

                        Util.saveData(getActivity(),"phone",model.getPhone());
                        Util.saveData(getActivity(),"name",model.getName());
                        Util.saveData(getActivity(),"email",model.getEmail());
                        Util.saveData(getActivity(),"profile",model.getProfile());
                        Util.saveData(getActivity(),"isAdmin","false");

                        startActivity(new Intent(getActivity(), HomePage.class));
                        break;
                    }
                }
                if(isUser==true){
                    if(isWrongPassword == true){
                        Util.showToast(getActivity(),"Wrong Password!");
                    }
                }else{
                    Util.showToast(getActivity(),"This Email has not an account. You can Register!");
                }

            }else{
                Util.showToast(getActivity(),"Invalid Email Address!");
            }
        }else{
            Util.showToast(getActivity(),"You must fill All fields!");
        }
    }

    private void register(){
        startActivity(new Intent(getActivity(), Register.class));
    }

    private void forgetPswd(){
        startActivity(new Intent(getActivity(), RequestPhoneForForgetPassword.class));
    }

    private void adminLogin(){
        startActivity(new Intent(getActivity(), AdminLogin.class));
    }
}
