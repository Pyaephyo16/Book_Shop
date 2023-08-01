package com.example.book_shelf.Helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_shelf.ChangeBookData;
import com.example.book_shelf.Models.OrderDetailModel;
import com.example.book_shelf.Models.OrderModel;
import com.example.book_shelf.Models.ShelfBookModel;
import com.example.book_shelf.Otp;
import com.example.book_shelf.R;
import com.example.book_shelf.VoucherPage;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderCardAdapter extends RecyclerView.Adapter<OrderCardAdapter.ViewHolder> {

    Context context;
    List<OrderModel> orderList;

    public OrderCardAdapter(Context context,List<OrderModel> orderList){
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel = orderList.get(position);

        String d1 = orderModel.getOrderDate();
        String deliverDate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();

        String todayDate = sdf.format(currentDate);

        try {
            Date date = sdf.parse(d1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.add(Calendar.DAY_OF_MONTH, 3);
            Date dateAfter3Days = calendar.getTime();

            String formattedDate = sdf.format(dateAfter3Days);
            deliverDate = formattedDate;
            System.out.println("Date after 3 days: " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Date d2 = sdf.parse(deliverDate);
            Date d3 = sdf.parse(todayDate);

            if (d2.compareTo(d3) > 0) {
               holder.orderCard.setVisibility(View.GONE);
            } else {
                holder.orderCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =new Intent(v.getContext(), VoucherPage.class);
                        i.putExtra("phone",String.valueOf(orderModel.getOrderId()));
                        v.getContext().startActivity(i);
                    }
                });
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.orderId.setText(String.valueOf(orderModel.getOrderId()));
        holder.orderDate.setText(orderModel.getOrderDate());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout orderCard;
        TextView orderId,orderDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderCard = itemView.findViewById(R.id.orderCard);
            orderId = itemView.findViewById(R.id.orderId);
        }
    }
}
