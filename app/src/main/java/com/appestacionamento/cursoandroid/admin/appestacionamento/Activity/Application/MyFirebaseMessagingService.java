package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.MainActivity;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessaging";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Message data Payload: "+remoteMessage.getData());
        }

        if(remoteMessage.getNotification() != null){
            String titulo = remoteMessage.getNotification().getTitle();
            String mensagem = remoteMessage.getNotification().getBody();

            Log.d(TAG, "Message Notification Titulo: "+titulo);
            Log.d(TAG, "Message Notification Corpo: "+mensagem);

            sendNotifications(titulo, mensagem);
        }

        /*String titulo = remoteMessage.getNotification().getTitle();
        String mensagem = remoteMessage.getNotification().getBody();

        Log.d(TAG, "onMessageReceived: Message Received: Message:"+mensagem);

        sendNotifications(titulo, mensagem);*/
    }

    @Override
    public void onDeletedMessages() {


    }

    private void sendNotifications(String titulo, String mensagem){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /*request code*/, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(mensagem)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*Id da notificação*/, notificationBuilder.build());
    }
}
