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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.AddToCartPage;
import com.example.book_shelf.BookDetailPage;
import com.example.book_shelf.Helper.BookAdapter;
import com.example.book_shelf.Helper.BookRowAdapter;
import com.example.book_shelf.List_By_Type;
import com.example.book_shelf.Login;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Profile;
import com.example.book_shelf.R;
import com.example.book_shelf.Search;
import com.example.book_shelf.Util.Util;
import com.example.book_shelf.VoucherPage;
import com.example.book_shelf.db.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFrag extends Fragment implements View.OnClickListener {

    View v;

    LinearLayout appBar;
    ImageView profile,cart,badgeImageView;
    EditText search;

    LinearLayout adminOnline;

    LinearLayout comicBranch,novelBranch,pictureBookBranch,dcComicsBranch,marvelComicBranch,mangaBranch,horrorBranch,fantasyBranch,adviceForABettrLifeBranch,romanceBranch,storiesToSaveTheWorldBranch,newYorkBranch;
    RecyclerView comicRecycler,novelRecycler,pictureBookRecycler,dcComicsRecycler,marvelComicRecycler,mangaRecycler,horrorRecycler,fantasyRecycler,adviceForABettrLifeRecycler,romanceRecycler,storiesToSaveTheWorldRecycler,newYorkRecycler;
    BookRowAdapter comicBookRowAdapter,novelBookRowAdapter,pictureBookBookRowAdapter,dcComicsBookRowAdapter,marvelComicBookRowAdapter,mangaBookRowAdapter,horrorBookRowAdapter,fantasyBookRowAdapter,adviceForABettrLifeBookRowAdapter,romanceBookRowAdapter,storiesToSaveTheWorldBookRowAdapter,newYorkBookRowAdapter;

    ImageView novelsTypeDetail,pictureBooksTypeDetail,comicsTypeDetail,dcComicsTypeDetail,marvelComicsTypeDetail,mangaTypeDetail,horrorTypeDetail,fantasyTypeDetail,adviceForABetterLifeTypeDetail,romanceTypeDetail,storiesToSaveTheWorldTypeDetail,newYorkTypeDetail;
    String isAdmin = null;
    String name = null;
    String email = null;
    String profilePath = null;

    String phone = null;

    DBHelper dbHelper;

    List<BookModel> comicList = new ArrayList<>();
    List<BookModel> pictureBookList = new ArrayList<>();
    List<BookModel> novelList = new ArrayList<>();
    List<BookModel> dcComicList = new ArrayList<>();
    List<BookModel> marvelComicList = new ArrayList<>();
    List<BookModel> mangaList = new ArrayList<>();
    List<BookModel> horrorList = new ArrayList<>();
    List<BookModel> fantasyList = new ArrayList<>();
    List<BookModel> adiveForABetterLifeList = new ArrayList<>();
    List<BookModel> romanceList = new ArrayList<>();
    List<BookModel> storiesToSaveTheWorldList = new ArrayList<>();
    List<BookModel> newYorkTimesBestSellersList = new ArrayList<>();
    List<BookModel> allBooks = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_frag,container,false);
        Context context = getContext();
        dbHelper = new DBHelper(context);
        setupUI(context);
        return v;
    }

    private void setupUI(Context context){
        adminOnline = v.findViewById(R.id.adminOnline);
        appBar = v.findViewById(R.id.appBar);
        profile = v.findViewById(R.id.profile);
        cart = v.findViewById(R.id.cart);
        search = v.findViewById(R.id.search);
        comicBranch = v.findViewById(R.id.comicBranch);
        comicRecycler = v.findViewById(R.id.comicRecycler);
        novelBranch = v.findViewById(R.id.novelBranch);
        novelRecycler = v.findViewById(R.id.novelRecycler);
        pictureBookBranch = v.findViewById(R.id.pictureBookBranch);
        pictureBookRecycler = v.findViewById(R.id.pictureBookRecycler);
        dcComicsRecycler = v.findViewById(R.id.dcComicsRecycler);
        dcComicsBranch = v.findViewById(R.id.dcComicsBranch);
        marvelComicRecycler = v.findViewById(R.id.marvelComicRecycler);
        marvelComicBranch = v.findViewById(R.id.marvelComicBranch);
        mangaBranch = v.findViewById(R.id.mangaBranch);
        mangaRecycler = v.findViewById(R.id.mangaRecycler);
        horrorRecycler = v.findViewById(R.id.horrorRecycler);
        horrorBranch = v.findViewById(R.id.horrorBranch);
        fantasyRecycler = v.findViewById(R.id.fantasyRecycler);
        fantasyBranch = v.findViewById(R.id.fantasyBranch);
        adviceForABettrLifeRecycler = v.findViewById(R.id.adviceForABettrLifeRecycler);
        adviceForABettrLifeBranch = v.findViewById(R.id.adviceForABettrLifeBranch);
        romanceRecycler = v.findViewById(R.id.romanceRecycler);
        romanceBranch = v.findViewById(R.id.romanceBranch);
        storiesToSaveTheWorldRecycler = v.findViewById(R.id.storiesToSaveTheWorldRecycler);
        storiesToSaveTheWorldBranch = v.findViewById(R.id.storyBranch);
        newYorkRecycler = v.findViewById(R.id.newYorkRecycler);
        newYorkBranch = v.findViewById(R.id.newYorkBranch);
        novelsTypeDetail = v.findViewById(R.id.novelsTypeDetail);
        pictureBooksTypeDetail = v.findViewById(R.id.pictureBooksTypeDetail);
        comicsTypeDetail = v.findViewById(R.id.comicsTypeDetail);
        dcComicsTypeDetail = v.findViewById(R.id.dcComicsTypeDetail);
        marvelComicsTypeDetail = v.findViewById(R.id.marvelComicsTypeDetail);
        mangaTypeDetail = v.findViewById(R.id.mangaTypeDetail);
        horrorTypeDetail = v.findViewById(R.id.horrorTypeDetail);
        fantasyTypeDetail = v.findViewById(R.id.fantasyTypeDetail);
        adviceForABetterLifeTypeDetail = v.findViewById(R.id.adviceForABetterLifeTypeDetail);
        romanceTypeDetail = v.findViewById(R.id.romanceTypeDetail);
        storiesToSaveTheWorldTypeDetail = v.findViewById(R.id.storiesToSaveTheWorldTypeDetail);
        newYorkTypeDetail = v.findViewById(R.id.newYorkTypeDetail);
        badgeImageView = v.findViewById(R.id.badgeImageView);

        if (Util.addToCardList.size()>0){
            badgeImageView.setVisibility(View.VISIBLE);
        }else{
            badgeImageView.setVisibility(View.GONE);
        }

        allBooks = dbHelper.getAllBooks();
        divdeBranch(allBooks);

        novelSection(context);
        pictureBookSection(context);
       comicSection(context);
       dcComicSection(context);
       marvelComicSection(context);
       mangaSection(context);
       horrorSection(context);
       fantasySection(context);
       adviceForABetterLifeSection(context);
       romanceSection(context);
       storiesToSaveTheWorldSection(context);
       newYorkSection(context);



        isAdmin = Util.getData(context,"isAdmin");
        setupProfile(context);

        search.setOnClickListener(this);
        profile.setOnClickListener(this);
        cart.setOnClickListener(this);
        novelsTypeDetail.setOnClickListener(this);
        pictureBooksTypeDetail.setOnClickListener(this);
        comicsTypeDetail.setOnClickListener(this);
        dcComicsTypeDetail.setOnClickListener(this);
        marvelComicsTypeDetail.setOnClickListener(this);
        mangaTypeDetail.setOnClickListener(this);
        horrorTypeDetail.setOnClickListener(this);
        fantasyTypeDetail.setOnClickListener(this);
        adviceForABetterLifeTypeDetail.setOnClickListener(this);
        romanceTypeDetail.setOnClickListener(this);
        storiesToSaveTheWorldTypeDetail.setOnClickListener(this);
        newYorkTypeDetail.setOnClickListener(this);


        if (isAdmin.equals("true")){
            adminOnline.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);
        }else{
            adminOnline.setVisibility(View.GONE);
        }
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
        Context context = getContext();
        int touch = v.getId();
        if (touch == R.id.search){
            startActivity(new Intent(getActivity(), Search.class));
        }else if (touch == R.id.profile){
            profileDialog();
        }else if (touch == R.id.cart){
            startActivity(new Intent(getActivity(), AddToCartPage.class));
        }else if (touch == R.id.novelsTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Novels");
            startActivity(i);
        }else if (touch == R.id.pictureBooksTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Picture Books");
            startActivity(i);
        }else if (touch == R.id.comicsTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Comics");
            startActivity(i);
        }else if (touch == R.id.dcComicsTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","DC Comics");
            startActivity(i);
        }else if (touch == R.id.marvelComicsTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Marvel Comics");
            startActivity(i);
        }else if (touch == R.id.mangaTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Manga");
            startActivity(i);
        }else if (touch == R.id.horrorTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Horror");
            startActivity(i);
        }else if (touch == R.id.fantasyTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Fantasy");
            startActivity(i);
        }else if (touch == R.id.adviceForABetterLifeTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Advice for a better life");
            startActivity(i);
        }else if (touch == R.id.romanceTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Romance");
            startActivity(i);
        }else if (touch == R.id.storiesToSaveTheWorldTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","Stories to save the world");
            startActivity(i);
        }else if (touch == R.id.newYorkTypeDetail){
            Intent i =new Intent(v.getContext(), List_By_Type.class);
            i.putExtra("bookType","New Your Times bestsellers");
            startActivity(i);
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
        System.out.println("homefrag check "+name+"  "+email+"  "+profilePath+"   "+phone);

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
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        manageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin.equals("true")){
                Util.showToast(getContext(),"Admin is not allow to update account information!");
            }else{
                    Intent i = new Intent(getActivity(), Profile.class);
                    i.putExtra("phone",phone);
                 startActivity(i);
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

    private void novelSection(Context context){
        if (novelList.size()==0 || novelList==null){
            novelBranch.setVisibility(View.GONE);
        }else{
            novelRecycler.setHasFixedSize(true);
            novelRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            novelBookRowAdapter = new BookRowAdapter(context,novelList);
            novelRecycler.setAdapter(novelBookRowAdapter);
        }
    }

    private void pictureBookSection(Context context){
        if (pictureBookList.size()==0 || pictureBookList==null){
            pictureBookBranch.setVisibility(View.GONE);
        }else{
            pictureBookRecycler.setHasFixedSize(true);
            pictureBookRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            pictureBookBookRowAdapter = new BookRowAdapter(context,pictureBookList);
            pictureBookRecycler.setAdapter(pictureBookBookRowAdapter);
        }
    }
    private void comicSection(Context context){
        if (comicList.size()==0 || comicList==null){
                comicBranch.setVisibility(View.GONE);
        }else{
            comicRecycler.setHasFixedSize(true);
            comicRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            comicBookRowAdapter = new BookRowAdapter(context,comicList);
            comicRecycler.setAdapter(comicBookRowAdapter);
        }
    }

    private void dcComicSection(Context context){
        if (dcComicList.size()==0 || dcComicList==null){
            dcComicsBranch.setVisibility(View.GONE);
        }else{
            dcComicsRecycler.setHasFixedSize(true);
            dcComicsRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            dcComicsBookRowAdapter = new BookRowAdapter(context,dcComicList);
            dcComicsRecycler.setAdapter(dcComicsBookRowAdapter);
        }
    }

    private void marvelComicSection(Context context){
        if (marvelComicList.size()==0 || marvelComicList==null){
            marvelComicBranch.setVisibility(View.GONE);
        }else{
            marvelComicRecycler.setHasFixedSize(true);
            marvelComicRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            marvelComicBookRowAdapter = new BookRowAdapter(context,marvelComicList);
            marvelComicRecycler.setAdapter(marvelComicBookRowAdapter);
        }
    }

    private void mangaSection(Context context){
        if (mangaList.size()==0 || mangaList==null){
            mangaBranch.setVisibility(View.GONE);
        }else{
            mangaRecycler.setHasFixedSize(true);
            mangaRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            mangaBookRowAdapter = new BookRowAdapter(context,mangaList);
            mangaRecycler.setAdapter(mangaBookRowAdapter);
        }
    }

    private void horrorSection(Context context){
        if (horrorList.size()==0 || horrorList==null){
            horrorBranch.setVisibility(View.GONE);
        }else{
            horrorRecycler.setHasFixedSize(true);
            horrorRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            horrorBookRowAdapter = new BookRowAdapter(context,horrorList);
            horrorRecycler.setAdapter(horrorBookRowAdapter);
        }
    }

    private void fantasySection(Context context){
        if (fantasyList.size()==0 || fantasyList==null){
            fantasyBranch.setVisibility(View.GONE);
        }else{
            fantasyRecycler.setHasFixedSize(true);
            fantasyRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            fantasyBookRowAdapter = new BookRowAdapter(context,fantasyList);
            fantasyRecycler.setAdapter(fantasyBookRowAdapter);
        }
    }

    private void adviceForABetterLifeSection(Context context){
        if (adiveForABetterLifeList.size()==0 || adiveForABetterLifeList==null){
            adviceForABettrLifeBranch.setVisibility(View.GONE);
        }else{
            adviceForABettrLifeRecycler.setHasFixedSize(true);
            adviceForABettrLifeRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            adviceForABettrLifeBookRowAdapter = new BookRowAdapter(context,adiveForABetterLifeList);
            adviceForABettrLifeRecycler.setAdapter(adviceForABettrLifeBookRowAdapter);
        }
    }

    private void romanceSection(Context context){
        if (romanceList.size()==0 || romanceList==null){
            romanceBranch.setVisibility(View.GONE);
        }else{
            romanceRecycler.setHasFixedSize(true);
            romanceRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            romanceBookRowAdapter = new BookRowAdapter(context,romanceList);
            romanceRecycler.setAdapter(romanceBookRowAdapter);
        }
    }

    private void storiesToSaveTheWorldSection(Context context){
        if (storiesToSaveTheWorldList.size()==0 || storiesToSaveTheWorldList==null){
            storiesToSaveTheWorldBranch.setVisibility(View.GONE);
        }else{
            storiesToSaveTheWorldRecycler.setHasFixedSize(true);
            storiesToSaveTheWorldRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            storiesToSaveTheWorldBookRowAdapter = new BookRowAdapter(context,storiesToSaveTheWorldList);
            storiesToSaveTheWorldRecycler.setAdapter(storiesToSaveTheWorldBookRowAdapter);
        }
    }

    private void newYorkSection(Context context){
        if (newYorkTimesBestSellersList.size()==0 || newYorkTimesBestSellersList==null){
            newYorkBranch.setVisibility(View.GONE);
        }else{
            newYorkRecycler.setHasFixedSize(true);
            newYorkRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            newYorkBookRowAdapter = new BookRowAdapter(context,newYorkTimesBestSellersList);
            newYorkRecycler.setAdapter(newYorkBookRowAdapter);
        }
    }
    private void divdeBranch(List<BookModel> allBooks){
        for (BookModel bm : allBooks) {
            if (bm.getType().equals("Comics")){
                comicList.add(bm);
            }else if (bm.getType().equals("Novels")){
                novelList.add(bm);
            }else if (bm.getType().equals("Picture Books")){
                pictureBookList.add(bm);
            } else if (bm.getType().equals("DC Comics")) {
                dcComicList.add(bm);
            } else if (bm.getType().equals("Marvel Comics")) {
                marvelComicList.add(bm);
            } else if (bm.getType().equals("Manga")) {
                mangaList.add(bm);
            } else if (bm.getType().equals("Horror")) {
                horrorList.add(bm);
            } else if (bm.getType().equals("Fantasy")) {
                fantasyList.add(bm);
            } else if (bm.getType().equals("Advice for a better life")) {
                adiveForABetterLifeList.add(bm);
            }else if (bm.getType().equals("Romance")){
                romanceList.add(bm);
            } else if (bm.getType().equals("Stories to save the world")) {
                storiesToSaveTheWorldList.add(bm);
            }else if (bm.getType().equals("New Your Times bestsellers")){
                newYorkTimesBestSellersList.add(bm);
            }
        }
    }

}
