package com.example.sellmybooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<BooksDetails> blist;
    Context context;

    public MyAdapter(ArrayList<BooksDetails> blist, Context context) {
        this.blist = blist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BooksDetails booksDetails = blist.get(position);
        holder.bookName.setText(booksDetails.getBookName());
        holder.bookDes.setText(booksDetails.getDescription());
        holder.bookPrice.setText(booksDetails.getPrice());
        holder.sellerEmail.setText(booksDetails.getUserEmail());
    }

    @Override
    public int getItemCount() {
        return blist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView bookName, bookDes, bookPrice, sellerEmail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.txt_ibookName);
            bookDes = itemView.findViewById(R.id.txt_idescription);
            bookPrice = itemView.findViewById(R.id.txt_iprice);
            sellerEmail = itemView.findViewById(R.id.txt_iemail);

        }
    }
}
