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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DailyCustomAdapter extends RecyclerView.Adapter<DailyCustomAdapter.MyViewHolder> {  private Context context;
    private ArrayList item_id, item_name,quantity_of_purchase,quantity_of_monthly_purchase;
    Activity activity;

    DailyCustomAdapter (Activity activity,Context context,ArrayList item_id, ArrayList item_name, ArrayList quantity_of_purchase, ArrayList quantity_of_monthly_purchase)
    {
        this.activity=activity;
        this.context = context;
        this.item_id=item_id;
        this.item_name=item_name;
        this.quantity_of_purchase=quantity_of_purchase;
        this.quantity_of_monthly_purchase = quantity_of_monthly_purchase;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.daily_todo_does,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DailyCustomAdapter.MyViewHolder holder, int position) {

        holder.txt_item_name.setText(String.valueOf(item_name.get(position)));
        holder.txt_quantity_of_purchase.setText(String.valueOf(quantity_of_purchase.get(position)));
        holder.txt_quantity_of_monthly_purchase.setText(String.valueOf(quantity_of_monthly_purchase.get(position)));
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateDailyQuantity.class);
                intent.putExtra("item_name", String.valueOf(item_name.get(position)) );
                intent.putExtra("quantity_of_purchase", String.valueOf(quantity_of_purchase.get(position)) );
                intent.putExtra("quantity_of_monthly_purchase",String.valueOf(quantity_of_monthly_purchase.get(position)));
                intent.putExtra("item_id", String.valueOf(item_id.get(position)) );
                activity.startActivityForResult(intent,1);

            }

        });
    }

    @Override
    public int getItemCount() {
        return item_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_item_name,txt_quantity_of_purchase,txt_quantity_of_monthly_purchase;
        ConstraintLayout item_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_item_name = itemView.findViewById(R.id.daily_item);
            txt_quantity_of_purchase = itemView.findViewById(R.id.quantity_of_purchase);
            txt_quantity_of_monthly_purchase = itemView.findViewById(R.id.quantity_of_monthly_purchase);
            item_layout = itemView.findViewById(R.id.item_layout);

        }
    }

    public void refreshAdapter(  ArrayList item_name, ArrayList quantity_of_purchase)
    {
        this.item_name=item_name;
        this.quantity_of_purchase=quantity_of_purchase;
        notifyDataSetChanged();
    }
}
