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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.AddToCartPage;
import com.example.book_shelf.CustomerOrderView;
import com.example.book_shelf.Helper.BookAdapter;
import com.example.book_shelf.Helper.LibraryBookAdapter;
import com.example.book_shelf.Login;
import com.example.book_shelf.Models.ShelfBookModel;
import com.example.book_shelf.Profile;
import com.example.book_shelf.R;
import com.example.book_shelf.Search;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class LibraryFrag extends Fragment implements View.OnClickListener {

    View v;

    LinearLayout appBar;
    ImageView profile,cart,badgeImageView;
    EditText search;
    CardView orderCard;
    RecyclerView recyclerView;
    LibraryBookAdapter adapter;

    List<ShelfBookModel> shelfBookList = new ArrayList<>();

    DBHelper dbHelper;

    String name = null;
    String email = null;
    String profilePath = null;

    String phone = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.library_frag,container,false);
        Context context = getContext();
        dbHelper = new DBHelper(context);
        setupUI(context);
        return v;
    }

    private void setupUI(Context context){
        appBar = v.findViewById(R.id.adminOnline);
        profile = v.findViewById(R.id.profile);
        cart = v.findViewById(R.id.cart);
        search = v.findViewById(R.id.search);
        badgeImageView = v.findViewById(R.id.badgeImageView);
        orderCard = v.findViewById(R.id.orderCard);
        recyclerView = v.findViewById(R.id.recyclerView);

        String phone = Util.getData(context,"phone");
        shelfBookList = dbHelper.getShelfBooksByUser(phone);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new LibraryBookAdapter(context,shelfBookList);
        recyclerView.setAdapter(adapter);



        if (Util.addToCardList.size()>0){
            badgeImageView.setVisibility(View.VISIBLE);
        }else{
            badgeImageView.setVisibility(View.GONE);
        }

        setupProfile(context);

        search.setOnClickListener(this);
        profile.setOnClickListener(this);
        cart.setOnClickListener(this);
        orderCard.setOnClickListener(this);
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
        }else if (touch == R.id.cart){
            startActivity(new Intent(getActivity(), AddToCartPage.class));
        }else if (touch == R.id.orderCard){
            startActivity(new Intent(getActivity(), CustomerOrderView.class));
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
        phone = Util.getData(context,"phone");
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
                Intent i = new Intent(getActivity(), Profile.class);
                i.putExtra("phone",phone);
               startActivity(i);
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
}
