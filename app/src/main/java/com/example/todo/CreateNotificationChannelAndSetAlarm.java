package com.example.todo;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class CreateNotificationChannelAndSetAlarm {

    private AlarmManager alarmManager;
    private PendingIntent alarmpendingIntent;

    public void setAlarm (Calendar calendar1 , Context context)
    {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context,AlarmReceiver.class);
         alarmpendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),alarmpendingIntent);

    }
}
