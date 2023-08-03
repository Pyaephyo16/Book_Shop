package com.example.book_shelf.Frag;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.book_shelf.AdminOrderView;
import com.example.book_shelf.AdminUserView;
import com.example.book_shelf.InsertBook;
import com.example.book_shelf.Login;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.R;
import com.example.book_shelf.Search;
import com.example.book_shelf.SearchForDelete;
import com.example.book_shelf.SearchForPromo;
import com.example.book_shelf.UpdateBook;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class DashboardFrag extends Fragment implements View.OnClickListener, Animation.AnimationListener {

    View v;
    LinearLayout appBar;
    ImageView profile;
    EditText search;

    String promoDate = null;

    Button insertExpand,updateExpand,deleteExpand,promotionExpand;

    CardView userCard,orderCard;

    RelativeLayout insertCard,updateCard,deleteCard,promotionCard;

    Animation fade_in_text,fade_out_text;

    String isAdmin = null;
    String name = null;
    String email = null;
    String profilePath = null;

    DBHelper dbHelper;

    List<BookModel> bookList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.dashboard_frag,container,false);
        Context context = getContext();
        dbHelper = new DBHelper(context);
        fade_in_text = AnimationUtils.loadAnimation(context,R.anim.fade_in_text);
        fade_out_text = AnimationUtils.loadAnimation(context,R.anim.fade_out_text);
        fade_in_text.setAnimationListener(this);
        fade_out_text.setAnimationListener(this);
        setupUI(context);
        return v;
    }

    private void setupUI(Context context){
        appBar = v.findViewById(R.id.adminOnline);
        profile = v.findViewById(R.id.profile);
        search = v.findViewById(R.id.search);
        userCard = v.findViewById(R.id.userCard);
        orderCard = v.findViewById(R.id.orderCard);
        insertCard = v.findViewById(R.id.insertCard);
        updateCard = v.findViewById(R.id.updateCard);
        deleteCard = v.findViewById(R.id.deleteCard);
        promotionCard = v.findViewById(R.id.promotionCard);
        insertExpand = v.findViewById(R.id.insertExpand);
        updateExpand = v.findViewById(R.id.updateExpand);
        deleteExpand = v.findViewById(R.id.deleteExpand);
        promotionExpand = v.findViewById(R.id.promotionExpand);

        insertExpand.startAnimation(fade_in_text);
        updateExpand.startAnimation(fade_in_text);
        deleteExpand.startAnimation(fade_in_text);
        promotionExpand.startAnimation(fade_in_text);

        isAdmin = Util.getData(context,"isAdmin");
        promoDate = Util.getData(context,"promotion");
        System.out.println("promodate in dashboard "+promoDate+"    "+Util.promoList.size());

        bookList = dbHelper.getAllBooks();

        setupProfile(context);

        search.setOnClickListener(this);
        profile.setOnClickListener(this);
        userCard.setOnClickListener(this);
        orderCard.setOnClickListener(this);
        insertCard.setOnClickListener(this);
        updateCard.setOnClickListener(this);
        deleteCard.setOnClickListener(this);
        promotionCard.setOnClickListener(this);

    }

    private void setupProfile(Context context){
        String image = Util.getData(context,"profile");
        System.out.println("home frag profile "+image);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(image,opt);
        profile.setImageBitmap(bm);
    }

    @Override
    public void onClick(View v) {
        int touch = v.getId();
        if (touch == R.id.search){
            startActivity(new Intent(getActivity(), Search.class));
        }else if (touch == R.id.profile){
           profileDialog();
        }else if (touch == R.id.userCard){
            startActivity(new Intent(getActivity(), AdminUserView.class));
        }else if (touch == R.id.orderCard){
            startActivity(new Intent(getActivity(), AdminOrderView.class));
        }else if (touch == R.id.insertCard){
            startActivity(new Intent(getActivity(), InsertBook.class));
        }else if (touch == R.id.updateCard){
            startActivity(new Intent(getActivity(), UpdateBook.class));
        }else if (touch == R.id.deleteCard){
            startActivity(new Intent(getActivity(), SearchForDelete.class));
        }else if (touch == R.id.promotionCard){
            //if (promoDate==null) {
                startActivity(new Intent(getActivity(), SearchForPromo.class));
//            }else{
//                promoEndDialog();
//            }
        }
    }



    private void profileDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.profile_dialog,null);
        builder.setView(view);

        TextView userName,userEmail,logout;
        EditText manageBtn;
        ImageView dialogProfile;

        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);
        manageBtn = view.findViewById(R.id.manageBtn);
        dialogProfile = view.findViewById(R.id.dialogProfile);
        logout = view.findViewById(R.id.logout);

        Context context = getContext();

        name = Util.getData(context,"name");
        email = Util.getData(context,"email");
        profilePath = Util.getData(context,"profile");
        System.out.println("homefrag check "+name+"  "+email+"  "+profilePath);

        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(profilePath,opt);
        dialogProfile.setImageBitmap(bm);
        userName.setText(name);
        userEmail.setText(email);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.saveData(context,"phone",null);
                Util.saveData(context,"name",null);
                Util.saveData(context,"email",null);
                Util.saveData(context,"profile",null);
                Util.saveData(context,"isAdmin",null);
                Util.totalCost = 0;
                Util.addToCardList.clear();
                Util.promoList.clear();
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin.equals("true")){
                    Util.showToast(getContext(),"Admin is not allow to update account information!");
                }else{
                    //Util.showToast(context,"Touch");
                }
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

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == fade_in_text){
            insertExpand.startAnimation(fade_out_text);
            updateExpand.startAnimation(fade_out_text);
            deleteExpand.startAnimation(fade_out_text);
            promotionExpand.startAnimation(fade_out_text);
        }else if (animation == fade_out_text){
            insertExpand.startAnimation(fade_in_text);
            updateExpand.startAnimation(fade_in_text);
            deleteExpand.startAnimation(fade_in_text);
            promotionExpand.startAnimation(fade_in_text);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}
