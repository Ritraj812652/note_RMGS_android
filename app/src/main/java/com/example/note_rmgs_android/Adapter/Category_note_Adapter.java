package com.example.note_rmgs_android.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_rmgs_android.Models.Note;
import com.example.note_rmgs_android.Models.Subject;
import com.example.note_rmgs_android.R;

import java.util.Date;
import java.util.List;

public class Category_note_Adapter extends RecyclerView.Adapter<Category_note_Adapter.Viewholder> {
    public Context context;
    public List<Note> list;


    public Category_note_Adapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Category_note_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  itemview= LayoutInflater.from(context).inflate(R.layout.category_note_item,parent,false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_note_Adapter.Viewholder holder, int position) {
        holder.note_title.setText(list.get(position).getTitle());
        holder.note_description.setText(list.get(position).getDescription());
        long millisecond = list.get(position).getCreated_date();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
        holder.note_date.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView note_date,note_title,note_description;
        ImageView location;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            note_date=(TextView) itemView.findViewById(R.id.note_date);
            note_title=(TextView) itemView.findViewById(R.id.note_title);
            note_description=(TextView) itemView.findViewById(R.id.note_description);
            location=(ImageView) itemView.findViewById(R.id.location);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
