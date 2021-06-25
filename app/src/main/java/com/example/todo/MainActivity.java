package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todo.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    MyDatabaseHelper mydb;
    ArrayList<String> todo_title, todo_disc, todo_time,todo_id;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


       binding.btnAddNew.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),AddNewTask.class);
               startActivity(intent);
           }
       });

        mydb = new MyDatabaseHelper(MainActivity.this);
        todo_title = new ArrayList<>();
        todo_disc = new ArrayList<>();
        todo_time = new ArrayList<>();
        todo_id = new ArrayList<>();
        storeDataInArray();

        customAdapter = new CustomAdapter(MainActivity.this,getApplicationContext(),todo_id,todo_title,todo_disc,todo_time);
        binding.recyclerview.setAdapter(customAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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
                todo_id.add(cursor.getString(0));
                todo_title.add(cursor.getString(1));
                todo_disc.add(cursor.getString(2));
                todo_time.add(cursor.getString(3));
                if (cursor.isLast())
                {
                    todo_id.add(cursor.getString(0));
                    todo_title.add(cursor.getString(1));
                    todo_disc.add(cursor.getString(2));
                    todo_time.add(cursor.getString(3));
                  break;

                }
                cursor.moveToNext();
            }
        }

    }
}