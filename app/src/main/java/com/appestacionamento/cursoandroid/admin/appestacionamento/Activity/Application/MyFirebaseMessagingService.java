package com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Application;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;

import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.MainActivity;
import  com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.*;
import com.appestacionamento.cursoandroid.admin.appestacionamento.Activity.Activity.Usuario.ActivityUsuario;
import com.appestacionamento.cursoandroid.admin.appestacionamento.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessaging";
    public static final String TITULO = "titulo", CORPOMENSAGEM = "mensagem";

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Message data Payload: "+remoteMessage.getData());
            try{
                JSONObject data = new JSONObject(remoteMessage.getData());
                String jsonMessage = data.getString("extra_information");
                Log.d(TAG, "onMessageReceived: Message Received: Message: "+jsonMessage);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(remoteMessage.getNotification() != null){
            String titulo = remoteMessage.getNotification().getTitle();
            String mensagem = remoteMessage.getNotification().getBody();
            String click_action = remoteMessage.getNotification().getClickAction();
            Map<String, String> extra = remoteMessage.getData();

            String mensagemExtra = extra.get("extra_information");

            Log.d(TAG, "Message EXTRA: "+mensagemExtra);

            Log.d(TAG, "Message Notification Titulo: "+titulo);
            Log.d(TAG, "Message Notification Corpo: "+mensagem);
            Log.d(TAG, "Message Notification Click: "+click_action);

            sendNotifications(titulo, mensagemExtra, click_action);
        }

    }

    @Override
    public void onDeletedMessages() {


    }

    private void sendNotifications(String titulo, String mensagemExtra, String click){
        Intent intent;
        if(click.equals("ACTIVITYNOTIFICATION")){
            intent = new Intent(this, NotificationActivity.class);
            intent.putExtra(TITULO, titulo);
            intent.putExtra(CORPOMENSAGEM, mensagemExtra);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else{
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Message")
                .setContentText(mensagemExtra)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /*Id da notificação*/, notificationBuilder.build());
    }


}
