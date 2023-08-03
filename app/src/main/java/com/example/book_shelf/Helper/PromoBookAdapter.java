package com.example.book_shelf.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.Models.BookModel;
import com.example.book_shelf.R;
import com.example.book_shelf.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class PromoBookAdapter extends RecyclerView.Adapter<PromoBookAdapter.ViewHolder> implements Filterable {

    Context context;
    List<BookModel> promoList;

    private List<BookModel> originalList;

    public PromoBookAdapter(Context context,List<BookModel> promoList){
        this.context = context;
        this.promoList = promoList;
        this.originalList = new ArrayList<>(promoList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.promo_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookModel model = promoList.get(position);

        holder.bookTitle.setText(model.getTitle());
        holder.bookPrice.setText("Pirce : "+model.getPrice()+" MMK");
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(model.getPicture(),opt);
        holder.book.setImageBitmap(bm);

        holder.addToPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAlreadyInsert = false;
                for (int type : Util.promoList) {
                    if (type==model.getBookId()){
                        isAlreadyInsert = true;
                        break;
                    }
                }
                if (isAlreadyInsert==true){
                    Util.showToast(context,"Already Added to the promotion list!");
                }else{
                    Util.promoList.add(model.getBookId());
                    Util.showToast(context,"Added to promotion list!");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return promoList.size();
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
            promoList.clear();
            promoList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView book;
        CardView bookCard;
        TextView bookTitle,bookPrice;
        EditText addToPromotion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book = itemView.findViewById(R.id.book);
            bookCard = itemView.findViewById(R.id.bookCard);
            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookPrice = itemView.findViewById(R.id.bookPrice);
            addToPromotion = itemView.findViewById(R.id.addToPromotion);
        }
    }
}
