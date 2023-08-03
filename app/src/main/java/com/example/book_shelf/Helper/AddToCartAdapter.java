package com.example.book_shelf.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.AddToCartPage;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.R;
import com.example.book_shelf.Util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AddToCartAdapter extends RecyclerView.Adapter<AddToCartAdapter.ViewHolder>{

    Context context;
    List<BookModel> bookList = new ArrayList<>();

    public AddToCartAdapter(Context context,List<BookModel> bookList){
        this.context = context;
        this.bookList = bookList;
        Collections.sort(Util.addToCardList);

        for (int k=0;k < bookList.size();k++){
               if (bookList.get(k).getPromoPrice().equals("0")){
                   Util.totalCost = Util.totalCost + Integer.parseInt(bookList.get(k).getPrice());
               }else{
                   Util.totalCost = Util.totalCost + Integer.parseInt(bookList.get(k).getPromoPrice());
               }
        }

        for (int j=0;j<Util.addToCardList.size();j++){
            int id = Util.addToCardList.get(j);
            System.out.println("price "+Util.totalCost);
            System.out.println("recycler data ============= "+j+ "   "+bookList.get(j));
            System.out.println("recycler data ============= "+j+ "   "+Util.addToCardList.get(j));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_book,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BookModel model =bookList.get(position);

        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(model.getPicture(),opt);
        holder.book.setImageBitmap(bm);
        holder.bookTitle.setText(model.getTitle());
        holder.stock.setText(String.valueOf(model.getStock()));
        int stock = model.getStock();

        holder.takeCount.setText(String.valueOf(model.getCurrentTake()));


        holder.btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getCurrentTake()>0){
                    if (model.getCurrentTake()<stock){
                        model.increaseCurrentTake();
                        holder.takeCount.setText(String.valueOf(model.getCurrentTake()));
                        //Util.showToast(context,model.getTitle()+"  "+currentTake);

                        if (model.getPromoPrice().equals("0")){
                            ((AddToCartPage) v.getContext()).updatePrice(model.getPrice(),1);
                        }else{
                            ((AddToCartPage) v.getContext()).updatePrice(model.getPromoPrice(),1);
                        }
                    }
                }
            }
        });

        holder.btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getCurrentTake()>1){
                    model.decreaseCurrentTake();
                    holder.takeCount.setText(String.valueOf(model.getCurrentTake()));
                    //Util.showToast(context,model.getTitle()+"  "+currentTake);

                    if (model.getPromoPrice().equals("0")){
                        ((AddToCartPage) v.getContext()).updatePrice(model.getPrice(),2);
                    }else{
                        ((AddToCartPage) v.getContext()).updatePrice(model.getPromoPrice(),2);
                    }
                }
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getCurrentTake() > 1){
                    Util.showToast(context,"Please reduce the taken count to 1 before remove");
                }else{
                    for (int j=0;j<Util.addToCardList.size();j++){
                        int id = Util.addToCardList.get(j);
                        if (id==model.getBookId()){
                            bookList.remove(j);
                            ((AddToCartPage) v.getContext()).updatePrice(model.getPrice(),0);

                            notifyItemRemoved(j);
                            Util.addToCardList.remove(j);
                            break;
                        }
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public String bookPrice(String current){
        Util.totalCost-=Integer.parseInt(current);
        System.out.println("price remove "+Util.totalCost+"    "+"book "+current);
        return String.valueOf(Util.totalCost);
    }

    public String increaseBookPrice(String current){
        Util.totalCost= Util.totalCost + Integer.parseInt(current);
        System.out.println("price increase "+Util.totalCost+"    "+"book "+current);
        return String.valueOf(Util.totalCost);
    }

    public void fixPrice(){

    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView book;
        TextView bookTitle,takeCount,stock;
        LinearLayout btnIncrease,btnDecrease,btnRemove;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book = itemView.findViewById(R.id.book);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            takeCount = itemView.findViewById(R.id.takeCount);
            stock = itemView.findViewById(R.id.stock);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}

