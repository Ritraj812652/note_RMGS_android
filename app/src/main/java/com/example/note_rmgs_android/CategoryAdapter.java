package com.example.note_rmgs_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {
    public Context context;
    public List<Group> list;
    public CategoryAdapter(Context context, List<Group> allGroup) {
        this.context = context;
        this.list = allGroup;
    }



    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  itemview= LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new Viewholder(itemview);
    }





    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.category_title.setText(list.get(position).getName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTheItem(position);
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
            selectTheItem(getAdapterPosition());
        }
    }




    public abstract void deleteTheItem(int pos);
    public abstract void selectTheItem(int pos);
}
