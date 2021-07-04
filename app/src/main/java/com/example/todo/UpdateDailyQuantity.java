package com.example.todo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.databinding.ActivityDailyTodoBinding;
import com.example.todo.databinding.ActivityUpdateDailyTaskBinding;
import com.example.todo.databinding.AddDailyTodoTaskBinding;

import java.util.Calendar;

public class UpdateDailyQuantity extends AppCompatActivity {

    ActivityUpdateDailyTaskBinding binding;
    int new_day;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent alarmpendingIntent;
    String item_name,item_id,item_name1,item_id1;
    int quantity_of_purchase,quantity_of_monthly_purchase,quantity_of_purchase1,quantity_of_monthly_purchase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_daily_task);

        binding = ActivityUpdateDailyTaskBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setDataToEditTxt();
        createNotificationChannel();


        binding.btnUpdateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEditText();
                new_day = ((quantity_of_purchase1*30)/quantity_of_monthly_purchase) - 2;
                calendar = Calendar.getInstance();
                calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + new_day);
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 10);
                MyDailyDatabaseHelper mydb = new MyDailyDatabaseHelper(getApplicationContext());
                mydb.updateDailyData(String.valueOf(quantity_of_purchase1),item_name1);
                gotoMainActivity();
                setAlarm(calendar);
            }
        });

        binding.btnUpdateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMainActivity();
            }
        });

        binding.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValuesFromEditText();
                MyDailyDatabaseHelper mydb = new MyDailyDatabaseHelper(getApplicationContext());
                mydb.deleteData(item_name1);
                gotoMainActivity();
            }
        });
    }

    void setDataToEditTxt()
    {
        item_name = getIntent().getStringExtra("item_name");
        quantity_of_purchase = Integer.parseInt(getIntent().getStringExtra("quantity_of_purchase"));
        quantity_of_monthly_purchase = Integer.parseInt(getIntent().getStringExtra("quantity_of_monthly_purchase"));
        item_id = getIntent().getStringExtra("item_id");
        binding.txtDailyItem.setText(item_name);
        binding.edtUpdateWeight.setText(String.valueOf(quantity_of_purchase));
    }
    void  gotoMainActivity()
    {
        Intent intent = new Intent(getApplicationContext(),DailyDoesActivity.class);
        startActivity(intent);
    }

    void getValuesFromEditText()
    {
        item_name1 = binding.txtDailyItem.getText().toString().trim();
        quantity_of_purchase1 = Integer.parseInt(binding.edtUpdateWeight.getText().toString().trim());
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
        Intent alarmIntent = new Intent(UpdateDailyQuantity.this,AlarmReceiver.class);
        alarmIntent.putExtra("title",item_name1);
        alarmpendingIntent = PendingIntent.getBroadcast(UpdateDailyQuantity.this, calendar1.get(Calendar.HOUR)+calendar1.get(Calendar.MILLISECOND)+calendar1.get(Calendar.SECOND), alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),alarmpendingIntent);

    }

}
