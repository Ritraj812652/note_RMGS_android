package com.example.note_rmgs_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_rmgs_android.Add_Edit_Note;
import com.example.note_rmgs_android.MapsActivity;
import com.example.note_rmgs_android.Models.Note;
import com.example.note_rmgs_android.Models.Subject;
import com.example.note_rmgs_android.R;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class Category_note_Adapter extends RecyclerView.Adapter<Category_note_Adapter.Viewholder> {
    public Context context;
    public List<Note> list;
    public List<Subject> subjects;


    public Category_note_Adapter(Context context, List<Note> list,List<Subject> subjects) {
        this.context = context;
        this.list = list;
        this.subjects=subjects;
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
        String dateString = DateFormat.format("MMM d, h:mm a", new Date(millisecond)).toString();
        holder.note_date.setText(dateString);
        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;

        GradientDrawable draw = new GradientDrawable();
        //draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.itemView.setBackground(draw);
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MapsActivity.class);
                i.putExtra("noteID",list.get(position).getNote_id());
                context.startActivity(i);
            }
        });
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

                String name = "";
                int id = -1;
                for (Subject sub : subjects) {
                    if (sub.getSubject_id() == list.get(getAdapterPosition()).getSubject_fk()) {
                        name = sub.getName();
                        id = sub.getSubject_id();
                    }
                }
                Intent i = new Intent(context, Add_Edit_Note.class);
                i.putExtra("from", "update");
                i.putExtra("name", name);
                i.putExtra("ID", getAdapterPosition());
                i.putExtra("noteID", list.get(getAdapterPosition()).getNote_id());
                i.putExtra("SubjectID", id);



                context.startActivity(i);
            }

    }
    public void updateList(List<Note> xlist){
        list.clear();
        list.addAll(xlist);
        notifyDataSetChanged();
    }
}
