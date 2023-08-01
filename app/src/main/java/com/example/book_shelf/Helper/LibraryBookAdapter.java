package com.example.book_shelf.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.Models.ShelfBookModel;
import com.example.book_shelf.R;

import java.util.List;

public class LibraryBookAdapter extends RecyclerView.Adapter<LibraryBookAdapter.ViewHolder>{

    Context context;
    private List<ShelfBookModel> bookList;

    public LibraryBookAdapter(Context context,List<ShelfBookModel> list){
            this.context = context;
            this.bookList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.library_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShelfBookModel model = bookList.get(position);

        holder.bookTitle.setText(model.getShelfBookTitle());
        holder.bookDate.setText("Type : "+ model.getShelfBookType());
        holder.bookPrice.setText("Pirce : "+model.getShelfBookPrice()+" MMK");
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(model.getShelfBookPicture(),opt);
        holder.book.setImageBitmap(bm);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView book;
        CardView bookCard;
        TextView bookTitle,bookDate,bookPrice;

        //LinearLayout pending;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book = itemView.findViewById(R.id.book);
            bookCard = itemView.findViewById(R.id.bookCard);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookDate = itemView.findViewById(R.id.bookDate);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            //pending = itemView.findViewById(R.id.pending);
        }
    }
}
