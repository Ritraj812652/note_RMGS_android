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
import com.example.note_rmgs_android.Models.Subject;
import com.example.note_rmgs_android.R;

import java.util.List;

public abstract class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    public Context context;
    public List<Subject> list;
    public CategoryAdapter(Context context, List<Subject> allSubject) {
        this.context = context;
        this.list = allSubject;
    }

    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  itemview= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, int position) {
        holder.category_title.setText(list.get(position).getName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView category_title,category_date;
        ImageView delete;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            category_title=(TextView)itemView.findViewById(R.id.category_title);
            delete=(ImageView)itemView.findViewById(R.id.sub_delete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i=new Intent(context, Category_Notes.class);
            i.putExtra("name",list.get(getAdapterPosition()).getName());
             context.startActivity(i);
        }
    }

    public abstract void deleteCategory(int pos);
}
