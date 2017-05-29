package com.imark.emailstalk;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    PendingIntent pendingIntent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional 
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getData());
    }

    private void sendNotification(String body, Map<String, String> data) {

//        if (isApplicationSentToBackground(getApplicationContext())) {
//            Intent intent = new Intent(this, EmailDetailActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//             pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//        }else
//        {
//            Intent push = new Intent();
//            push.putExtra("default", body);
//            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            push.setClass(this, EmailDetailActivity.class);
//            this.startActivity(push);
//        }

        Intent intent = new Intent(this, EmailDetailActivity.class);
        intent.putExtra("MessageId", data.get("message-id"));
        intent.putExtra("Type", "Notification");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Email Stalk")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());


        if(!isApplicationSentToBackground(this)){
            Intent push = new Intent();
            push.putExtra("Body",body);
            push.putExtra("ID", data.get("message-id"));
            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            push.setClass(this, NotificationPopupActivity.class);
            this.startActivity(push);
        }
    }


    public boolean isApplicationSentToBackground(Context mcontext) {
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

    void NotificationPopup(String body, final String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Email Stalk");
        builder.setMessage(body);
        builder.setMessage("Do you wanted to see Email Detail")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), EmailDetailActivity.class);
                        intent.putExtra("MessageId", s );
                        intent.putExtra("Type", "Notification");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                                PendingIntent.FLAG_ONE_SHOT);
                            dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle(this.getResources().getString(R.string.app_name));
        alert.show();
    }
}
