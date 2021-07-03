package com.example.todo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.todo.databinding.ActivityDailyTodoBinding;
import com.example.todo.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class DailyDoesActivity extends AppCompatActivity {
    ActivityDailyTodoBinding binding;
    MyDailyDatabaseHelper mydb;
    ArrayList<String> item_name, quantity_of_purchase,item_id ,quantity_of_monthly_purchase;
    DailyCustomAdapter dailyCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_todo);
        binding = ActivityDailyTodoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        mydb = new MyDailyDatabaseHelper(DailyDoesActivity.this);
        item_name = new ArrayList<>();
        quantity_of_purchase = new ArrayList<>();
        item_id = new ArrayList<>();
        quantity_of_monthly_purchase = new ArrayList<>();
        storeDataInArray();

        dailyCustomAdapter = new DailyCustomAdapter(DailyDoesActivity.this,getApplicationContext(),item_id,item_name,quantity_of_purchase,quantity_of_monthly_purchase);
        binding.recyclerview.setAdapter(dailyCustomAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(DailyDoesActivity.this));
        dailyCustomAdapter.refreshAdapter(item_name,quantity_of_purchase);

        binding.btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddDailyTask.class);
                startActivity(intent);
            }
        });

        binding.btnGoToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1)
        {
            recreate();
        }
    }

    void storeDataInArray()
    {
        Cursor cursor = mydb.readData();
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        }
        else {
            cursor.moveToFirst();
            while (true)
            {
                item_id.add(cursor.getString(0));
                item_name.add(cursor.getString(1));
                quantity_of_purchase.add(cursor.getString(2));
                quantity_of_monthly_purchase.add(cursor.getString(3));
                if (cursor.isLast() )
                {
                    break;
                }

                cursor.moveToNext();
            }
        }

    }
}