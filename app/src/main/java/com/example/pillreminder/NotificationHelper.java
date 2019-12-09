package com.example.pillreminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String  channel1Id = "channel1ID";
    public static final String  channel1Name = "Channel Name";
    private  NotificationManager mManager;

    public NotificationHelper(Context base){
        super(base);

        createChannels();
    }
    public void createChannels(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel1= new NotificationChannel(channel1Id,channel1Name, NotificationManager.IMPORTANCE_HIGH);
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLightColor(R.color.colorPrimary);
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(channel1);


        }

    }

    public NotificationManager getManager() {
        if (mManager == null){
            mManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannel1Notification(){
        return  new NotificationCompat.Builder(getApplicationContext(),channel1Id)
                .setContentTitle("Alarm")
                .setContentText("It is time to take your drugs ")
                .setSmallIcon(R.drawable.pill1);
    }
}
