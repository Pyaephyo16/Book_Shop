package com.example.book_shelf.Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.ChangeBookData;
import com.example.book_shelf.DeleteBook;
import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.R;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> implements Filterable {

    Context context;
    private List<BookModel> bookList;
    private List<BookModel> originalList;

    //update = 1
    //delete = 2
    //search = 3
    int backPage = 0;

    public BookAdapter(Context context,List<BookModel> bookList,int backPage){
            this.context = context;
            this.bookList = bookList;
            this.originalList = new ArrayList<>(bookList);
            this.backPage = backPage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookModel model = bookList.get(position);

        holder.bookTitle.setText(model.getTitle());
        holder.bookAuthor.setText("Name : "+ model.getAuthor());
        holder.bookPrice.setText("Pirce : "+model.getPrice()+" MMK");
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(model.getPicture(),opt);
        holder.book.setImageBitmap(bm);

        holder.bookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backPage == 1){
                    Intent i =new Intent(v.getContext(), ChangeBookData.class);
                    i.putExtra("bId",String.valueOf(model.getBookId()));
                    v.getContext().startActivity(i);
                }else if (backPage == 2){
                    Intent i =new Intent(v.getContext(), DeleteBook.class);
                    i.putExtra("bId",String.valueOf(model.getBookId()));
                    v.getContext().startActivity(i);
                }else if (backPage == 3){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    @Override
    public Filter getFilter() {
        return bookFilter;
    }

    private Filter bookFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<BookModel> filterList =new ArrayList<>();
            if (charSequence==null && charSequence.length()==0){
                filterList.addAll(originalList);
            }else{
                String pattern = charSequence.toString().toLowerCase().trim();
                for (BookModel bm : originalList){
                    if (bm.getTitle().toLowerCase().toString().contains(pattern)){
                        filterList.add(bm);
                    }
                }
            }
            FilterResults rs = new FilterResults();
            rs.values = filterList;
            return rs;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bookList.clear();
            bookList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView book;
        CardView bookCard;
        TextView bookTitle,bookAuthor,bookPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book =itemView.findViewById(R.id.book);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookAuthor = itemView.findViewById(R.id.bookAuthor);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            bookCard = itemView.findViewById(R.id.bookCard);
        }
    }
}



