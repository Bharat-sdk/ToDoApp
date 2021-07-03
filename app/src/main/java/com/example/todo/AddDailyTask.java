package com.example.todo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todo.databinding.AddDailyTodoTaskBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddDailyTask extends AppCompatActivity {

    AddDailyTodoTaskBinding binding;
    int days;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent alarmpendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
         binding = AddDailyTodoTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        createNotificationChannel();

        binding.btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item_name = binding.edtDailyItem.getText().toString().trim();
                int qty_purchase = Integer.parseInt(binding.edtWeight.getText().toString().trim());
                int qty_monthly_purchase = Integer.parseInt(binding.edtMonthlyWeight.getText().toString().trim());
                days = ((qty_purchase*30)/qty_monthly_purchase) - 2;
                calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 10);
                MyDailyDatabaseHelper mydb = new MyDailyDatabaseHelper(AddDailyTask.this);
                mydb.addItem(item_name,qty_purchase,qty_monthly_purchase);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

               String time = (simpleDateFormat.format(calendar.getTime()));
                Log.d("TAG", "onClick: " + time);


                setAlarm(calendar, binding.edtDailyItem.getText().toString().trim());
                Intent intent = new Intent(getApplicationContext(), DailyDoesActivity.class);
                startActivity(intent);
            }
        });
    }


        public void createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "ToDoNotificationChannel";
                String description = "Channel for normal TODO Works";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel1 = new NotificationChannel("BasicTodoAlarm", name, importance);
                channel1.setDescription(description);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel1);
            }

        }
        public void setAlarm (Calendar calendar1, String title)
        {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent alarmIntent = new Intent(AddDailyTask.this, AlarmReceiver.class);
            alarmIntent.putExtra("title", title);
            alarmpendingIntent = PendingIntent.getBroadcast(AddDailyTask.this, calendar1.get(Calendar.HOUR) + calendar1.get(Calendar.MILLISECOND) + calendar1.get(Calendar.SECOND), alarmIntent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), alarmpendingIntent);

        }


}
