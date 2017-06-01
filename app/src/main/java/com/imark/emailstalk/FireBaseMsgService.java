package com.imark.emailstalk;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.imark.emailstalk.Infrastructure.AppCommon;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;

public class FireBaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    NotificationManager mManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData());
     }

    private void sendNotification(String body, Map<String, String> data) {

        if (isApplicationSentToBackground(getApplicationContext())) {
            mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
            Intent intent = new Intent(this, EmailDetailActivity.class);
            intent.putExtra("MessageId", data.get("message-id"));
            intent.putExtra("Type", "Notification");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.appicon)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }else{
            Intent intent = new Intent(this, EmailDetailActivity.class);
            intent.putExtra("MessageId", data.get("message-id"));
            intent.putExtra("Type", "Notification");
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.appicon)
                    .setContentTitle(getResources().getString(R.string.app_name))
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingNotificationIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            notificationManager.notify(0, notificationBuilder.build());
            Intent push = new Intent();
            push.putExtra("Body", body);
            push.putExtra("ID", data.get("message-id"));
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            push.setClass(this, NotificationPopupActivity.class);
            this.startActivity(push);
        }
    }


    private boolean isApplicationSentToBackground(Context mcontext) {
        // TODO Auto-generated method stub
        ActivityManager am = (ActivityManager) mcontext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mcontext.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
