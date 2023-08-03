package com.example.book_shelf.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.R;

import java.util.List;

public class PromoPriceAdapter extends RecyclerView.Adapter<PromoPriceAdapter.ViewHolder> {

    Context context;
    List<BookModel> bookList;

    public PromoPriceAdapter(Context context,List<BookModel> bookList){
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.promo_price_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookModel model = bookList.get(position);

        SpannableString spannableString = new SpannableString(String.valueOf(model.getPrice()));
        spannableString.setSpan(new StrikethroughSpan(),0,model.getPrice().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.bookTitle.setText(model.getTitle());
        holder.price.setText(spannableString+" MMK");
        holder.promoPrice.setText(model.getPromoPrice()+" MMK");
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(model.getPicture(),opt);
        holder.book.setImageBitmap(bm);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        CardView bookCard;
        ImageView book;
        TextView bookTitle,price,promoPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookCard = itemView.findViewById(R.id.bookCard);
            book = itemView.findViewById(R.id.book);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            price = itemView.findViewById(R.id.price);
            promoPrice = itemView.findViewById(R.id.promoPrice);
        }
    }
}
