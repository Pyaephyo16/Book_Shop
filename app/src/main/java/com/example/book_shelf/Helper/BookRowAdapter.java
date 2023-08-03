package com.example.book_shelf.Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.BookDetailPage;
import com.example.book_shelf.ChangeBookData;
import com.example.book_shelf.DeleteBook;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.R;
import com.example.book_shelf.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class BookRowAdapter extends RecyclerView.Adapter<BookRowAdapter.ViewHolder>{

    Context context;
    List<BookModel> bookList = new ArrayList<>();

    public BookRowAdapter(Context context,List<BookModel> list){
            this.context = context;
            this.bookList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_row,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            BookModel model =bookList.get(position);

//            String isAdmin = Util.getData(context,"isAdmin");
//            if (isAdmin.equals("true")){
//                holder.addToCart.setVisibility(View.GONE);
//            }else{
//                holder.addToCart.setVisibility(View.VISIBLE);
//                holder.addToCart.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//            }

            holder.bookTitle.setText(model.getTitle());
            if (model.getPromoPrice().equals("0")){
                holder.bookPrice.setText(model.getPrice()+" MMK");
            }else{
                holder.bookPrice.setText(model.getPromoPrice()+" MMK");
            }
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(model.getPicture(),opt);
        holder.book.setImageBitmap(bm);

        holder.bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i =new Intent(v.getContext(), BookDetailPage.class);
                    i.putExtra("bookId",String.valueOf(model.getBookId()));
                    i.putExtra("source",String.valueOf("home"));
                    v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView book;
        TextView bookTitle,bookPrice;

        LinearLayout bookCard;
//           LinearLayout addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book = itemView.findViewById(R.id.book);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            bookCard = itemView.findViewById(R.id.bookCard);
           // addToCart = itemView.findViewById(R.id.addToCart);
        }
    }

}
