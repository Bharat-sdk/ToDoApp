package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList todo_id, todo_title,todo_disc,todo_time;
    Activity activity;

    CustomAdapter (Activity activity,Context context,ArrayList todo_id, ArrayList todo_title, ArrayList todo_disc, ArrayList todo_time)
    {
        this.activity=activity;
        this.context = context;
        this.todo_title=todo_title;
        this.todo_disc=todo_disc;
        this.todo_time=todo_time;
        this.todo_id = todo_id;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
       View view = inflater.inflate(R.layout.item_does,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.todo_title_txt.setText(String.valueOf(todo_title.get(position)));
        holder.todo_description_txt.setText(String.valueOf(todo_disc.get(position)));
        holder.todo_time_txt.setText(String.valueOf(todo_time.get(position)));
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateTask.class);
                intent.putExtra("todo_title", String.valueOf(todo_title.get(position)) );
                intent.putExtra("todo_description", String.valueOf(todo_disc.get(position)) );
                intent.putExtra("todo_time", String.valueOf(todo_time.get(position)) );
                intent.putExtra("todo_id", String.valueOf(todo_id.get(position)) );
               activity.startActivityForResult(intent,1);

            }

        });
    }

    @Override
    public int getItemCount() {
        return todo_title.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView todo_title_txt,todo_description_txt,todo_time_txt;
        LinearLayout item_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            todo_title_txt = itemView.findViewById(R.id.titledoes);
            todo_description_txt = itemView.findViewById(R.id.descdoes);
            todo_time_txt = itemView.findViewById(R.id.datedoes);
            item_layout = itemView.findViewById(R.id.item_layout);

        }
    }

    public void refreshAdapter( ArrayList todo_title, ArrayList todo_disc, ArrayList todo_time)
    {
        this.todo_title=todo_title;
        this.todo_disc=todo_disc;
        this.todo_time=todo_time;
        notifyDataSetChanged();
    }
}
