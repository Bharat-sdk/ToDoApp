package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.todo.databinding.ActivityNewTaskBinding;
import com.example.todo.databinding.ActivityUpdateTaskBinding;

public class UpdateTask extends AppCompatActivity {
    String id,title,date,desc,title1,date1,desc1;
ActivityUpdateTaskBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        binding = ActivityUpdateTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setDataToEditTxt();

        binding.btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = binding.edtTitledoes2.getText().toString().trim();
                date = binding.edtDatedoes2.getText().toString().trim();
                desc = binding.edtDescdoes2.getText().toString().trim();
    MyDatabaseHelper mydb = new MyDatabaseHelper(getApplicationContext());
    mydb.updateData(id,title,desc,date);
            }
        });

    }

    void setDataToEditTxt()
    {
    title1 = getIntent().getStringExtra("todo_title");
    desc1 = getIntent().getStringExtra("todo_description");
    date1 = getIntent().getStringExtra("todo_time");
    id = getIntent().getStringExtra("todo_id");
        binding.edtTitledoes2.setText(title1);
        binding.edtDescdoes2.setText(desc1);
        binding.edtDatedoes2.setText(date1);
    }
}