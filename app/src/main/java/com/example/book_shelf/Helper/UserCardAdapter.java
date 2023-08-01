package com.example.book_shelf.Helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.Models.UserModel;
import com.example.book_shelf.R;

import java.util.ArrayList;
import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardAdapter.ViewHolder> {

    Context context;
    List<UserModel> userList = new ArrayList<>();

    Drawable admin_card;

    public UserCardAdapter(Context context,List<UserModel> userList){
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel model = userList.get(position);

        admin_card = ResourcesCompat.getDrawable(context.getResources(),
                R.drawable.admin_card, null);

        if (model.getIsAdmin().equals("true")){
            holder.userCard.setBackground(admin_card);
        }

        holder.userEmail.setText(model.getEmail());
        holder.userName.setText(model.getName());
        holder.userPhone.setText(model.getPhone());
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(model.getProfile(),opt);
        holder.profile.setImageBitmap(bm);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profile;
        TextView userName,userPhone,userEmail;
        LinearLayout userCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            userName = itemView.findViewById(R.id.userName);
            userPhone = itemView.findViewById(R.id.userPhone);
            userEmail = itemView.findViewById(R.id.userEmail);
            userCard = itemView.findViewById(R.id.userCard);
        }
    }
}
