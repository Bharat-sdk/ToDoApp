package com.example.todo;

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
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.databinding.ActivityMainBinding;
import com.example.todo.databinding.ActivityNewTaskBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNewTask extends AppCompatActivity {
    ActivityNewTaskBinding binding;
    String time;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent alarmpendingIntent;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        binding = ActivityNewTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
      createNotificationChannel();

        binding.edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 calendar=Calendar.getInstance();
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

                                time = (simpleDateFormat.format(calendar.getTime()));
                                binding.edtTime.setText(time);
                            }

                        };
                        new TimePickerDialog(AddNewTask.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                    }
                };
                new DatePickerDialog(AddNewTask.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        binding.btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyDatabaseHelper mydb = new MyDatabaseHelper(AddNewTask.this);
                mydb.addWork(binding.edtTitledoes.getText().toString().trim(),
                        binding.edtDescdoes.getText().toString().trim(),time
                );
                setAlarm(calendar,binding.edtTitledoes.getText().toString().trim());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
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
    public void setAlarm (Calendar calendar1,String title)
    {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(AddNewTask.this,AlarmReceiver.class);
        alarmIntent.putExtra("title",title);
        Log.d("TAG", "setAlarm: "+ title);
        alarmpendingIntent = PendingIntent.getBroadcast(AddNewTask.this, calendar1.get(Calendar.HOUR)+calendar1.get(Calendar.MILLISECOND)+calendar1.get(Calendar.SECOND), alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),alarmpendingIntent);

    }

}
