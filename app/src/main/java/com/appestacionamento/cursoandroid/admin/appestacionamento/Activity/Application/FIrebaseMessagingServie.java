package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.MainActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by renna on 05/07/2017.
 */

public class FIrebaseMessagingServie extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        super.onMessageReceived(remoteMessage);
        Intent intent = new Intent(this, MainActivity.class);
        //Flag Clear Top https://developer.android.com/reference/android/content/Intent.html
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // https://justanapplication.wordpress.com/tag/flag_one_shot/
        PendingIntent pendingIntent =  PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("Hello World");
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());

    }
}
