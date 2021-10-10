package com.example.note_rmgs_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_rmgs_android.Category_Notes;
import com.example.note_rmgs_android.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    public Context context;
    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  itemview= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView category_title,category_date;
        ImageView delete;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            category_title=(TextView)itemView.findViewById(R.id.category_title);
            category_date=(TextView) itemView.findViewById(R.id.category_date);
            delete=(ImageView)itemView.findViewById(R.id.sub_delete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i=new Intent(context, Category_Notes.class);
            context.startActivity(i);
        }
    }
}
