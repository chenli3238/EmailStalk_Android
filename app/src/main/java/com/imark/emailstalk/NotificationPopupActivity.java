package com.imark.emailstalk;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationPopupActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_popup);
        ButterKnife.bind(this);
        String mTextmessage = getIntent().getStringExtra("Body");
        String messageid = getIntent().getStringExtra("ID");
        showDialog(this, mTextmessage,messageid);
    }
    public void showDialog(Activity mactivity, String body, final String messageid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mactivity);
        builder.setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(body);
        builder.setIcon(R.drawable.appicon);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), EmailDetailActivity.class);
                intent.putExtra("MessageId", messageid );
                intent.putExtra("Type", "Notification");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dialog.dismiss();
                NotificationPopupActivity.this.finish();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        NotificationPopupActivity.this.finish();
                    }
                });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.Popup_notification)
    protected void click(){
        NotificationPopupActivity.this.finish();
    }
}

