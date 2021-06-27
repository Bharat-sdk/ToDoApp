package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.todo.databinding.ActivityNewTaskBinding;
import com.example.todo.databinding.ActivityUpdateTaskBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateTask extends AppCompatActivity {
    String id,title,date,desc,title1,date1,desc1;
ActivityUpdateTaskBinding binding;
    private AlarmManager alarmManager;
    private PendingIntent alarmpendingIntent;
Calendar calendar;
CreateNotificationChannelAndSetAlarm createNotificationChannelAndSetAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        AddNewTask  addNewTask = new AddNewTask();

        binding = ActivityUpdateTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setDataToEditTxt();
        createNotificationChannel();
        binding.edtDatedoes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar= Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);

                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

                                date = (simpleDateFormat.format(calendar.getTime()));
                                binding.edtDatedoes2.setText(date);
                            }

                        };
                        new TimePickerDialog(UpdateTask.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                    }
                };
                new DatePickerDialog(UpdateTask.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        binding.btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getValuesFromEditText();
               MyDatabaseHelper mydb = new MyDatabaseHelper(getApplicationContext());
               mydb.updateData(id,title,desc,date);
               gotoMainActivity();
               setAlarm(calendar);
            }
        });

        binding.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getValuesFromEditText();
                MyDatabaseHelper mydb = new MyDatabaseHelper(getApplicationContext());
                mydb.deleteData(title,desc,date);
                gotoMainActivity();
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

    void  gotoMainActivity()
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    void getValuesFromEditText()
    {
        title = binding.edtTitledoes2.getText().toString().trim();
        date = binding.edtDatedoes2.getText().toString().trim();
        desc = binding.edtDescdoes2.getText().toString().trim();
    }
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name  = "ToDoNotificationChannel";
            String description = "Channel for normal TODO Works";
            int importance  = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel1 = new NotificationChannel("BasicTodoAlarm", name, importance);
            channel1.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
        }
    }



    public void setAlarm (Calendar calendar1)
    {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(UpdateTask.this,AlarmReceiver.class);
        alarmpendingIntent = PendingIntent.getBroadcast(UpdateTask.this, 0, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),alarmpendingIntent);

    }
}