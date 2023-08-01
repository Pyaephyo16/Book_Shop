package com.example.book_shelf.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.book_shelf.R;

import java.util.List;

public class SpinAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> list;

    public SpinAdapter(@NonNull Context context, int resource, List<String> list) {
        super(context, resource);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // View v = LayoutInflater.from(context).inflate(R.layout.spin_text,parent,false);
        View v = LayoutInflater.from(context).inflate(R.layout.spin_text,parent,false);
        TextView label = (TextView) super.getView(position, v, parent);

        label.setText(list.get(position));
        return label;
    }
}
