package com.example.todo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override

    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        Intent i = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        Log.d("TAG", "onReceive: "+ title);

        NotificationCompat.Builder builder = new  NotificationCompat.Builder(context,"BasicTodoAlarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Todo Reminder")
                .setContentText(title)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                 .setContentIntent(pendingIntent);

      NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123444,builder.build());
    }
}
